package com.backend.services.utils;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date Utils
 * 
 * <p>It collects date operation utilities to support 
 * making decision or concert/parse a date and so on.</p>
 */
@SuppressLint("SimpleDateFormat")
public class DateUtils {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private DateUtils() {
	}
	
	/**
	 * Tries to parse and convert the given string date to its long value.
	 * 
	 * @param date The date to be parsed.
	 * @return The long value represented by the given string.
	 * @throws ParseException
	 */
	public static long parse(String date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		return formatter.parse(date).getTime();
	}
	
	/**
	 * Tries to parse and convert the date given by its long value 
	 * to the string representation.
	 * 
	 * @param date The long value of the date.
	 * @return The string representation of the given date. 
	 */
	public static String parse(long date) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		return formatter.format(new Date(date));
	}
	
	/**
	 * Compares the date given by its long value to the current system time.
	 * 
	 * @param date
	 * @return 0 on equal, 1 if first argument is greater than the second or -1 if the second argument is the greater.
	 */
	public static int compare(long date) {
		return compare(date, new Date().getTime());
	}
	
	/**
	 * Compares the date given by its string value to the current system time. 
	 * String is converted to its long value.
	 * 
	 * @param date
	 * @return 0 on equal, 1 if first argument is greater than the second or -1 if the second argument is the greater.
	 */
	public static int compare(String date) throws ParseException {
		return compare(parse(date), new Date().getTime());
	}
	
	/**
	 * Compares two dates by their long value.
	 * 
	 * @param dateA
	 * @param dateB
	 * @return 0 on equal, 1 if first argument is greater than the second or -1 if the second argument is the greater. 
	 */
	public static int compare(long dateA, long dateB) {
		if(dateB < dateA) {
			return 1;
		} else if(dateA < dateB) {
			return -1;
		}
		return 0;
	}
	
	/**
	 * Compares two dates by their string value but the strings are converted to longs.
	 * 
	 * @param dateA
	 * @param dateB
	 * @return 0 on equal, 1 if first argument is greater than the second or -1 if the second argument is the greater.
	 * @throws ParseException
	 */
	public static int compare(String dateA, String dateB) throws ParseException {
		return compare(parse(dateA), parse(dateB));
	}
	
	@Override
	public final Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
