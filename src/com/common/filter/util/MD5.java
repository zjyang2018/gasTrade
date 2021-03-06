/**
 * Title: MD5.java
 * Description:
 * Copyright: Copyright (c) 2013-2015 luoxudong.com
 * Company:  个人
 * Author 罗旭东 (hi@luoxudong.com)
 * Date 2014-7-15 上午9:55:08
 * Version 1.0
 */
package com.common.filter.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.common.constant.CommonConstant;

/**
 * ClassName: MD5 Description: Create by 罗旭东 Date 2014-7-15 上午9:55:08
 */
public class MD5 {
	private static final char[] HEX_DIGITS = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };

	public static String hexdigest(String content) {
		if (content == null) {
			return null;
		}

		try {
			return hexdigest( content.getBytes( CommonConstant.UTF_8 ) );
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static String hexdigest(byte[] input) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance( "MD5" );
		messageDigest.update( input );
		byte[] buffer = messageDigest.digest();
		return bytesToStr( buffer );
	}

	private static String bytesToStr(byte[] buffer) {
		char[] resultBuffer = new char[32];
		int i = 0;
		int j = 0;
		while (true) {
			if (i >= 16)
				return new String( resultBuffer );
			int k = buffer[ i ];
			int m = j + 1;
			resultBuffer[ j ] = HEX_DIGITS[ (0xF & k >>> 4) ];
			j = m + 1;
			resultBuffer[ m ] = HEX_DIGITS[ (k & 0xF) ];
			i++;
		}
	}

	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance( "MD5" );
			in = new FileInputStream( file );
			while ((len = in.read( buffer, 0, 1024 )) != -1) {
				digest.update( buffer, 0, len );
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return bytesToStr( digest.digest() );
	}
}
