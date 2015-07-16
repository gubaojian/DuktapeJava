package com.furture.react.hybrid;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.xmlpull.v1.XmlPullParser;

import dalvik.system.DexClassLoader;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class HybridManager  extends ContextWrapper {
	
	private AssetManager mAssetManager;
	private HybridActivity mActivity;
	private Resources mResources;
	private int mCookie;
	private Theme  mTheme;
	private String packageName;
	public HybridManager(HybridActivity activity, File file) {
		super(activity);
		this.mActivity = activity;
		try {
			File optPath = new File("/data/data/com.furture.react/files/dex/");
			if (!optPath.exists()) {
				optPath.mkdirs();
			}
			this.mAssetManager = AssetManager.class.newInstance();
			Method addAssetPath = mAssetManager.getClass().getMethod("addAssetPath", String.class);
			Object value = addAssetPath.invoke(mAssetManager, file.getAbsolutePath());
			if (value instanceof Number) {
				mCookie = ((Number) value).intValue();
		    }
			if (mCookie <= 0) {
				throw new RuntimeException("addAssetPath error, invalid " + file.getAbsolutePath()  + " file");
			}
			String packageName = null;
			XmlResourceParser xml = mAssetManager.openXmlResourceParser("AndroidManifest.xml");
			int eventType = xml.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if(eventType == XmlPullParser.START_TAG){
					if ("manifest".equals(xml.getName())) {
						packageName = xml.getAttributeValue("http://schemas.android.com/apk/res/android", "package");
						if (TextUtils.isEmpty(packageName)) {
							packageName = xml.getAttributeValue(null, "package");
						}
						break;
					}
				}
				eventType = xml.nextToken();
			}
			xml.close();
			if (packageName == null) {
				throw new RuntimeException("package not found in AndroidManifest.xml [" + file.getAbsolutePath() + "]");
			}
			this.packageName = packageName;
			mResources = new Resources(mAssetManager, activity.getResources().getDisplayMetrics(),  activity.getResources().getConfiguration());
			mTheme = mResources.newTheme();
			mTheme.setTo(activity.getTheme());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
   }

	
	public Animation getAnimation(String name){
		int animation = mResources.getIdentifier(name, "animator", packageName);
		return AnimationUtils.loadAnimation(this, animation);
	}
	
	public Drawable getDrawable(String name){
		int drawable = mResources.getIdentifier(name, "drawable", packageName);
		return mResources.getDrawable(drawable);
	}
	

	public View inflate(String name) throws IOException{
		return inflate(name, null);
	}
	
	public View inflate(String name, ViewGroup root) throws IOException{
		XmlResourceParser parser = mAssetManager.openXmlResourceParser(mCookie,  "res/layout/" +  name + ".xml");
		return LayoutInflater.from(mActivity).inflate(parser, root);
	}
	
	
	

	@Override
	public Theme getTheme() {
		return mTheme;
	}

	@Override
	public AssetManager getAssets() {
		return mAssetManager;
	}

    @Override
	public Resources getResources() {
		return mResources;
	}
	
	
}
