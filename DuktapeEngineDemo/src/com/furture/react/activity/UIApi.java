package com.furture.react.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.Toast;

import com.furture.react.JSRef;

public class UIApi {
	
	private Activity activity;
	public UIApi(Activity activity) {
		super();
		this.activity = activity;
	}

	public void toast(String message){
		Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
	}
	
	public void alert(String title, String message, String ok, final JSRef okClickListener,
			String cancel, final JSRef cancelClickListener){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		if(!TextUtils.isEmpty(message)){
			builder.setMessage(message);
		}
		if(!TextUtils.isEmpty(title)){
			builder.setTitle(title);
		}
		if(!TextUtils.isEmpty(ok)){
			 builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(okClickListener != null){
						       okClickListener.getEngine().callJSRef(okClickListener, "onClick");
						}
					}
			 });
		}
		
		if(!TextUtils.isEmpty(cancel)){
			 builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if(cancelClickListener != null){
						    cancelClickListener.getEngine().callJSRef(cancelClickListener, "onClick");
						}
					}
			 });
		}
	    builder.create().show();
	}
	
}
