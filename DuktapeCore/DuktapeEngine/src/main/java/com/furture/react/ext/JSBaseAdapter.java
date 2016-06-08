package com.furture.react.ext;

import com.furture.react.JSRef;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class JSBaseAdapter extends BaseAdapter{

	private JSRef jsRef;

	public JSBaseAdapter(JSRef jsRef) {
		this.jsRef = jsRef;
	}


	@Override
	public int getCount() {
		Object count = jsRef.getEngine().call(jsRef, "getCount");
		if (count == null) {
			return 0;
		}
		return (Integer) count;
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

}
