package com.furture.react.ext;

import com.furture.react.JSRef;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 *  BaseAdapter 抽象类的实现。JavaScript使用BaseAdapter抽象类时，会映射到该类；
 *  也可直接使用此类。
 * */
public class JSBaseAdapter extends BaseAdapter{

	protected JSRef jsRef;

	private boolean multiType;

	public JSBaseAdapter(JSRef jsRef) {
		this.jsRef = jsRef;
		this.multiType = false;
	}

	public JSBaseAdapter(JSRef jsRef, boolean multiType) {
		this.jsRef = jsRef;
		this.multiType = multiType;
	}


	@Override
	public int getCount() {
		Object count = jsRef.getEngine().call(jsRef, "getCount");
		if (count == null) {
			return 0;
		}
		return ((Number) count).intValue();
	}

	@Override
	public Object getItem(int position) {
		return jsRef.getEngine().call(jsRef, "getItem",  position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return (View) jsRef.getEngine().call(jsRef, "getView",  position, convertView, parent);
	}


	@Override
	public int getViewTypeCount() {
		if(multiType){
			Object count = jsRef.getEngine().call(jsRef, "getViewTypeCount");
			if (count == null) {
				return 1;
			}
			return ((Number) count).intValue();
		}
		return  super.getViewTypeCount();
	}

	@Override
	public int getItemViewType(int position) {
		if(multiType){
			Object count = jsRef.getEngine().call(jsRef, "getItemViewType", position);
			if (count == null) {
				return 0;
			}
			return ((Number) count).intValue();
		}
		return  super.getItemViewType(position);
	}
}
