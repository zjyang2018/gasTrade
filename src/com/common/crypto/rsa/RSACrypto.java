package com.common.crypto.rsa;

import java.io.ByteArrayOutputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSACrypto {
	private static Logger	logger			= Logger.getLogger( RSACrypto.class );
	static String			encoding		= "utf-8";
	static BASE64Encoder	Base64Encoder	= new BASE64Encoder();
	static BASE64Decoder	Base64Decoder	= new BASE64Decoder();
	static int				ENCRYPT_LENGTH	= 117;
	static int				DECRYPT_LENGTH	= 128;

	public static String encode(String text, RSAPublicKey publicKey, String encoding) {
		try {
			byte[] cipherBytes = encode( text.getBytes( encoding ), publicKey );
			return Base64Encoder.encode( cipherBytes );
		} catch (Exception e) {
			logger.error( "加密出错", e );
			throw new RuntimeException( "加密出错" );
		}
	}

	public static byte[] encode(byte[] plainBytes, RSAPublicKey publicKey) {
		try {
			Cipher cipher = Cipher.getInstance( "RSA/ECB/PKCS1Padding" );
			cipher.init( Cipher.ENCRYPT_MODE, publicKey );
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			for (int i = 0; i < plainBytes.length; i += ENCRYPT_LENGTH) {
				out.write( cipher.doFinal( plainBytes, i, i + ENCRYPT_LENGTH >= plainBytes.length ? plainBytes.length - i : ENCRYPT_LENGTH ) );
			}
			byte[] cipherBytes = out.toByteArray();
			out.close();
			return cipherBytes;
		} catch (Exception e) {
			logger.error( "加密出错", e );
			throw new RuntimeException( "加密出错" );
		}
	}

	public static String encode(String text, RSAPublicKey publicKey) {
		return encode( text, publicKey, encoding );
	}

	public static String decode(String text, RSAPrivateKey privateKey, String encoding) {
		try {
			byte[] cipherBytes = Base64Decoder.decodeBuffer( text );
			byte[] plainBytes = decode( cipherBytes, privateKey );
			return new String( plainBytes, encoding );
		} catch (Exception e) {
			logger.error( "解密出错", e );
			throw new RuntimeException( "解密出错" );
		}
	}

	public static byte[] decode(byte[] cipherBytes, RSAPrivateKey privateKey) {
		try {
			Cipher cipher = Cipher.getInstance( "RSA/ECB/PKCS1Padding" );
			cipher.init( Cipher.DECRYPT_MODE, privateKey );
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			for (int i = 0; i < cipherBytes.length; i += DECRYPT_LENGTH) {
				out.write( cipher.doFinal( cipherBytes, i, i + DECRYPT_LENGTH >= cipherBytes.length ? cipherBytes.length - i : DECRYPT_LENGTH ) );
			}
			byte[] plainBytes = out.toByteArray();
			out.close();
			return plainBytes;
		} catch (Exception e) {
			logger.error( "解密出错", e );
			throw new RuntimeException( "解密出错" );
		}
	}

	public static String decode(String text, RSAPrivateKey privateKey) {
		return decode( text, privateKey, encoding );
	}
}
