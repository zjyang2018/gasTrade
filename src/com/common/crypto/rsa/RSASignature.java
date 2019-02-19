package com.common.crypto.rsa;

import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSASignature {

	private static Logger	logger			= Logger.getLogger( RSASignature.class );
	private static String	encoding		= "utf-8";

	static BASE64Encoder	Base64Encoder	= new BASE64Encoder();
	static BASE64Decoder	Base64Decoder	= new BASE64Decoder();

	public static String sign(String plainText, RSAPrivateKey key) {
		return sign( plainText, key, encoding );
	}

	public static String sign(String plainText, RSAPrivateKey key, String encoding) {
		try {
			Signature signature = Signature.getInstance( "MD5WithRSA" );
			signature.initSign( key );
			signature.update( plainText.getBytes( encoding ) );
			return Base64Encoder.encode( signature.sign() );
		} catch (Exception e) {
			logger.error( "error when sign:", e );
		}
		return null;
	}

	public static boolean verify(String cipherText, String plainText, RSAPublicKey key) {
		return verify( cipherText, plainText, key, encoding );
	}

	public static boolean verify(String cipherText, String plainText, RSAPublicKey key, String encoding) {
		try {
			Signature verifier = Signature.getInstance( "MD5WithRSA" );
			verifier.initVerify( key );
			verifier.update( plainText.getBytes( encoding ) );
			return verifier.verify( Base64Decoder.decodeBuffer( cipherText ) );
		} catch (Exception e) {
			logger.error( "error when verify:", e );
		}
		return false;
	}
}
