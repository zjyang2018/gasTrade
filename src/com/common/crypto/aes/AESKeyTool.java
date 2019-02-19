package com.common.crypto.aes;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESKeyTool {
	public static SecretKey getDesKey(String password) throws NoSuchAlgorithmException {
		KeyGenerator kgen = KeyGenerator.getInstance( "AES" );
		kgen.init( 128, new SecureRandom( password.getBytes() ) );
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		return new SecretKeySpec( enCodeFormat, "AES" );
	}
}
