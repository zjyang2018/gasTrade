package com.common.crypto.md5;

import java.security.MessageDigest;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Md5Signature {
	private static Logger	logger			= Logger.getLogger( Md5Signature.class );

	static BASE64Encoder	Base64Encoder	= new BASE64Encoder();
	static BASE64Decoder	Base64Decoder	= new BASE64Decoder();

	public static String sign(String text) {
		return sign( text, "utf-8" );
	}

	public static String sign(String text, String encoding) {
		try {
			MessageDigest md5 = MessageDigest.getInstance( "MD5" );
			md5.update( text.getBytes( encoding ) );
			return Base64Encoder.encode( md5.digest() );
		} catch (Exception e) {
			logger.error( "error when md5 sign: ", e );
			return null;
		}
	}
}
