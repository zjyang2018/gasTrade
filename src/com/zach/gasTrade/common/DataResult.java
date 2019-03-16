package com.zach.gasTrade.common;

public class DataResult<T> extends Result {

	private T data;

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

	public void setData(T data) {
		this.data = data;
	}

	public static DataResult initResult() {

		return new DataResult(0, "success!");
	}
}
