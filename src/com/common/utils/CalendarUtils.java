package com.common.utils;

/**    
 * @description  :  
 * 				 : 时间工具类,提供各种时间转行API
 * @version      : V 1.0
 * @author       : ztiny
 * @create       : 2012-4-27 下午5:16:03  
 * @Copyright    :  Corporation 2012    
 * 
 * Modification History:
 * 	Date			Author			Version			Description
 *--------------------------------------------------------------
 * 2012-4-27 下午5:16:03
 */
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarUtils extends org.apache.commons.lang3.time.DateUtils {
	private static final String				DEFAULT_PATTERN				= "yyyy-MM-dd HH:mm:ss";

	/**
	 * 日期格式化
	 */
	public static final SimpleDateFormat	FORMAT_DATE					= new SimpleDateFormat( "yyyy-MM-dd" );

	/**
	 * 年月格式化
	 */
	public static final SimpleDateFormat	FORMAT_YEAR_MONTH			= new SimpleDateFormat( "yyyy-MM" );

	/**
	 * 年月格式化
	 */
	public static final SimpleDateFormat	FORMAT_YEAR					= new SimpleDateFormat( "yyyy" );

	/**
	 * 月日格式化
	 */
	public static final SimpleDateFormat	FORMAT_MONTH_DAY			= new SimpleDateFormat( "MM-dd" );

	public static final SimpleDateFormat	FORMAT_AAA_TIME_NOT_SECOND	= new SimpleDateFormat( "aaa HH:mm" );

	/**
	 * 时间格式化(不含秒)
	 */
	public static final SimpleDateFormat	FORMAT_TIME_NOT_SECOND		= new SimpleDateFormat( "HH:mm" );

	/**
	 * 时间格式化
	 */
	public static final SimpleDateFormat	FORMAT_TIME					= new SimpleDateFormat( "HH:mm:ss" );

	/**
	 * 日期时间格式化(不含秒)
	 */
	public static final SimpleDateFormat	FORMAT_DATE_TIME_NOT_SECOND	= new SimpleDateFormat( "yyyy-MM-dd HH:mm" );

	/**
	 * 日期格式化字符串
	 */
	public static final SimpleDateFormat	FORMAT_DATE_TIME			= new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

	/**
	 * 将时间格式化日期格式(yyyy-MM-dd)
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return formatDateByFormat( date, FORMAT_DATE );
	}

	public static String formatMonthDay(Date date) {
		return formatDateByFormat( date, FORMAT_MONTH_DAY );
	}

	/**
	 * 将日期格式化为年月(yyyy-MM)
	 * 
	 * @param date
	 * @return
	 */
	public static String formatYearMonth(Date date) {
		return formatDateByFormat( date, FORMAT_YEAR_MONTH );
	}

	/**
	 * 将时间格式为时间形式(HH:mm:ss)
	 * 
	 * @param date
	 * @return
	 */
	public static String formatTime(Date date) {
		return formatDateByFormat( date, FORMAT_TIME );
	}

	/**
	 * 将时间格式化为不带秒的时间形式(HH:mm)
	 * 
	 * @param date
	 * @return
	 */
	public static String formatTimeNoSecond(Date date) {
		return formatDateByFormat( date, FORMAT_TIME_NOT_SECOND );
	}

	public static String formatAaaTimeNoSecond(Date date) {
		return formatDateByFormat( date, FORMAT_AAA_TIME_NOT_SECOND );
	}

	/**
	 * 将时间格式化为日期时间格式(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTime(Date date) {
		return formatDateByFormat( date, FORMAT_DATE_TIME );
	}

	/**
	 * 将时间格式化为日期时间格式不带秒(yyyy-MM-dd HH:mm)
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateTimeNotSecond(Date date) {
		return formatDateByFormat( date, FORMAT_DATE_TIME_NOT_SECOND );
	}

	/**
	 * 将时间格式化为指定格式
	 * 
	 * @param date
	 * @param formatformatDateByFormat
	 * @return
	 */
	public final static String formatDateByFormat(Date date, SimpleDateFormat format) {
		String result = "";
		if (date != null) {
			result = format.format( date );
		}
		return result;
	}

	/**
	 * 将java.util.Date转化为java.sql.Date
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date parseSqlDate(Date date) {
		if (date == null) {
			throw new NullPointerException();
		}
		return new java.sql.Date( date.getTime() );
	}

	/**
	 * 获取年份
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime( date );
		return c.get( Calendar.YEAR );
	}

	/**
	 * 获取月份
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime( date );
		return c.get( Calendar.MONTH ) + 1;
	}

	/**
	 * 获取日期(一个月中的哪一天)
	 * 
	 * @param date
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime( date );
		return c.get( java.util.Calendar.DAY_OF_MONTH );
	}

	/**
	 * 获取小时(24小时制)
	 * 
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime( date );
		return c.get( Calendar.HOUR_OF_DAY );
	}

	/**
	 * 获取分钟
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime( date );
		return c.get( Calendar.MINUTE );
	}

	/**
	 * 获取秒
	 * 
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime( date );
		return c.get( Calendar.SECOND );
	}

	/**
	 * 获取毫秒
	 * 
	 * @param date
	 * @return
	 */
	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime( date );
		return c.getTimeInMillis();
	}

	/**
	 * 获取指定时间是星期几
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime( date );
		int dayOfWeek = c.get( Calendar.DAY_OF_WEEK );
		dayOfWeek = dayOfWeek - 1;
		if (dayOfWeek == 0) {
			dayOfWeek = 7;
		}
		return dayOfWeek;
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            Date
	 * @param day
	 *            int
	 * @return Date
	 */
	public static Date addDate(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis( getMillis( date ) + ((long) day) * 24 * 3600 * 1000 );
		return c.getTime();
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            Date
	 * @param year
	 *            int
	 * @return Date
	 */
	public static Date addYear(Date date, int year) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis( getMillis( date ) );
		c.add( Calendar.YEAR, year );
		return c.getTime();
	}

	/**
	 * 日期相加
	 * 
	 * @param date
	 *            Date
	 * @param month
	 *            int
	 * @return Date
	 */
	public static Date addMonth(Date date, int month) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis( getMillis( date ) );
		c.add( Calendar.MONTH, month );
		return c.getTime();
	}

	public static Date addMinute(Date date, int minute) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis( getMillis( date ) + ((long) minute) * 60 * 1000 );
		return c.getTime();
	}

	@SuppressWarnings("deprecation")
	public static Date formtMinute(Date date, int interval) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis( getMillis( date ) - ((long) date.getMinutes() % interval) * 60 * 1000 );
		return c.getTime();
	}

	/**
	 * 日期相减
	 * 
	 * @param date
	 *            Date
	 * @param date1
	 *            Date
	 * @return int
	 */
	public static int diffDate(Date date, Date date1) {
		return (int) ((getMillis( date ) - getMillis( date1 )) / (24 * 3600 * 1000));
	}

	public static int diffMinute(Date endDate, Date starDate) {
		return (int) ((getMillis( endDate ) - getMillis( starDate )) / (60 * 1000));
	}

	/**
	 * 
	 * diffMonth:计算两个日期间的月数. <br/>
	 * 
	 * @author William
	 * @param starDate
	 * @param endDate
	 * @return
	 * @since JDK 1.7
	 */
	public static int diffMonth(Date starDate, Date endDate) {
		Calendar c = Calendar.getInstance();
		c.setTime( starDate );
		int m1 = c.get( Calendar.MONTH );
		int y1 = c.get( Calendar.YEAR );
		int d1 = c.get( Calendar.DAY_OF_MONTH );
		long t1 = c.getTimeInMillis();
		c.setTime( endDate );
		int m2 = c.get( Calendar.MONTH );
		int y2 = c.get( Calendar.YEAR );
		int d2 = c.get( Calendar.DAY_OF_MONTH );
		long t2 = c.getTimeInMillis();
		// 开始日期若小月结束日期
		if (t1 < t2) {
			int year = y2 - y1;
			if (d2 >= d1 - 1) {
				return year * 12 + m2 - m1;
			} else {
				return year * 12 + m2 - m1 - 1;
			}
		} else {
			int year = y1 - y2;
			if (d1 >= d2 - 1) {
				return year * 12 + m1 - m2;
			} else {
				return year * 12 + m1 - m2 - 1;
			}
		}
	}

	/**
	 * 将字符串转换为日期格式的Date类型(yyyy-MM-dd)
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDate(String date) throws ParseException {
		return FORMAT_DATE.parse( date );
	}

	/**
	 * 将字符串转换为日期格式的Date类型(yyyy-MM)
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseMonth(String date) throws ParseException {
		return FORMAT_YEAR_MONTH.parse( date );
	}

	/**
	 * 将字符串转换为日期时间格式的Date类型(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseDateTime(String date) throws ParseException {
		return FORMAT_DATE_TIME.parse( date );
	}

	public static Date parseDateTimeNoSecond(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
		return sdf.parse( date );
	}

	public static boolean compareDateAndWeekDay(Date date, int weekDay) {
		if (weekDay == getWeek( date )) {
			return true;
		}
		return false;
	}

	/**
	 * 将字符串转换为日期时间格式的Date类型( MM-dd )
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseMonthDay(String date) throws ParseException {
		return FORMAT_MONTH_DAY.parse( date );
	}

	/**
	 * 将字符串转为时间格式的Date类型(HH:mm)
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseTimeNoSecond(String date) throws ParseException {
		return FORMAT_TIME_NOT_SECOND.parse( date );
	}

	/**
	 * 将字符串转为时间格式的Date类型(YYYY)
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parseTimeFromToDate(String date) {
		Date d = null;
		try {
			d = FORMAT_YEAR.parse( date );
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return d;
	}

	/**
	 * 获得当前时间
	 * 
	 * @return
	 */
	public static Date getCurrentDateTime() {
		Date date = new Date();
		String result = CalendarUtils.formatDateTime( date );
		try {
			return FORMAT_DATE_TIME.parse( result );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 时间比较yyyy-MM-dd
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareDate(Date date1, Date date2) {
		String str1 = formatDate( date1 );
		String str2 = formatDate( date2 );
		if (str1.equals( str2 )) {
			return true;
		}
		return false;
	}

	/**
	 * 时间比较HH:mm
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareDateTimeHHmm(Date date1, Date date2) {
		String str1 = formatTimeNoSecond( date1 );
		String str2 = formatTimeNoSecond( date2 );
		if (str1.equals( str2 )) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否是有效的日期， yyyy-MM-dd, yyyy-MM-d, yyyy-M-dd, yyyy-M-d yyyy/MM/dd
	 * yyyy/MM/d yyyy/M/dd yyyy/M/d yyyyMMdd
	 * 
	 * @param date
	 *            日期字符串
	 * @return 是则返回true，否则返回false
	 */
	public static boolean isValidDate(String date) {
		if ((date == null) || (date.length() < 8)) {
			return false;
		}
		try {
			boolean result = false;
			SimpleDateFormat formatter;
			char dateSpace = date.charAt( 4 );
			String format[];
			if ((dateSpace == '-') || (dateSpace == '/')) {
				format = new String[4];
				String strDateSpace = Character.toString( dateSpace );
				format[ 0 ] = "yyyy" + strDateSpace + "MM" + strDateSpace + "dd";
				format[ 1 ] = "yyyy" + strDateSpace + "MM" + strDateSpace + "d";
				format[ 2 ] = "yyyy" + strDateSpace + "M" + strDateSpace + "dd";
				format[ 3 ] = "yyyy" + strDateSpace + "M" + strDateSpace + "d";
			} else {
				format = new String[1];
				format[ 0 ] = "yyyyMMdd";
			}

			for (int i = 0; i < format.length; i++) {
				String aFormat = format[ i ];
				formatter = new SimpleDateFormat( aFormat );
				formatter.setLenient( false );
				String tmp = formatter.format( formatter.parse( date ) );
				if (date.equals( tmp )) {
					result = true;
					break;
				}
			}
			return result;
		} catch (ParseException e) {
			return false;
		}
	}

	public static SimpleDateFormat getSimpleDateFormat(String pattern) {
		return new SimpleDateFormat( pattern );
	}

	/**
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String timestampTostr(Timestamp time, String pattern) {
		if (time == null) {
			throw new IllegalArgumentException( "Timestamp is null" );
		}
		if (pattern != null && !"".equals( pattern )) {
			if (!"yyyyMMddHHmmss".equals( pattern ) && !"yyyy-MM-dd HH:mm:ss".equals( pattern ) && !"yyyy-MM-dd".equals( pattern )
					&& !"MM/dd/yyyy".equals( pattern )) {
				throw new IllegalArgumentException( "Date format [" + pattern + "] is invalid" );
			}
		} else {
			pattern = DEFAULT_PATTERN;
		}

		Calendar cal = Calendar.getInstance( TimeZone.getDefault() );
		cal.setTime( time );
		SimpleDateFormat sdf = new SimpleDateFormat( pattern );
		return sdf.format( cal.getTime() );
	}

	public static Date defaultDate(Date date, Date defaultDate) {
		if (date == null) {
			return defaultDate;
		}
		return date;
	}

	public static Timestamp strToTimestamp(String timeStr, String pattern) {
		Timestamp result = null;
		if (timeStr == null) {
			throw new IllegalArgumentException( "Timestamp is null" );
		}
		if (pattern != null && !"".equals( pattern )) {
			if (!"yyyyMMddHHmmss".equals( pattern ) && !"yyyy-MM-dd HH:mm:ss".equals( pattern ) && !"MM/dd/yyyy HH:mm:ss".equals( pattern )
					&& !"yyyy-MM-dd".equals( pattern ) && !"MM/dd/yyyy".equals( pattern )) {
				throw new IllegalArgumentException( "Date format [" + pattern + "] is invalid" );
			}
		} else {
			pattern = DEFAULT_PATTERN;
		}

		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat( pattern );
		try {
			d = sdf.parse( timeStr );
			result = new Timestamp( d.getTime() );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			/*
			 * System.out.println( truncate( new Date(), Calendar.MONTH ) );
			 * Date d1 = parseDate( "2000-12-10" ); Date d2 = parseDate(
			 * "1900-12-10" ); System.out.println( "ccc:" +
			 * CalendarUtils.parseDateTimeNoSecond( "2013-08-30 00:00:00"
			 * ).toString() ); System.out.println( CalendarUtils.diffDate( d1,
			 * d2 ) ); // Date date = CalendarUtils.getCurrentDateTime(); //
			 * System.out.println(date); System.out.println( strToTimestamp(
			 * "2008-01-25 13:10:00", "yyyy-MM-dd HH:mm:ss" ) );
			 * System.out.println( timestampTostr( new Timestamp(
			 * System.currentTimeMillis() ), "yyyy-MM-dd HH:mm:ss" ) );
			 * System.out.println( strToTimestamp( "01/25/2008", "MM/dd/yyyy" )
			 * );
			 * 
			 * System.out.println( "测试月:" + formatDate( addMonth( new Date(), 3
			 * ) ) ); System.out.println( "测试年:" + formatDate( addYear( new
			 * Date(), 3 ) ) );
			 */
			/*
			 * System.out.println( CalendarUtils.getSpecifiedDayBefore(
			 * "20170301" ) ); System.out.println(
			 * CalendarUtils.getSpecifiedDayAfter( "20170301" ) );
			 */
			System.out.println( getDayBetween( 7 ) );
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat( "yyyyMMdd" ).parse( specifiedDay );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime( date );
		int day = c.get( Calendar.DATE );
		c.set( Calendar.DATE, day - 1 );

		String dayBefore = new SimpleDateFormat( "yyyyMMdd" ).format( c.getTime() );
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat( "yyyyMMdd" ).parse( specifiedDay );
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime( date );
		int day = c.get( Calendar.DATE );
		c.set( Calendar.DATE, day + 1 );

		String dayAfter = new SimpleDateFormat( "yyyyMMdd" ).format( c.getTime() );
		return dayAfter;
	}

	/**
	 * 日期回滚day天
	 * 
	 * @author yangaobiao
	 * @param day
	 * @return
	 * @since JDK 1.7
	 */
	public static String getDayBetween(Integer day) {
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyyMMdd" );
		Calendar lastDate = Calendar.getInstance();
		lastDate.roll( Calendar.DATE, -day );// 日期回滚day天
		return sdf.format( lastDate.getTime() );

	}
}
