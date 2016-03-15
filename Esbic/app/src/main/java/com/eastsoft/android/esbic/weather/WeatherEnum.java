package com.eastsoft.android.esbic.weather;

import com.eastsoft.android.esbic.R;

/*
 * 
 * 晴|多云|阴|阵雨|雷阵雨|雷阵雨伴有冰雹|
 * 雨夹雪|小雨|中雨|大雨|暴雨|大暴雨|特大暴雨|
 * 阵雪|小雪|中雪|大雪|暴雪|
 * 雾|
 * 冻雨|
 * 沙尘暴|
 * 小雨转中雨|中雨转大雨|大雨转暴雨|暴雨转大暴雨|大暴雨转特大暴雨|
 * 小雪转中雪|中雪转大雪|大雪转暴雪|
 * 浮尘|扬沙|强沙尘暴|霾
 * 
 * 
 * */
public enum WeatherEnum
{
	TE_DA_BAO_YU					(0x06, "特大暴雨", R.drawable.weather_4),
	DA_BAO_YU						(0x06, "大暴雨", R.drawable.weather_4),
	BAO_YU							(0x06, "暴雨", R.drawable.weather_4),
	DA_YU							(0x06, "大雨", R.drawable.weather_4),
	LEI_ZHEN_YU						(0x09, "雷阵雨", R.drawable.weather_7),
	ZHONG_YU						(0x06, "中雨", R.drawable.weather_11),
	XIAO_YU							(0x06, "小雨", R.drawable.weather_11),
	ZHEN_YU							(0x05, "阵雨", R.drawable.weather_10),
	DONG_YU							(0x06, "冻雨", R.drawable.weather_10),

	BAO_XUE							(0x08, "暴雪", R.drawable.weather_3),
	DA_XUE							(0x08, "大雪", R.drawable.weather_3),
	ZHONG_XUE						(0x08, "中雪", R.drawable.weather_6),
	YU_JIA_XUE						(0x04, "雨夹雪", R.drawable.weather_6),
	XIAO_XUE						(0x08, "小雪",R.drawable.weather_2),
	ZHEN_XUE						(0x08, "阵雪", R.drawable.weather_12),

	QIANG_SHA_CHEN_BAO				(0x0C, "强沙尘暴", R.drawable.weather_8),
	SA_CHEN_BAO						(0x0D, "沙尘暴", R.drawable.weather_8),

	MAI								(0x0B, "霾", R.drawable.weather_8),
	WU								(0x03, "雾", R.drawable.weather_1),
	YANG_SHA						(0x0E, "扬沙", R.drawable.weather_8),
	FU_CHEN							(0x0A, "浮尘", R.drawable.weather_8),
	
	YIN								(0x07, "阴", R.drawable.weather_9),
	DUO_YUN							(0x01, "多云", R.drawable.weather_9),
	QING							(0x02, "晴", R.drawable.weather_8),
	
	
	UNKNOW							(0x00, "未知", R.drawable.weather_8),
	
	;
	public final int sno;
	public final String name;
	public final int icon;
	
	WeatherEnum(int sno, String name, int icon)
	{
		this.sno = sno;
		this.name = name;
		this.icon = icon;
	}
	
	// 	在获取天气图标序号时，采取优先匹配原则，例如多云转晴==》多云，晴转多云==》晴
	public static WeatherEnum find(String name)
	{
		if(name == null)
		{
			return UNKNOW;
		}
		int index = name.indexOf("伴");
		if(index > 0)
		{
			name = name.substring(0, index);
		}
		for(WeatherEnum e : WeatherEnum.values())
		{
			if(name.contains(e.name) == true)
			{
				return e;
			}
		}
		return UNKNOW;
	}
	public String toString()
	{
		return "(" + sno + ","+name+") ";
	}
	
	public static void main(String[]argv)
	{
		String weather = "雷阵雨伴有冰雹";
		WeatherEnum e = WeatherEnum.find(weather);
		System.out.println(e.toString());
		
		weather = "大暴雨转特大暴雨";
		e = WeatherEnum.find(weather);
		System.out.println(e.toString());
		weather = "小到中雨";
		e = WeatherEnum.find(weather);
		System.out.println(e.toString());
		weather = "小到中雨转中到大雨";
		e = WeatherEnum.find(weather);
		System.out.println(e.toString());
		weather = "大风";
		e = WeatherEnum.find(weather);
		System.out.println(e.toString());
	}
}
