package com.furture.react.hybrid;

import java.security.MessageDigest;

import android.util.Log;

public class Hash {

	
	 
   public static String md5(String message){ 
		 try { 
			  MessageDigest md = MessageDigest.getInstance("MD5");
			  byte[] hash = md.digest(message.getBytes("UTF-8")); //converting byte array to Hexadecimal
			  StringBuilder sb = new StringBuilder(2*hash.length); 
		      for(byte b : hash){ 
		    	  sb.append(String.format("%02x", b&0xff)); 
		      }
		      return sb.toString(); 
	    } catch (Exception e) { 
	    	 Log.e("Hash", "Md5 Hash Exception " + message);
	    	 return message.hashCode() + "";
	    } 
	}
}
