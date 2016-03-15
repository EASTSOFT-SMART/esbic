package com.eastsoft.android.esbic.weather;

import java.util.List;


public class BaiduWeather
{
	public final int error;
	public final String status;
	public final String date;
	public final List<Result> results;
	
	public BaiduWeather(int error, String status, String date, List<Result> results)
	{
		this.error = error;
		this.status = status;
		this.date = date;
		this.results = results;
	}
	
	public static class Result
	{
		public final String currentCity;
//		public final int pm25;
		public final List<Index> index;
		public final List<WeatherData> weather_data;
		
		public Result(String currentCity, int pm25, List<Index> index, List<WeatherData> weather_data)
		{
			this.currentCity = currentCity;
//			this.pm25 = pm25;
			this.index = index;
			this.weather_data = weather_data;
		}
	}
	
	public static class Index
	{
		public final String title;
		public final String zs;
		public final String tipt;
		public final String des;
		
		public Index(String title, String zs, String tipt, String des)
		{
			this.title = title;
			this.zs = zs;
			this.tipt = tipt;
			this.des = des;
		}
	}
	
	public static class WeatherData
	{
		public final String date;
		public final String dayPictureUrl;
		public final String nightPictureUrl;
		public final String weather;
		public final String wind;
		public final String temperature;
		
		public WeatherData(String date, String dayPictureUrl, String nightPictureUrl, String weather, String wind, String temperature)
		{
			this.date =date;
			this.dayPictureUrl = dayPictureUrl;
			this.nightPictureUrl = nightPictureUrl;
			this.weather = weather;
			this.wind = wind;
			this.temperature = temperature;
		}
	}
	
}