package com.common.crypto.aes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * 
 * @author Feng
 *
 */
public class AESCrypto {
	private static Logger	logger		= Logger.getLogger( AESCrypto.class );

	private static String	encoding	= "utf-8";

	public static String encode(String plainText, String password) {
		try {
			byte[] bytes = plainText.getBytes( encoding );
			return Base64.encodeBase64String( encode( bytes, AESKeyTool.getDesKey( password ) ) );
		} catch (Exception e) {
			logger.error( "加密出错", e );
			throw new RuntimeException( "加密出错" );
		}
	}

	private static byte[] encode(byte[] plainBytes, SecretKey desKey) {
		try {
			Cipher cipher = Cipher.getInstance( "AES" );
			cipher.init( Cipher.ENCRYPT_MODE, desKey );
			return cipher.doFinal( plainBytes );
		} catch (Exception e) {
			logger.error( "加密出错", e );
			throw new RuntimeException( "加密出错" );
		}
	}

	public static String decode(String cipherText, String password) {
		try {
			byte[] bytes = cipherText.getBytes( encoding );
			return new String( decode( Base64.decodeBase64( bytes ), AESKeyTool.getDesKey( password ) ) );
		} catch (Exception e) {
			logger.error( "解密出错", e );
			throw new RuntimeException( "解密出错" );
		}
	}

	private static byte[] decode(byte[] cipherBytes, SecretKey desKey) {
		try {
			Cipher cipher = Cipher.getInstance( "AES" );
			cipher.init( Cipher.DECRYPT_MODE, desKey );
			return cipher.doFinal( cipherBytes );
		} catch (Exception e) {
			logger.error( "解密出错", e );
			throw new RuntimeException( "解密出错" );
		}
	}

}
