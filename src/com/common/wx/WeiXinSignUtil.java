package com.common.wx;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.UUID;

public class WeiXinSignUtil {

	private static String token = "1314wxafd1d8527f4f2020";

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
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));
			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}
}