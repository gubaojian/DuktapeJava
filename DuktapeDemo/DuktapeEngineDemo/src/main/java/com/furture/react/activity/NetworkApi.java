package com.furture.react.activity;

import java.util.Locale;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkApi {

	public static final String WIFI = "wifi";
	public static final String WIMAX = "wimax";
	// mobile
	public static final String MOBILE = "mobile";

	// Android L calls this Cellular, because I have no idea!
	public static final String CELLULAR = "cellular";
	// 2G network types
	public static final String GSM = "gsm";
	public static final String GPRS = "gprs";
	public static final String EDGE = "edge";
	// 3G network types
	public static final String CDMA = "cdma";
	public static final String UMTS = "umts";
	public static final String HSPA = "hspa";
	public static final String HSUPA = "hsupa";
	public static final String HSDPA = "hsdpa";
	public static final String ONEXRTT = "1xrtt";
	public static final String EHRPD = "ehrpd";
	// 4G network types
	public static final String LTE = "lte";
	public static final String UMB = "umb";
	public static final String HSPA_PLUS = "hspa+";

	public static final String TYPE_UNKNOWN = "unknown";
	public static final String TYPE_ETHERNET = "ethernet";
	public static final String TYPE_WIFI = "wifi";
	public static final String TYPE_2G = "2g";
	public static final String TYPE_3G = "3g";
	public static final String TYPE_4G = "4g";
	public static final String TYPE_NONE = "none";

	private Context context;

	public NetworkApi(Context context) {
		super();
		this.context = context.getApplicationContext();
	}

	public String getNetworkType() {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isConnected()) {
			return TYPE_NONE;
		}
		String type = networkInfo.getTypeName().toLowerCase(Locale.US);

		if (type.equals(WIFI)) {
			return TYPE_WIFI;
		} else if (type.equals(TYPE_ETHERNET)) {
			return TYPE_ETHERNET;
		} else if (type.equals(MOBILE) || type.equals(CELLULAR)) {
			type = networkInfo.getSubtypeName().toLowerCase(Locale.US);
			if (type.equals(GSM) || type.equals(GPRS) || type.equals(EDGE)) {
				return TYPE_2G;
			} else if (type.startsWith(CDMA) || type.equals(UMTS)
					|| type.equals(ONEXRTT) || type.equals(EHRPD)
					|| type.equals(HSUPA) || type.equals(HSDPA)
					|| type.equals(HSPA)) {
				return TYPE_3G;
			} else if (type.equals(LTE) || type.equals(UMB)
					|| type.equals(HSPA_PLUS)) {
				return TYPE_4G;
			}
		}
		return TYPE_UNKNOWN;

	}

}
