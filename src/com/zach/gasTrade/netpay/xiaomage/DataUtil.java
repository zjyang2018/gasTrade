package com.zach.gasTrade.netpay.xiaomage;

import java.util.Calendar;
import java.util.UUID;

class DataUtil {// 全局参数的处理获取方法
	/**
	 * 获取设备标识
	 * 
	 * @param DockingDeviceID
	 *            对接注册设备标识开头
	 * @param UserStr
	 *            用户自定义字段
	 * @return DeviceID
	 */
	static String GetDeviceID(String DockingDeviceID, String UserStr) {
		// 拼接DockingDeviceID+用户字符
		return DockingDeviceID + UserStr;
	}

	/**
	 * 获取UTC时间戳
	 * 
	 * @return timestamp
	 */
	static String GetTimestamp() {
		// 返回UTC时间（精确到秒）
		return String.valueOf(Calendar.getInstance().getTimeInMillis() / 1000);
	}

	/**
	 * 生成32位随机字符串
	 * 
	 * @return nonceStr
	 */
	static String GetNonceStr() {
		return UUID.randomUUID().toString().trim().replaceAll("-", "");
	}

	/**
	 * 获取签名
	 * 
	 * @param stringA
	 *            非空参数字符串
	 * @param DockingSecret
	 *            对接秘钥
	 * @return Sign
	 */
	static String GetSign(String stringA, String DockingSecret) {
		System.out.println((stringA + "&" + DockingSecret).toLowerCase());
		return MD5.MD5((stringA + "&" + DockingSecret).toLowerCase());
	}
}
