package com.furture.react.hybrid;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.util.Log;

public class ZipUtils {
	
	public static void unzip(File zipFile, File targetDirectory) throws IOException {
	    ZipInputStream zipInputStream = null;
	    try {
	       	zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)));
	        ZipEntry ze;
	        int count;
	        byte[] buffer = new byte[8192];
	        while ((ze = zipInputStream.getNextEntry()) != null) {
	           	String name = ze.getName();
	         	Log.e("HybirdName", "HybirdName" + name);
	            File file = new File(targetDirectory, name);
	            File dir = ze.isDirectory() ? file : file.getParentFile();
	            if (!dir.isDirectory() && !dir.mkdirs())
	                throw new FileNotFoundException("Failed to ensure directory: " + dir.getAbsolutePath());
	            if (ze.isDirectory())
	                continue;
	            FileOutputStream fout = new FileOutputStream(file);
	            try {
	                while ((count = zipInputStream.read(buffer)) != -1)
	                    fout.write(buffer, 0, count);
	            } finally {
	                fout.close();
	            }
	        }
	    } finally {
	        zipInputStream.close();
	    }
	}

}
