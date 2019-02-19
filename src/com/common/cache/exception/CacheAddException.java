package com.common.cache.exception;

/**
 * 
 * @Project usercenter-common
 * @Description: 缓存添加失败异常
 * @Company youku
 * @Create 2015年8月2日下午9:45:21
 * @author zhoushaoyu
 * @version 1.0 Copyright (c) 2015 youku, All Rights Reserved.
 */
public class CacheAddException extends CacheBaseException {

	private static final long serialVersionUID = 8598405794295828698L;

	public CacheAddException() {
		super();
	}

	public CacheAddException(String msg, Throwable cause) {
		super( msg, cause );
	}

	public CacheAddException(String msg) {
		super( msg );
	}

	public CacheAddException(Throwable cause) {
		super( cause );
	}

}
