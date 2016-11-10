/*
 * @(#)DateUtil.java $version 2013. 7. 10.
 *
 * Copyright 2013 NHN Entertainment Corp. All rights Reserved. 
 * NHN Entertainment PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.dasoulte.simons.core.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang.StringUtils;

/**
 * 날짜 유틸
 * 
 * @author 이윤희(yunhee.lee@nhn.com)
 */
public class DateUtils extends org.apache.commons.lang.time.DateUtils {

	/** 기본 Date, Time 포멧 문자열 */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyyMMddHHmmss";
	/** 기본 Date 포멧 문자열 */
	public static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";
	/** 기본 Time 포맷 문자열 */
	public static final String DEFAULT_TIME_FORMAT = "HHmmss";
	/** DB Select Date 포멧 문자열 */
	public static final String DB_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * <pre>
	 * 입력받은 날짜와 오늘 날짜를 비교한다.
	 * 같으면 0
	 * 오늘보다 이전이면 - (less than 0)
	 * 오늘보다 이후이면 + (greater than 0)
	 * </pre>
	 * 
	 * @param comparedDateString 
	 * @param dateStringFormat
	 * @return
	 */
	public static int compare(String comparedDateString, String dateStringFormat) {
		Date now = new Date();
		
		if (!StringUtils.equals(DEFAULT_DATETIME_FORMAT, dateStringFormat)) {
			now = parseDate(getTodayAs(dateStringFormat), dateStringFormat);
		}
		
		Date comparedDate = parseDate(comparedDateString, dateStringFormat);

		return comparedDate.compareTo(now);
	}

	public static int compare(Date comparedDate) {
		Date now = new Date();

		return comparedDate.compareTo(now);
	}

	/**
	 * 입력한 날짜가 시작일과 종료일 사이에 날짜인지 여부를 반환한다.
	 * 
	 * @param compareDate {@link Date} : 비교 날짜
	 * @param startDate {@link Date} : 시작일
	 * @param endDate {@link Date} : 종료일
	 * @return boolean : 시작일과 종료일 사이의 날짜 여부
	 */
	public static boolean isBetweenness(Date compareDate, Date startDate, Date endDate) {
		if (compareDate != null && startDate != null && endDate != null) {
			long compareTime = compareDate.getTime();
			long startTime = startDate.getTime();
			long endTime = endDate.getTime();

			if (startTime <= compareTime && compareTime < endTime) {
				return true;
			}
			if (endTime <= compareTime && compareTime < startTime) {
				return true;
			}
		}
		return false;
	}

	/**
	 * fromYmd 에서 toYmd 까지의 일(yyyyMMdd)들을 리스트로 반환한다. 
	 * @param fromYmd
	 * @param toYmd
	 * @return
	 */
	public static List<String> betweenDays(String fromYmd, String toYmd) {
		Date fromDate = DateUtils.parseDate(fromYmd, DateUtils.DEFAULT_DATE_FORMAT);
		Date toDate = DateUtils.parseDate(toYmd, DateUtils.DEFAULT_DATE_FORMAT);

		return betweenDays(fromDate, toDate);
	}

	/**
	 * fromDate 부터 toDate까지 사이의 일(yyyyMMdd)을 리스트로 반환한다.
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static List<String> betweenDays(Date fromDate, Date toDate) {
		List<String> betweenDays = new ArrayList<String>();

		while (fromDate.compareTo(toDate) <= 0) {
			betweenDays.add(DateUtils.convertToDate(fromDate, DateUtils.DEFAULT_DATE_FORMAT));
			fromDate = DateUtils.addDays(fromDate, 1);
		}

		return betweenDays;
	}

	/**
	 * 기본 날짜 포맷<br>
	 * 날짜 포맷이 비어있으면 기본 포맷(yyyyMMddHHmmss)을 반환
	 * 
	 * @param format
	 *            날짜 포맷
	 * @return String
	 */
	private static String getFormat(String format) {
		if (StringUtils.isEmpty(format)) {
			return DEFAULT_DATETIME_FORMAT;
		}

		return format;
	}

