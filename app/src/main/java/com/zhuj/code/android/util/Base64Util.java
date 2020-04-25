package com.jbzh.android.util;

import android.util.Base64;

public class Base64Util{
	private Base64Util() {
		throw new AssertionError("u can't instantiate me...");
	}

	public static String toBase64String(String str){
		return Base64.encodeToString(str.getBytes(), Base64.DEFAULT);
	}
	public static String toBase64String(byte[] input){
		return Base64.encodeToString(input, Base64.DEFAULT);
	}
	public static String decodeToString(String str){
		return new String(Base64.decode(str, Base64.DEFAULT));
	}
	public static byte[] decodeToBytes(String str){
		return Base64.decode(str, Base64.DEFAULT);
	}

	public static byte[] decodeToBytes(byte[] input){
		return Base64.decode(input, Base64.DEFAULT);
	}
}