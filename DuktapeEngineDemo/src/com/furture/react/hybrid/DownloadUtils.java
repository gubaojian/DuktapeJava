package com.furture.react.hybrid;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;

public class DownloadUtils {

	
	public static void downloadZip(URL url, File saveFile) throws Exception{
		URLConnection connection = url.openConnection();
		connection.setUseCaches(false);
		connection.setDefaultUseCaches(false);
		connection.connect();
		if (connection instanceof HttpURLConnection) {
			HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
			httpURLConnection.setConnectTimeout(1000*16);
			httpURLConnection.setReadTimeout(1000*8);
			if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				 Log.w("DownloadTask", url + "Server returned HTTP " + httpURLConnection.getResponseCode() + " " + httpURLConnection.getResponseMessage());
				throw new RuntimeException(url + "Server returned HTTP " + httpURLConnection.getResponseCode() + " " + httpURLConnection.getResponseMessage());
            }
		}
		InputStream inputStream =  null; 
		try {
			inputStream =  connection.getInputStream();
			File  file = new File(saveFile.getAbsolutePath() + ".tmp");
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] bts = new byte[1024];
			int count = -1;
			while ((count = inputStream.read(bts)) >= 0) {
				fileOutputStream.write(bts, 0, count);
			}
			fileOutputStream.close();
			file.renameTo(saveFile);
        }finally {
				if (inputStream != null) {
					inputStream.close();
				}
	    }
	}
}