	/**
	 * 두 날짜 사이의 일(day) 차이를 반환
	 * 
	 * @param startDt
	 *            시작 날짜
	 * @param endDt
	 *            종료 날짜
	 * @return 두 날짜 사이의 일(day) 차이
	 */
	public static int daysBetween(Date startDt, Date endDt) {
		return (int)((endDt.getTime() - startDt.getTime()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * 두 날짜 사이의 일(day) 차이를 반환 <br/><br/>
	 * 
	 * Ex. startDt = 20140915 가정 <br/>
	 * 1) endDt = 20140920 일때 5 반환 <br/>
	 * 2) endDt = 20140915 일때 0 반환 <br/>
	 * 3) endDt = 20140910 일때 -5 반환 <br/>
	 * 
	 * @param startDt 시작 날짜
	 * @param endDt 종료 날짜
	 * @param format 날짜 형식
	 * 
	 * @return 두 날짜 사이의 일(day) 차이
	 */
	public static int daysBetween(String startDt, String endDt, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(getFormat(format));
		try {
			return daysBetween(sdf.parse(startDt), sdf.parse(endDt));
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 입력받은 2개의 날짜 객체의 만 연도 차이를 반환한다.
	 * 
	 * @param date1 {@link Date}
	 * @param date2  {@link Date}
	 * @return int
	 */
	public static int yearsBetween(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();

		if (date1.getTime() > date2.getTime()) {
			calendar1.setTime(date2);
			calendar2.setTime(date1);
		} else {
			calendar1.setTime(date1);
			calendar2.setTime(date2);
		}

		int years = calendar2.get(Calendar.YEAR) - calendar1.get(Calendar.YEAR);

		int monthDate1 = calendar1.get(Calendar.MONTH) * 100 + calendar1.get(Calendar.DATE);
		int monthDate2 = calendar2.get(Calendar.MONTH) * 100 + calendar2.get(Calendar.DATE);

		if (monthDate2 < monthDate1) {
			--years;
		}

		return years;
	}

	/**
	 * 지정한 날짜에 날짜를 더함
	 * 
	 * @param date
	 *            기준 날짜(not null)
	 * @param amount
	 *            더할 날짜
	 * @param format
	 *            원하는 format
	 * @return String
	 */
	public static String addDays(String date, int amount, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(getFormat(format));
		try {
			Date addedDate = addDays(sdf.parse(date), amount);
			return sdf.format(addedDate);
		} catch (Exception e) {
			return date;
		}
	}

	/**
	 * 지정한 날짜에 시간을 더함
	 * 
	 * @param date
	 *            기준 날짜(not null)
	 * @param amount
	 *            더할 시간
	 * @param format
	 *            원하는 format
	 * @return String
	 */
	public static String addHours(String date, int amount, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(getFormat(format));
		try {
			Date addedDate = addHours(sdf.parse(date), amount);
			return sdf.format(addedDate);
		} catch (Exception e) {
			return date;
		}
	}

	/**
	 * 지정한 날짜에 분을 더함
	 * 
	 * @param date
	 *            기준 날짜(not null)
	 * @param amount
	 *            더할 분
	 * @param format
	 *            원하는 format
	 * @return String
	 */
	public static String addMinutes(String date, int amount, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(getFormat(format));
		try {
			Date addedDate = addMinutes(sdf.parse(date), amount);
			return sdf.format(addedDate);
		} catch (Exception e) {
			return date;
		}
	}

	/**
	 * 두 dtm 사이의 시간(hour) 차이를 반환
	 * 
	 * @param startDt
	 *            시작 DTM
	 * @param endDt
	 *            종료 DTM
	 * @return 시간 차이
	 */
	public static int hoursBetween(Date startDt, Date endDt) {
		return (int)((endDt.getTime() - startDt.getTime()) / MILLIS_PER_HOUR);
	}

	/**
	 * 두 date time 사이의 시간(hour) 차이를 반환
	 * 
	 * @param startDt
	 *            시작 DTM
	 * @param endDt
	 *            종료 DTM
	 * @param format
	 *            날짜 형식
	 * @return 시간 차이
	 */
	public static int hoursBetween(String startDt, String endDt, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(getFormat(format));
		try {
			return hoursBetween(sdf.parse(startDt), sdf.parse(endDt));
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 문자열 날짜를 입력 받아 패턴에 맞게 반환한다.
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String convertToDate(String date, String inputPattern, String outputPattern) {
		if (StringUtils.isBlank(date)) {
			return StringUtils.EMPTY;
		}

		try {
			return new SimpleDateFormat(outputPattern).format(parseDate(date, inputPattern));
		} catch (Exception e) {
			return StringUtils.EMPTY;
		}
	}

	/**
	 * Date를 입력 받아 패턴에 맞게 반환한다.
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String convertToDate(Date date, String pattern) {
		try {
			return new SimpleDateFormat(pattern).format(date);
		} catch (Exception e) {
			return StringUtils.EMPTY;
		}
	}

	/**
	 * inputPattern의 dateString을 January 1, 1970, 00:00:00 GMT 로 부터의 millisecond 으로 반환한다. 
	 * 
	 * @param dateString
	 * @param inputPattern
	 * @return
	 */
	public static long convertToDate(String dateString, String inputPattern) {
		Date date = parseDate(dateString, inputPattern);
		return date.getTime();
	}

	/**
	 * String 포맷의 날자를 date 형으로 변환한다.
	 * 
	 * @param dateString
	 *            dateString
	 * @param pattern
	 *            pattern
	 * @return Date
	 */
	public static Date parseDate(String dateString, String pattern) {
		return parseDate(dateString, pattern, null);
	}

	/**
	 * String 포맷의 날자를 date 형으로 변환한다.
	 * 
	 * @param dateString  {@link String}
	 * @param pattern {@link String}
	 * @param defaultDate {@link Date} : 파싱 실패시 반환할 기본 Date 객체
	 * @return Date
	 */
	public static Date parseDate(String dateString, String pattern, Date defaultDate) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
			ParsePosition pos = new ParsePosition(0);
			return formatter.parse(dateString, pos);
		} catch (Exception e) {
			return defaultDate;
		}
	}

	/**
	 * 오늘 기준으로 일개월 전의 날짜를 반환한다.
	 * @param inputPattern (예, yyyy-MM-dd)
	 * @return
	 */
	public static String getMonthAgoDate(String inputPattern) {
		Date addedDate = addMonths(new Date(), -1);
		return new SimpleDateFormat(inputPattern).format(addedDate);
	}

	/**
	 * 어제 날짜를 반환한다.
	 * @param inputPattern (예, yyyy-MM-dd)
	 * @return
	 */
	public static String getYesterday(String inputPattern) {
		Date yesterday = addDays(new Date(), -1);
		return new SimpleDateFormat(inputPattern).format(yesterday);
	}

	/**
	 * 오늘 날짜를 지정된 형식으로 반환한다.
	 * @param inputPattern
	 * @return
	 */
	public static String getTodayAs(String inputPattern) {
		return new SimpleDateFormat(inputPattern).format(new Date());
	}

	/**
	 * 오늘보다 amount 전(후) 날짜를 inputPattern 형식으로 반환한다.
	 * @param inputPattern (예, yyyy-MM-dd)
	 * @return
	 */
	public static String getAppointedDay(String inputPattern, int amount) {
		Date appointedDate = DateUtils.addDays(new Date(), amount);
		return new SimpleDateFormat(inputPattern).format(appointedDate);
	}

	/**
	 * 현재의 요일을 구한다. 
	 * @return 요일
	 * 일요일 : 1
	 * 월요일 : 2
	 * 화요일 : 3
	 * 수요일 : 4
	 * 목요일 : 5
	 * 금요일 : 6
	 * 토요일 : 7
	 */
	public static int getDayOfWeek() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 해당날짜의 요일을 구한다. 
	 * 
	 * <pre>
	 * 일=1, 월=2, 화=3, 수=4, 목=5, 금=6, 토=7
	 * </pre>
	 * 
	 * @param date {@link Date} : 입력날짜
	 * @return int : 한주의 날짜 순서 값
	 * @see Calendar#SUNDAY
	 * @see Calendar#MONDAY
	 * @see Calendar#TUESDAY
	 * @see Calendar#WEDNESDAY
	 * @see Calendar#THURSDAY
	 * @see Calendar#FRIDAY
	 * @see Calendar#SATURDAY
	 */
	public static int daySequenceOfWeek(Date date) {
		Calendar theDay = Calendar.getInstance();
		theDay.setTime(date == null ? new Date() : date);

		return theDay.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 오늘까지 남은 일 수를 구한다.
	 * 일단위로 구하기 때문에 1000*60*60*24로 나누지 않는다.
	 * @param inputDate
	 * @return
	 */
	public static int getDaysUntilToday(Date inputDate) {
		try {
			Calendar nowCal = Calendar.getInstance();
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(inputDate);
			// 날짜 일수 차이를 구하기 위하여 마지막 날짜를 23:59:59.999로 변환
			endCal.set(Calendar.HOUR_OF_DAY, 23);
			endCal.set(Calendar.MINUTE, 59);
			endCal.set(Calendar.SECOND, 59);
			endCal.set(Calendar.MILLISECOND, 999);
			long diffTimeMillis = (endCal.getTimeInMillis() - nowCal.getTimeInMillis());
			long diffDays = (diffTimeMillis / (1000 * 60 * 60 * 24));

			return (int)diffDays;
		} catch (Exception e) {
			return 0;
		}

	}

	/**
	 * 현재 시간까지 남은 시간&분의 문자열을 구한다.
	 * @param inputDate
	 * @return ex) 23:59
	 */
	public static String getRemainingHoursAndMimites(Date inputDate) {

		try {
			long diffTimeMillis = (inputDate.getTime() - System.currentTimeMillis());
			long remainingHours = (diffTimeMillis / (1000 * 60 * 60));
			long remainingMinites = (diffTimeMillis % (1000 * 60 * 60)) / (1000 * 60);

			if (remainingHours >= 24) {
				return "";
			}

			return String.format("%02d:%02d", remainingHours, remainingMinites);

		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 특정 날짜의 요일을 구해온다.
	 * @param date
	 * @return
	 */
	public static String getDayOfWeekString(Date date) {
		String[] dayArray = {"일", "월", "화", "수", "목", "금", "토"};

		int dayIndex = daySequenceOfWeek(date);

		return dayArray[dayIndex - 1];
	}

	public static int getDay() {
		return getNumberByPattern("dd");
	}

	public static int getDay(Date date) {
		return getNumberByPattern("dd", date);
	}

	public static int getYear() {
		return getNumberByPattern("yyyy");
	}

	public static int getYear(Date date) {
		return getNumberByPattern("yyyy", date);
	}

	public static int getMonth() {
		return getNumberByPattern("MM");
	}

	public static int getMonth(Date date) {
		return getNumberByPattern("MM", date);
	}

	public static int getNumberByPattern(String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.KOREA);
		String dateString = formatter.format(new Date());
		return Integer.parseInt(dateString);
	}

	public static int getNumberByPattern(String pattern, Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.KOREA);
		String dateString = formatter.format(date);
		return Integer.parseInt(dateString);
	}

	/**
	 * 특정 시간의 해당달의 첫날 00:00:00 시간을 가져온다.
	 * @param date
	 * @return
	 */
	public static Date getFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDate = calendar.getTime();

		return DateUtils.truncate(firstDate, Calendar.DATE);
	}

	/**
	 * 특정 시간의 해당달의 마지막날 00:00:000 시간을 가져온다.
	 * 		e.g. 2015-05-12 10:34:122 -> 2015-05-31 00:00:000 
	 * @param date
	 * @return
	 */
	public static Date getLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date lastDate = calendar.getTime();
		return DateUtils.truncate(lastDate, Calendar.DATE);
	}
}
