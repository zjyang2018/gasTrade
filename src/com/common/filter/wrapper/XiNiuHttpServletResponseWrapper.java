package com.common.filter.wrapper;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class XiNiuHttpServletResponseWrapper extends javax.servlet.http.HttpServletResponseWrapper {

	private int statusCode;

	public XiNiuHttpServletResponseWrapper(HttpServletResponse response) {
		super( response );
		this.statusCode = HttpServletResponse.SC_OK; // 默认的状态是200
	}

	public void sendError(int sc) throws IOException {
		statusCode = sc;
		super.sendError( sc );
	}

	public void sendError(int sc, String msg) throws IOException {
		statusCode = sc;
		super.sendError( sc, msg );
	}

	public void setStatus(int sc) {
		this.statusCode = sc;
		super.setStatus( sc );
	}

	public int getStatusCode() {
		return this.statusCode;
	}
}
