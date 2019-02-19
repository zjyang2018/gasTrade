/**
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Gjfax.com</p>
 */
package com.common.utils;

/**
 * 文件名称： ClassUtil.java 功能说明： 开发人员： liangqf002 开发时间：2015年8月25日 上午10:24:57
 * 修改记录：修改日期 修改人员 修改说明
 */
public class ClassUtil {
	public static ClassLoader getClassLoader() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = ClassUtil.class.getClassLoader();
		}
		return classLoader;
	}

	public static Class loadClass(String className) throws ClassNotFoundException {
		return getClassLoader().loadClass( className );
	}

	public static Object newInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		return loadClass( className ).newInstance();
	}
}
