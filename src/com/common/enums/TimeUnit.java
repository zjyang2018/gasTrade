package com.common.enums;

/**
 * 本类为：时间单元枚举类
 */
public enum TimeUnit {

	YEAR("年"), MONTH("月"), DAY("日"), HOUR("时"), MINUTE("分"), SECONDE("秒");

	private String desc;

	public String getDesc() {
		return desc;
	}

	private TimeUnit(String desc) {
		this.desc = desc;
	}
}
