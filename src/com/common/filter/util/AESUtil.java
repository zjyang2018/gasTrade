/**
 * Title: AESUtil.java
 * Description: 
 * Copyright: Copyright (c) 2013-2015 luoxudong.com
 * Company: 个人
 * Author: 罗旭东 (hi@luoxudong.com)
 * Date: 2016年1月8日 下午12:08:10
 * Version: 1.0
 */
package com.common.filter.util;

import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.common.constant.CommonConstant;

/**
 * <pre>
 * ClassName: AESUtil
 * Description:
 * Create by: 罗旭东
 * Date: 2016年1月8日 下午12:08:10
 * </pre>
 */
public class AESUtil {
	/** IV容器 */
	private static final byte[]	IV						= "ujhfwe9ihv0as89w".getBytes( CommonConstant.UTF_8 );

	/** CBC加密模式 */
	private static final String	CIPHER_ALGORITHM_CBC	= "AES/CBC/PKCS5Padding";								// ZeroBytePadding
																												// NoPadding
																												// PKCS5Padding

	static final String			KEY_ALGORITHM			= "AES";

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) throws Exception {
		byte[] bt = encrypt( data.getBytes( CommonConstant.UTF_8 ), get16ByteKey( key ).getBytes( CommonConstant.UTF_8 ) );
		String strs = Base64.encodeToString( bt, Base64.NO_WRAP );
		return strs;
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decrypt(String data, String key) throws IOException, Exception {
		if (data == null)
			return null;
		byte[] buf = Base64.decode( data, Base64.NO_WRAP );
		byte[] bt = decrypt( buf, get16ByteKey( key ).getBytes( CommonConstant.UTF_8 ) );
		return new String( bt, CommonConstant.UTF_8 );
	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec( key, KEY_ALGORITHM );
		Cipher cipher = Cipher.getInstance( CIPHER_ALGORITHM_CBC );
		cipher.init( Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec( IV ) );
		return cipher.doFinal( data );
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec( key, KEY_ALGORITHM );
		Cipher cipher = Cipher.getInstance( CIPHER_ALGORITHM_CBC );
		cipher.init( Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec( IV ) );// 使用解密模式初始化
																				// 密钥
		return cipher.doFinal( data );
	}

	private static String get16ByteKey(String key) {
		String keyTemp = MD5.hexdigest( key );
		keyTemp = keyTemp.substring( 8, 24 ).toUpperCase();
		return keyTemp;
	}
}
