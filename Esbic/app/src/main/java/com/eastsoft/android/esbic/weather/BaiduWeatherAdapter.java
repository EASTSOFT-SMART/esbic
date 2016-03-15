package com.eastsoft.android.esbic.weather;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaiduWeatherAdapter implements WeatherAdapter
{

	@Override
	public WeatherInfo transform(Object obj)
	{
		if (obj == null || !(obj instanceof BaiduWeather))
		{
			throw new IllegalArgumentException("para error");
		}

		BaiduWeather weather = (BaiduWeather) obj;

		if (weather.status.compareTo("success") != 0)
		{
			return null;
		}

		if (weather.results == null || weather.results.get(0) == null
				|| weather.results.get(0).weather_data == null
				|| weather.results.get(0).weather_data.get(0) == null)
		{
			return null;
		}

		BaiduWeather.WeatherData wdata = weather.results.get(0).weather_data.get(0);
		Pattern p = Pattern.compile("[-]?[0-9]+");
		Matcher m = p.matcher(wdata.temperature);
		int lowTemp, hightTemp;
		if(m.find())
		{
			hightTemp = Integer.parseInt(m.group());
		}
		else
		{
			return null;
		}
		
		if(m.find())
		{
			lowTemp = Integer.parseInt(m.group());
		}
		else
		{
			return null;
		}
		WeatherEnum e = WeatherEnum.find(wdata.weather);
		String location = weather.results.get(0).currentCity;
		
		return new WeatherInfo(e, e.name, (byte)lowTemp, (byte)hightTemp, location, wdata.wind);
	}
	
	public static void main(String[] args)
	{
		Pattern p = Pattern.compile("[-]?[0-9]+");
		Matcher m = p.matcher("26 ~ -18.5");
		int lowTemp, hightTemp;
		if(m.find())
		{
			lowTemp = Integer.parseInt(m.group());
		}
		else
		{
			return ;
		}
		
		if(m.find())
		{
			hightTemp = Integer.parseInt(m.group());
		}
		else
			return;
		System.out.println("low : " + lowTemp + " high : "+ hightTemp);
	}

}