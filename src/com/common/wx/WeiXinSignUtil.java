package com.common.wx;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.UUID;

public class WeiXinSignUtil {

	private static String token = "u8PyrF9R88wM6p96jYnJyszz0sZ0s0R8";

	/**
	 * 校验签名
	 * 
	 * @param signature
	 *            签名
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机数
	 * @return 布尔值
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		String checktext = null;
		if (null != signature) {
			// 对ToKen,timestamp,nonce 按字典排序
			String[] paramArr = new String[] { token, timestamp, nonce };
			Arrays.sort(paramArr);
			// 将排序后的结果拼成一个字符串
			String content = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

			checktext = WeiXinSignUtil.sha1Hex(content);
		}
		// 将加密后的字符串与signature进行对比
		return checktext != null ? checktext.equals(signature.toUpperCase()) : false;
	}

	// public static void main(String[] args) {
	// //
	// signature=8e07f66e22aeb2995cd2fe733345df15c0bb7ce2&echostr=2420431578604568574&timestamp=1553227518&nonce=1432642647
	// String signature = "8e07f66e22aeb2995cd2fe733345df15c0bb7ce2";
	// String echostr = "2420431578604568574";
	// String timestamp ="1553227518";
	// String nonce="1432642647";
	// if(checkSignature(signature,timestamp,nonce)) {
	// System.out.println("签名通过");
	// }else {
	// System.out.println("签名错误");
	// }
	// }

	/**
	 * 生成签名的随机串
	 * 
	 * @return
	 */
	public static String getNonceStr() {
		return UUID.randomUUID().toString().replace("-", "").substring(0, 32);
	}

	/**
	 * 生成签名的时间戳
	 * 
	 * @return
	 */
	public static String getTimeStamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	/**
	 * 将字符串进行sha1加密
	 * 
	 * @return
	 */
	public static String sha1Hex(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			// 对接后的字符串进行sha1加密
			byte[] digest = md.digest(str.getBytes());
			return byteToStr(digest);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 将字节数组转化我16进制字符串
	 * 
	 * @param byteArrays
	 *            字符数组
	 * @return 字符串
	 */
	private static String byteToStr(byte[] byteArrays) {
		String str = "";
		for (int i = 0; i < byteArrays.length; i++) {
			str += byteToHexStr(byteArrays[i]);
		}
		return str;
	}

	/**
	 * 将字节转化为十六进制字符串
	 * 
	 * @param myByte
	 *            字节
	 * @return 字符串
	 */
	private static String byteToHexStr(byte myByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tampArr = new char[2];
		tampArr[0] = Digit[(myByte >>> 4) & 0X0F];
		tampArr[1] = Digit[myByte & 0X0F];
		String str = new String(tampArr);
		return str;
	}
}