package com.common.seq;

import java.util.UUID;

import com.common.cache.CacheService;
import com.common.context.SpringContextHolder;
import com.common.utils.DateTimeUtils;

public class SerialGenerator {

	public final static IdWorker idWorker = new IdWorker(2, 3);

	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "");
	}

	public static String getSerialNum() {
		String preStr = "";
		try {
			preStr = DateTimeUtils.getCurrentDate("yyyyMMddHHmmss");
		} catch (RuntimeException e) {

		}
		return preStr + idWorker.nextId();
	}

	public static String getOrderId() {
		CacheService cacheService = SpringContextHolder.getBean("redisCacheService");
		String orderId = cacheService.get("orderId");
		if (orderId == null || orderId.isEmpty()) {
			String preStr = DateTimeUtils.getCurrentDate("yyyyMMdd");
			orderId = preStr + "P" + "0000001";
		} else {
			String preStr = DateTimeUtils.getCurrentDate("yyyyMMdd");
			String[] orderIdStr = orderId.split("P");
			if (preStr.equals(orderIdStr[0])) {
				String numStr = orderIdStr[1];
				int num = Integer.valueOf(numStr) + 1;
				int length = String.valueOf(num).length();
				String numStrPre = "";
				for (int index = 0; index < 7 - length; index++) {
					numStrPre = numStrPre + "0";
				}
				orderId = preStr + "P" + numStrPre + String.valueOf(num);
			} else {
				orderId = preStr + "P" + "0000001";
			}
		}
		cacheService.add("orderId", orderId, 24 * 60 * 60);
		return orderId;
	}

	public static void main(String[] args) {
		String numxx = getSerialNum();
		System.out.println(numxx);
		String numStr = "1234214";
		int num = Integer.valueOf(numStr) + 1;
		int length = String.valueOf(num).length();
		String numStrPre = "";
		for (int index = 0; index < 7 - length; index++) {
			numStrPre = numStrPre + "0";
		}
		numStr = numStrPre + String.valueOf(num);
		System.out.println(numStr);
	}
}
