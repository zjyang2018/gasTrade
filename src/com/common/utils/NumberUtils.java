package com.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.common.exception.CommonException;

/**
 * 本类为：数字类函数
 */
public class NumberUtils {

	private static Logger logger = Logger.getLogger( NumberUtils.class );

	/**
	 * 格式化百分数
	 * 
	 * @param inputString:传入字符串
	 * @return
	 * @throws CommonException
	 */
	public static String formatPercent(String inputString) throws CommonException {
		BigDecimal bigDecimal = stringToBigDecimal( inputString, 4 );
		return formatPercent( bigDecimal );
	}

	/**
	 * 格式化百分数
	 * 
	 * @param bigDecimal:大数据转换类
	 * @return
	 * @throws CommonException
	 */
	public static String formatPercent(BigDecimal bigDecimal) throws CommonException {
		return bigDecimalToString( bigDecimal, "##.00%" );
	}

	/**
	 * 格式化百分数
	 * 
	 * @param bigDecimal:大数据转换类
	 * @return
	 * @throws CommonException
	 */
	public static String formatPercent(Double inputNumber) throws CommonException {
		String inputString = inputNumber.toString();
		BigDecimal bigDecimal = stringToBigDecimal( inputString );
		return bigDecimalToString( bigDecimal, "##.00%" );
	}

	/**
	 * BigDecimal转换为String(默认转换为小数2位)
	 * 
	 * @param bigDecimal:大数据转换类
	 * @return
	 * @throws CommonException
	 */
	public static String bigDecimalToString(BigDecimal bigDecimal) throws CommonException {
		return bigDecimalToString( bigDecimal, "##.00" );
	}

	/**
	 * BigDecimal转换为String
	 * 
	 * @param bigDecimal:大数据转换类
	 * @return
	 * @throws CommonException
	 */
	public static String bigDecimalToString(BigDecimal bigDecimal, String pattern) throws CommonException {
		try {
			DecimalFormat format = new DecimalFormat( pattern );
			return format.format( bigDecimal );
		} catch (Exception ex) {
			String message = "BigDecimal转换为String发生异常,传入数据:" + bigDecimal + ",传入的格式化参数为:" + pattern;
			logger.error( message + ",异常信息:", ex );
			throw new CommonException( message );
		}
	}

	/**
	 * String转换为BigDecimal(默认精确到小位2位)
	 * 
	 * @param inputString
	 * @return
	 * @throws CommonException
	 */
	public static BigDecimal stringToBigDecimal(String inputString) throws CommonException {
		return stringToBigDecimal( inputString, 2 );
	}

	/**
	 * String转换为BigDecimal
	 * 
	 * @param inputString
	 * @param scale:精确的小数位
	 * @return
	 * @throws CommonException
	 */
	public static BigDecimal stringToBigDecimal(String inputString, int scale) throws CommonException {
		try {
			BigDecimal bigDecimal = new BigDecimal( inputString );
			return bigDecimal.setScale( scale, BigDecimal.ROUND_HALF_UP );
		} catch (Exception ex) {
			String message = "String转换为BigDecimal发生异常,传入的字符串参数为:" + inputString;
			logger.error( message + ",异常信息:", ex );
			throw new CommonException( message );
		}
	}

	/**
	 * Double转换为BigDecimal
	 * 
	 * @param inputString
	 * @param scale:精确的小数位
	 * @return
	 * @throws CommonException
	 */
	public static BigDecimal doubleToBigDecimal(Double doubleValue, int scale) throws CommonException {
		try {
			BigDecimal bigDecimal = new BigDecimal( doubleValue );
			return bigDecimal.setScale( scale, BigDecimal.ROUND_HALF_UP );
		} catch (Exception ex) {
			String message = "Double转换为BigDecimal发生异常,传入的字符串参数为:" + doubleValue;
			logger.error( message + ",异常信息:", ex );
			throw new CommonException( message );
		}
	}

	/**
	 * 比较两个金额大小范围
	 * 
	 * @param minAmount
	 * @param maxAmount
	 * @throws CommonException
	 */
	public static void checkNumberSection(String minAmount, String maxAmount) throws CommonException {
		Double minValue = null;
		Double maxValue = null;
		try {
			minValue = Double.valueOf( minAmount );
		} catch (Exception ex) {
			String message = "最小金额:" + minAmount + "不正确";
			logger.error( message + ",异常信息:", ex );
			throw new CommonException( message );
		}
		try {
			maxValue = Double.valueOf( maxAmount );
		} catch (Exception ex) {
			String message = "最大金额:" + maxAmount + "不正确";
			logger.error( message + ",异常信息:", ex );
			throw new CommonException( message );
		}
		if (minValue > maxValue) {
			String message = "最小金额:" + minAmount + "不能大于最大金额:" + maxAmount;
			throw new CommonException( message );
		}
	}

	/**
	 * 
	 * doubleToBigDecimal:(这里用一句话描述这个方法的作用). <br/>
	 * 
	 * @author William
	 * @return
	 * @since JDK 1.7
	 */
	public static BigDecimal defaultBigDecimal(BigDecimal amount) {
		if (amount == null) {
			return new BigDecimal( 0 );
		}
		return amount;
	}
}
