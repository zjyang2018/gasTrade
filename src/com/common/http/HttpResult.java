package com.common.http;

public class HttpResult {

	private String	statusCode;

	private String	content;

	public String getStatusCode() {
		return statusCode;
	}

	public HttpResult setStatusCode(String statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	public String getContent() {
		return content;
	}

	public HttpResult setContent(String content) {
		this.content = content;
		return this;
	}

}
