package com.service.banking.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatterUtil {
	
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
	
	static public String currentDateInMilliString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String currentDate = sdf.format(new Date());
		try {
			String currentDateInMilliS = String.valueOf(sdf.parse(currentDate).getTime());
			return currentDateInMilliS;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	static public String currentDateInString() {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		return sdf.format(new Date());
	}
	
	static public String convertDateStringToMillisString(String dateInString) {
		try {
			Date date = sdf.parse(dateInString);
			return sdf.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	static public Long convertDateStringToMillisLong(String dateInString) {
		try {
			return sdf.parse(dateInString).getTime();
//			return Long.parseLong(sdf.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	static public Long currentDateInLong() {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		String currentDate = sdf.format(new Date());
		try {
			return sdf.parse(currentDate).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}	
