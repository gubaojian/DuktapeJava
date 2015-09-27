package com.furture.react.activity;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.furture.react.JSRef;

public class MediaApi {

	public static final int CAMERA_REQUEST_CODE = 100;
	
	
	private Activity activity;

	private JSRef  pictureCallback;
	private Uri    pictureUri;
	
	
	public MediaApi(Activity activity) {
		super();
		this.activity = activity;
	}


	public void takePicture(JSRef callback){
		try {
			File file = File.createTempFile(System.currentTimeMillis() + "", ".jpg", getTempDirectoryPath());
			pictureUri = Uri.fromFile(file);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, pictureUri);
			activity.startActivityForResult(intent, CAMERA_REQUEST_CODE);
			pictureCallback = callback;
        } catch (IOException e) {
			e.printStackTrace();
			callback.getEngine().call(callback, "fail", "创建拍照文件失败");
		}
	}
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MediaApi.CAMERA_REQUEST_CODE) {
			if(pictureCallback != null){
				if (resultCode == Activity.RESULT_CANCELED) {
					pictureCallback.getEngine().call(pictureCallback, "fail", "User Cancel");
					pictureCallback = null;
					return;
				}
				if (resultCode == Activity.RESULT_OK) {
					pictureCallback.getEngine().call(pictureCallback, "success", pictureUri.toString());
					pictureCallback = null;
					pictureUri = null;
					return;
				}
			}
			
		}
		
	}
	
	
	private File getTempDirectoryPath() {
        File cache = null;
        // SD Card Mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cache =  activity.getExternalCacheDir();
        }else {
            cache = activity.getCacheDir();
        }
        // Create the cache directory if it doesn't exist
        if(!cache.exists()){
            cache.mkdirs();
        }
        return cache;
    }
}
