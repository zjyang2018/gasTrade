package com.zach.gasTrade.netpay.xiaomage;

import java.security.MessageDigest;

public class MD5 {
	/**
	 * MD5处理
	 * 
	 * @param str2sign
	 *            待签名字符串
	 * @return 签名结果sign
	 */
	public static String MD5(String str2sign) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(str2sign.getBytes("utf-8"));
			return toHex(bytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 预处理
	public static String toHex(byte[] bytes) {
		final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
		StringBuilder ret = new StringBuilder(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
			ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
		}
		return ret.toString();
	}
}
