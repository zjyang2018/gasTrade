package com.zach.gasTrade.common;

public class DataResult extends Result {

	private Object data;

	public DataResult() {

	}

	public DataResult(Integer code) {
		super(code);
	}

	public DataResult(Integer code, String msg) {
		super(code, msg);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static DataResult initResult() {

		return new DataResult(0, "success!");
	}
}
