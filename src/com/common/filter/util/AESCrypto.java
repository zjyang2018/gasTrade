package com.common.filter.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

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
			return Base64.encodeToString( encode( bytes, AESKeyTool.getDesKey( password ) ), Base64.NO_WRAP );
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
			bytes = Base64.decode( bytes, Base64.NO_WRAP );
			return new String( decode( bytes, AESKeyTool.getDesKey( password ) ), encoding );
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

	public static void main(String[] args) {
		String encodeStr = encode( "123456", "QWERTYUIOPLKJHGFDSAZXCVBNM0123456789ASDFGHJKLMNBVCXZ1234567890OK" );

		System.out.println( encodeStr );

		String decodeStr = decode( "MBEDQkGeP+rW7CbKB42A8w==", "QWERTYUIOPLKJHGFDSAZXCVBNM0123456789ASDFGHJKLMNBVCXZ1234567890OK" );

		System.out.println( decodeStr );
	}
}
