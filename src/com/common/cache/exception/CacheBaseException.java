package com.common.cache.exception;

/**
 * 
 * @Project usercenter-common
 * @Description: cache相关操作的基本异常
 * @Company youku
 * @Create 2015年8月2日下午9:44:43
 * @author zhoushaoyu
 * @version 1.0 Copyright (c) 2015 youku, All Rights Reserved.
 */
public abstract class CacheBaseException extends RuntimeException {

	private static final long serialVersionUID = -3476588591987609006L;

	public CacheBaseException() {
		super();
	}

	public CacheBaseException(String msg, Throwable cause) {
		super( msg, cause );
	}

	public CacheBaseException(String msg) {
		super( msg );
	}

	public CacheBaseException(Throwable cause) {
		super( cause );
	}

}
