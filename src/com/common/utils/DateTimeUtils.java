package com.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.common.enums.TimeUnit;

/**
 * 
 * @author Feng
 * 
 */
public class DateTimeUtils {

	private static Logger logger = Logger.getLogger(DateTimeUtils.class);

	/**
	 * 获取当前日期(默认:yyyyMMdd)
	 * 
	 * @return
	 * @throws RuntimeException
	 */
	public static String getCurrentDate() throws RuntimeException {
		String pattern = "yyyyMMdd";
		return getCurrentDate(pattern);
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 * @throws RuntimeException
	 */
	public static Date getCurrentTime() throws RuntimeException {
		return new Date();
	}

	/**
	 * 获取当前的日期时间(默认:yyyy-MM-dd HH:mm:sss)
	 * 
	 * @return
	 */
	public static String getCurrentDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(Calendar.getInstance().getTime());
	}

	/**
	 * 获取当前的日期时间(默认:yyyy-MM-dd HH:mm:sss)
	 * 
	 * @return
	 */
	public static Timestamp getCurrentDateTimeStamp() throws RuntimeException {
		String currentDateTime = getCurrentDateTime();
		try {
			return Timestamp.valueOf(currentDateTime);
		} catch (Exception ex) {
			String message = "将传入字符串" + currentDateTime + "转换为TimeStamp类型发生异常";
			logger.error(message + ",异常信息:", ex);
			throw new RuntimeException(message);
		}
	}

	/**
	 * 获取当前日期
	 * 
	 * @param pattern
	 * @return
	 * @throws RuntimeException
	 */
	public static String getCurrentDate(String pattern) throws RuntimeException {
		Calendar calendar = Calendar.getInstance();
		return calendarToString(calendar, pattern);
	}

	/**
	 * 将String类型转换为Date类型
	 * 
	 * @param inputString
	 * @param pattern
	 * @return
	 * @throws RuntimeException
	 */
	public static Date stringToDate(String inputString, String pattern)
			throws RuntimeException {
		try {
			DateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.parse(inputString);
		} catch (Exception ex) {
			String message = "将传入字符串" + inputString + "转换Date类型发生异常,传入的格式化参数为:"
					+ pattern;
			logger.error(message + ",异常信息:", ex);
			throw new RuntimeException(message);
		}
	}

	/**
	 * 将String类型转换为Calendar类型
	 * 
	 * @param inputString
	 * @param pattern
	 * @return
	 * @throws RuntimeException
	 */
	public static Calendar stringToCalendar(String inputString, String pattern)
			throws RuntimeException {
		try {
			Date date = stringToDate(inputString, pattern);
			Calendar calendar = dateToCalendar(date);
			return calendar;
		} catch (Exception ex) {
			String message = "将传入字符串" + inputString
					+ "转换Calendar类型发生异常,传入的格式化参数为:" + pattern;
			logger.error(message + ",异常信息:", ex);
			throw new RuntimeException(message);
		}
	}

	/**
	 * 将Date类型转换为String类型
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 * @throws RuntimeException
	 */
	public static String dateToString(Date date, String pattern)
			throws RuntimeException {
		try {
			return DateFormatUtils.format(date, pattern);
		} catch (Exception ex) {
			String message = "将Date类型转换为String类型发生异常,传入的格式化参数为:" + pattern;
			logger.error(message + ",异常信息:", ex);
			throw new RuntimeException(message);
		}
	}

