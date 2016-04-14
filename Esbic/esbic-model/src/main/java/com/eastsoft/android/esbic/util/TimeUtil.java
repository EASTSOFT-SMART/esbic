package com.eastsoft.android.esbic.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil
{
	private TimeUtil()
	{
		throw new AssertionError(); 
	}
	
	public static boolean isSystemTimeCorrect()
	{
        try
        {
            SimpleDateFormat myfmt = new SimpleDateFormat("yyyyMMddHHmmss");
            TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");
            myfmt.setTimeZone(timeZoneChina);

            Date date1 = new Date();
            Date date2 = myfmt.parse("20160101000000");
            long ms1 = date1.getTime();
            long ms2 = date2.getTime();
            long sdiff = (ms1-ms2)/1000;
            if (sdiff < 0)
            {
                return false;
            }
        }catch (Exception e)
        {
            LogUtil.print(LogUtil.LogPriorityEnum.CORE_LOG_PRI_ERROR, e.getMessage());
            return false;
        }
        return true;
	}
	
	
	public static String getDateTimeofNow()
	{
		String date;
		SimpleDateFormat myfmt = new SimpleDateFormat("[yy-MM-dd HH:mm:ss:SSS]");
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");
		myfmt.setTimeZone(timeZoneChina);
		date = myfmt.format(new Date());
		return date;
	}

	public static String getDateTimeofNow2()
	{
		String date;
		SimpleDateFormat myfmt = new SimpleDateFormat("yyMMddHHmmss");
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");
		myfmt.setTimeZone(timeZoneChina);
		date = myfmt.format(new Date());
		return date;
	}
	
	public static String getDateTimeofNow3()
	{
		String date;
		SimpleDateFormat myfmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");
		myfmt.setTimeZone(timeZoneChina);
		date = myfmt.format(new Date());
		return date;
	}

	public static String getDateTimeofNow4()
	{
		String date;
		SimpleDateFormat myfmt = new SimpleDateFormat("yyyyMMddHHmmss");
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");
		myfmt.setTimeZone(timeZoneChina);
		date = myfmt.format(new Date());
		return date;
	}

	public static String formatTime(long ms)
	{
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;
		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;
		String strDay = day < 10 ? "0" + day : "" + day; //天
		String strHour = hour < 10 ? "0" + hour : "" + hour;//小时
		String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟
		String strSecond = second < 10 ? "0" + second : "" + second;//秒
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;

		return strHour + "时" + strMinute + "分" + strSecond + "秒";
	}

	public static String getPreviousDateTime(String oldDate, long interval) throws ParseException
	{
		SimpleDateFormat myfmt = new SimpleDateFormat("yyMMddHHmmss");
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");
		myfmt.setTimeZone(timeZoneChina);
		
		Date date = myfmt.parse(oldDate);
		long ms = date.getTime() - interval*1000;
		
		return myfmt.format(new Date(ms));
	}
	public static String getNextDateTime(String oldDate, long interval) throws ParseException
	{

		SimpleDateFormat myfmt = new SimpleDateFormat("yyMMddHHmmss");
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");
		myfmt.setTimeZone(timeZoneChina);
		
		Date date = myfmt.parse(oldDate);
		long ms = date.getTime() + interval*1000;
		
		return myfmt.format(new Date(ms));
		
	}
	
	// return |newDate -oldDate|, uint second
	public static int getTimeDiff(String newDate, String oldDate) throws ParseException
	{
		SimpleDateFormat myfmt = new SimpleDateFormat("yyMMddHHmmss");
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");
		myfmt.setTimeZone(timeZoneChina);
		
		Date date1 = myfmt.parse(newDate);
		Date date2 = myfmt.parse(oldDate);
		long ms1 = date1.getTime();
		long ms2 = date2.getTime();
		long sdiff = (ms1-ms2)/1000;
		return (int) Math.abs(sdiff);
	}
	
	public static long compareTime(String strdate1, String strdate2) throws ParseException
	{
		SimpleDateFormat myfmt = new SimpleDateFormat("yyMMddHHmmss");
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");
		myfmt.setTimeZone(timeZoneChina);
		
		Date date1 = myfmt.parse(strdate1);
		Date date2 = myfmt.parse(strdate2);
		long ms1 = date1.getTime();
		long ms2 = date2.getTime();
		long sdiff = (ms1-ms2)/1000;
		return sdiff;
	}
	
	public static boolean checkDateTime(String tmp)
	{
		SimpleDateFormat myfmt = new SimpleDateFormat("yyMMddHHmmss");
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");
		myfmt.setTimeZone(timeZoneChina);
		
		try
		{
			Date date = myfmt.parse(tmp);
			//DBGMessage.println(myfmt.format(date));
			if(myfmt.format(date).compareTo(tmp) == 0)
			{
				return true;
			}
		} catch (ParseException e)
		{
		}
		return false;
	}

	public static String getDateString(Date date)
	{
		String tmp;
		SimpleDateFormat myfmt = new SimpleDateFormat("yyMMddHHmmss");
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");
		myfmt.setTimeZone(timeZoneChina);
		tmp = myfmt.format(date).toString();
		return tmp;
	}

	public static String covert(String time, String sformat, String tformat) throws ParseException
	{
		SimpleDateFormat format = new SimpleDateFormat(sformat);
		Date date = format.parse(time);
		format = new SimpleDateFormat(tformat);
		TimeZone timeZoneChina = TimeZone.getTimeZone("Asia/Shanghai");
		format.setTimeZone(timeZoneChina);
		time = format.format(new Date());
		return time;
	}
	public static String getLastHourTime(int lastHour) throws ParseException 
	{
		String curDate = TimeUtil.getDateTimeofNow2();
		String lastHourDate = TimeUtil.getPreviousDateTime(curDate, lastHour*60*60);
		return (lastHourDate.substring(0, 8) + "0000");
	}
	
	public static String getLastDayTime(int lastDay) throws ParseException 
	{
		String curDate = TimeUtil.getDateTimeofNow2();
		String lastDate = TimeUtil.getPreviousDateTime(curDate, lastDay * 24*60*60);	
		return (lastDate.substring(0, 6) + "000000");
	}
	
	public static String getLastMonthTime(int lastMonth) throws ParseException 
	{
		String curDate = TimeUtil.getDateTimeofNow2();
		String monthDate = curDate.substring(0, 4) + "01000000";
		for(int i = 0; i < lastMonth; i++)
		{
			monthDate = TimeUtil.getPreviousDateTime(monthDate, 24*60*60);
			monthDate = monthDate.substring(0, 4) + "01000000";
		}
		return monthDate;
	}
	
	public static String getRandomDate(String format, String startDate, String endDate) throws ParseException
	{
		SimpleDateFormat sdformat = new SimpleDateFormat(format);
		Date start = sdformat.parse(startDate);
		Date end = sdformat.parse(endDate);
		long startTime = start.getTime();
		long endTime = end.getTime();
		if (startTime >= endTime)
		{
			return null;
		}
		long randomTime;
		do{
			randomTime = startTime + (long) (Math.random() * (endTime - startTime));
		}while(randomTime == startTime || randomTime == endTime);
		return sdformat.format(new Date(randomTime));	
	}
	
	public static void main(String[] args) throws ParseException
	{
		System.out.println("last 1 hour time : " + getLastHourTime(1));
		System.out.println("last 13 hour time : " +getLastHourTime(13));
		System.out.println("last 1 day time : " + getLastDayTime(1));
		System.out.println("last 5 day time : " + getLastDayTime(5));
		System.out.println("last month time : " + getLastMonthTime(1));
		System.out.println("last month time : " + getLastMonthTime(15));
		String time = getRandomDate("HHmm", "0100", "0600");
		System.out.println(time);
	}
}
