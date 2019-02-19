package com.common.seq;

import java.util.UUID;

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
}