	/**
	 * 将Date类型转换为Calendar类型
	 * 
	 * @param date
	 * @return
	 * @throws RuntimeException
	 */
	public static Calendar dateToCalendar(Date date) throws RuntimeException {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		} catch (Exception ex) {
			String message = "将Date类型转换为Calendar类型发生异常";
			logger.error(message + ",异常信息:", ex);
			throw new RuntimeException(message);
		}
	}

	/**
	 * 将Calendar类型转换为Date类型
	 * 
	 * @param calendar
	 * @return
	 * @throws RuntimeException
	 */
	public static Date calendarToDate(Calendar calendar)
			throws RuntimeException {
		try {
			return calendar.getTime();
		} catch (Exception ex) {
			String message = "将Calendar类型转换为Date类型发生异常";
			logger.error(message + ",异常信息:", ex);
			throw new RuntimeException(message);
		}
	}

	/**
	 * 将Calendar类型转换为String类型
	 * 
	 * @param calendar
	 * @param pattern
	 * @return
	 * @throws RuntimeException
	 */
	public static String calendarToString(Calendar calendar, String pattern)
			throws RuntimeException {
		try {
			Date date = calendar.getTime();
			return dateToString(date, pattern);
		} catch (Exception ex) {
			String message = "将Calendar类型转换为String类型发生异常,传入的格式化参数为:" + pattern;
			logger.error(message + ",异常信息:", ex);
			throw new RuntimeException(message);
		}
	}

	/**
	 * String类加减时间处理(转换为String)
	 * 
	 * @param inputString
	 * @param pattern
	 * @param timeUnit
	 * @param amount
	 * @return
	 * @throws GalaxyValidateException
	 */
	public static String addDateTime(String inputString, String pattern,
			TimeUnit timeUnit, int amount) throws RuntimeException {
		Calendar calendar = addDateTimeToCalendar(inputString, pattern,
				timeUnit, amount);
		return calendarToString(calendar, pattern);
	}

	/**
	 * String类加减时间处理(转换为Date)
	 * 
	 * @param inputString
	 * @param pattern
	 * @param timeUnit
	 * @param amount
	 * @return
	 * @throws RuntimeException
	 */
	public static Date addDateTimeToDate(String inputString, String pattern,
			TimeUnit timeUnit, int amount) throws RuntimeException {
		Calendar calendar = addDateTimeToCalendar(inputString, pattern,
				timeUnit, amount);
		return calendar.getTime();
	}

	/**
	 * String类加减时间处理(转换为Calendar)
	 * 
	 * @param inputString
	 * @param pattern
	 * @param timeUnit
	 * @param amount
	 * @return
	 * @throws RuntimeException
	 */
	public static Calendar addDateTimeToCalendar(String inputString,
			String pattern, TimeUnit timeUnit, int amount)
			throws RuntimeException {
		Calendar calendar = stringToCalendar(inputString, pattern);
		return addDateTime(calendar, timeUnit, amount);
	}

	/**
	 * Date类加减时间处理
	 * 
	 * @param date
	 * @param timeUnit
	 * @param amount
	 * @return
	 * @throws GalaxyValidateException
	 */
	public static Date addDateTime(Date date, TimeUnit timeUnit, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return addDateTime(calendar, timeUnit, amount).getTime();
	}

	/**
	 * Calendar类加减时间处理
	 * 
	 * @param calendar
	 *            :传入时间
	 * @param timeUnit
	 *            :时间单元
	 * @param amount
	 *            :数字
	 * @return
	 * @throws RuntimeException
	 */
	public static Calendar addDateTime(Calendar calendar, TimeUnit timeUnit,
			int amount) {
		Calendar cloneCalendar = (Calendar) calendar.clone();
		switch (timeUnit) {
		case YEAR:
			cloneCalendar.add(Calendar.YEAR, amount);
			break;
		case MONTH:
			cloneCalendar.add(Calendar.MONTH, amount);
			break;
		case DAY:
			cloneCalendar.add(Calendar.DATE, amount);
			break;
		case HOUR:
			cloneCalendar.add(Calendar.HOUR_OF_DAY, amount);
			break;
		case MINUTE:
			cloneCalendar.add(Calendar.MINUTE, amount);
			break;
		case SECONDE:
			cloneCalendar.add(Calendar.SECOND, amount);
			break;
		default:
			break;
		}
		return cloneCalendar;
	}

	/**
	 * 格式化当天时间
	 * 
	 * @param format
	 *            :传入格式(如:yyyy-MM-dd)
	 * @return
	 * @throws RuntimeException
	 */
	public static String toCurrentDateTime(String pattern) {
		try {
			Date date = new Date();
			return dateToString(date, pattern);
		} catch (Exception ex) {
			String message = "格式化当天时间发生异常,传入的格式化参数为:" + pattern;
			logger.error(message + ",格式化异常信息:", ex);
			throw new RuntimeException(message);
		}
	}

	/**
	 * 验证起始结束时间
	 * 
	 * @param startDate
	 * @param endDate
	 * @throws RuntimeException
	 */
	public static void checkDateTimeSection(String startDate, String endDate,
			String pattern) throws RuntimeException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		Calendar startCalendar = null;
		Calendar endCalendar = null;
		try {
			startCalendar = dateToCalendar(dateFormat.parse(startDate));
		} catch (Exception ex) {
			String message = "格式化起始时间发生异常,传入的格式化参数为:" + pattern + ",起始时间为:"
					+ startDate;
			logger.error(message + ",格式化异常信息:", ex);
			throw new RuntimeException(message);
		}
		try {
			endCalendar = dateToCalendar(dateFormat.parse(endDate));
		} catch (Exception ex) {
			String message = "格式化结束时间发生异常,传入的格式化参数为:" + pattern + ",起始时间为:"
					+ endDate;
			logger.error(message + ",格式化异常信息:", ex);
			throw new RuntimeException(message);
		}
		if (startCalendar.getTimeInMillis() > endCalendar.getTimeInMillis()) {
			String message = "传入的起始时间:" + startDate + ",必须不能大于结束时间:" + endDate;
			throw new RuntimeException(message);
		}
	}

	/**
	 * 获取时间列表
	 * 
	 * @param startDate
	 * @param endDate
	 * @throws RuntimeException
	 */
	public static List<String> getDateTimeSectionList(String startDate,
			String endDate, String pattern) throws RuntimeException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		Calendar startCalendar = null;
		Calendar endCalendar = null;
		checkDateTimeSection(startDate, endDate, pattern);
		try {
			startCalendar = dateToCalendar(dateFormat.parse(startDate));
			endCalendar = dateToCalendar(dateFormat.parse(endDate));
		} catch (Exception ex) {
			String message = "格式化结束时间发生异常,传入的格式化参数为:" + pattern;
			logger.error(message + ",格式化异常信息:", ex);
			throw new RuntimeException(message);
		}
		List<String> dateList = new LinkedList<String>();
		while (startCalendar.getTimeInMillis() <= endCalendar.getTimeInMillis()) {
			String dateValue = dateFormat.format(startCalendar.getTime());
			dateList.add(dateValue);
			startCalendar.add(Calendar.DATE, 1);
		}
		return dateList;
	}

	/**
	 * 获取起始时间
	 * 
	 * @param pattern
	 * @return
	 * @throws RuntimeException
	 */
	public static String getStartTime(String pattern) throws RuntimeException {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.format(calendar.getTime());
		} catch (Exception ex) {
			String message = "传入的格式化参数为:" + pattern;
			logger.error(message + ",格式化异常信息:", ex);
			throw new RuntimeException(message);
		}
	}

	/**
	 * 获取结束时间
	 * 
	 * @param pattern
	 * @return
	 * @throws RuntimeException
	 */
	public static String getEndTime(String pattern) throws RuntimeException {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
			return dateFormat.format(calendar.getTime());
		} catch (Exception ex) {
			String message = "传入的格式化参数为:" + pattern;
			logger.error(message + ",格式化异常信息:", ex);
			throw new RuntimeException(message);
		}
	}
}
