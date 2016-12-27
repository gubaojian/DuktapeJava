package com.furture.react.activity;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.furture.react.JSRef;

public class MediaApi {

	public static final int CAMERA_REQUEST_CODE = 100;
	public static final int GALLERY_REQUEST_CODE = 101;
	
	private Activity activity;

	private JSRef  mediaCallback;
	private Uri    mediaUri;
	
	public MediaApi(Activity activity) {
		super();
		this.activity = activity;
	}


	/**
	 * 拍照
	 * */
	public void takePicture(JSRef callback){
		if(callback == null){
			mediaCallback = null;
			return;
		}
		try {
			File file = File.createTempFile(System.currentTimeMillis() + "", ".jpg", getCachePath());
			mediaUri = Uri.fromFile(file);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mediaUri);
			activity.startActivityForResult(intent, CAMERA_REQUEST_CODE);
			mediaCallback = callback;
        } catch (IOException e) {
			Log.e("MediaApi", "Create Temp File Exception " + e.getMessage());
			callback.getEngine().call(callback, "fail", "创建拍照文件失败");
		}
	}
	
	/**
	 * 从相册选择图片
	 * */
	public void choosePicture(JSRef callback){
		if(callback == null){
			mediaCallback = null;
			return;
		}
		Intent intent = new Intent(Intent.ACTION_PICK,  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		activity.startActivityForResult(intent, GALLERY_REQUEST_CODE);
		mediaCallback = callback;
	}
	
	
	/**
	 * 录像
	 * */
	public void takeVideo(JSRef callback){
		if(callback == null){
			mediaCallback = null;
			return;
		}
		try {
			File file = File.createTempFile(System.currentTimeMillis() + "", ".mp4", getCachePath());
			mediaUri = Uri.fromFile(file);
			Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mediaUri);
			activity.startActivityForResult(intent, CAMERA_REQUEST_CODE);
			mediaCallback = callback;
        } catch (IOException e) {
			Log.e("MediaApi", "Create Temp File Exception " + e.getMessage());
			callback.getEngine().call(callback, "fail", "创建拍照文件失败");
		}
	}
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MediaApi.CAMERA_REQUEST_CODE) {
			if(mediaCallback != null){
				if (resultCode == Activity.RESULT_CANCELED) {
					mediaCallback.getEngine().call(mediaCallback, "fail", "User Cancel");
					mediaCallback = null;
					return;
				}
				if (resultCode == Activity.RESULT_OK) {
					mediaCallback.getEngine().call(mediaCallback, "success", mediaUri.toString());
					mediaCallback = null;
					mediaUri = null;
					return;
				}
			}
		}
		if (requestCode == MediaApi.GALLERY_REQUEST_CODE) {
			if(mediaCallback != null){
				if (resultCode == Activity.RESULT_CANCELED || data == null) {
					mediaCallback.getEngine().call(mediaCallback, "fail", "User Cancel");
					mediaCallback = null;
					return;
				}
				if (resultCode == Activity.RESULT_OK && data.getData() != null) {
					Uri imageContentUri = data.getData();  
		            String[] filePathColumn = { MediaStore.Images.Media.DATA };  
		   
		            Cursor cursor = activity.getContentResolver().query(imageContentUri,   filePathColumn, null, null, null);  
		            cursor.moveToFirst();  
		            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);  
		            String picturePath = cursor.getString(columnIndex);  
		            cursor.close();  
		            Uri imageUri = Uri.fromFile(new File(picturePath));
					mediaCallback.getEngine().call(mediaCallback, "success", imageUri.toString());
					mediaCallback = null;
					return;
				}
			}
		}
	}
	
	
	private File getCachePath() {
        File cacheDir = null;
        // SD Card Mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir =  activity.getExternalCacheDir();
        }else {
            cacheDir = activity.getCacheDir();
        }
        // Create the cache directory if it doesn't exist
        if(!cacheDir.exists()){
            cacheDir.mkdirs();
        }
        return cacheDir;
    }
}
