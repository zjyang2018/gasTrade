package com.common.context;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * 
 * @Project common-utils
 * @Description:
 * @Company youku
 * @Create 2014年12月18日下午9:08:24
 * @author zhengyi
 * @version 1.0 Copyright (c) 2014 youku, All Rights Reserved.
 */
@Service
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext;
	}

	/**
	 * 取得存储在静态变量中的ApplicationContext.
	 */
	public static ApplicationContext getApplicationContext() {
		checkApplicationContext();
		return applicationContext;
	}

	/**
	 * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean( name );
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name, String defaultName) {
		checkApplicationContext();
		Object bean = null;
		try {
			bean = applicationContext.getBean( name );
		} catch (NoSuchBeanDefinitionException e) {
			bean = applicationContext.getBean( defaultName );
		}
		return (T) bean;
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException( "applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtil" );
		}
	}

}
