package com.common.filter.util;

import java.util.Random;

public class MSGCodeGenerator {
	private static final char[] pool = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

	public static String generater() {
		Random random = new Random();
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			int index = random.nextInt( 10 );
			buff.append( pool[ index ] );
		}
		return buff.toString().toUpperCase();
	}

	public static String generater(int length) {
		if (length < 0) {
			return "";
		}
		Random random = new Random();
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt( 10 );
			buff.append( pool[ index ] );
		}
		return buff.toString().toUpperCase();
	}
}
