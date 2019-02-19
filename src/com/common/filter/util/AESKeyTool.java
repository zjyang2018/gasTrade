package com.common.filter.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESKeyTool {
	public static SecretKey getDesKey(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchProviderException {
		KeyGenerator kgen = KeyGenerator.getInstance( "AES" );
		SecureRandom sr = SecureRandom.getInstance( "SHA1PRNG", "SUN" );
		sr.setSeed( password.getBytes( "UTF-8" ) );
		kgen.init( 128, sr );
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		return new SecretKeySpec( enCodeFormat, "AES" );
	}
}
