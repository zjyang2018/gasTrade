/**
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: Gjfax.com</p>
 */
package com.common.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 文件名称： ApplicationContextHolder.java 功能说明： 开发人员： liangqf002 开发时间：2015年8月20日
 * 上午10:41:13 修改记录：修改日期 修改人员 修改说明
 */
public class ApplicationContextHolder implements ApplicationContextAware {

	/**
	 * Spring应用上下文环境
	 */
	private static ApplicationContext applicationContext;

	/**
	 * <p>
	 * Title: setApplicationContext
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param arg0
	 * @throws BeansException
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextHolder.applicationContext = applicationContext;
	}

	/**
	 * 获取Spring上下文
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取bean
	 * 
	 * @param name
	 * @return
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException {
		return applicationContext.getBean( name );
	}

	/**
	 * 获取bean
	 * 
	 * @param name
	 * @param requiredType
	 * @return
	 * @throws BeansException
	 */
	public static Object getBean(String name, Class requiredType) throws BeansException {
		return applicationContext.getBean( name, requiredType );
	}

	/**
	 * 判断上限为是否存在bean
	 * 
	 * @param name
	 * @return
	 */
	public static boolean containsBean(String name) {
		return applicationContext.containsBean( name );
	}

	/**
	 * 判断bean 是否是单例
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.isSingleton( name );
	}

	/**
	 * 获取bean的类型
	 * 
	 * @param name
	 * @return
	 */
	public static Class getType(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getType( name );
	}

	/**
	 * 获取bean的别名
	 * 
	 * @param name
	 * @return
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
		return applicationContext.getAliases( name );
	}

}
