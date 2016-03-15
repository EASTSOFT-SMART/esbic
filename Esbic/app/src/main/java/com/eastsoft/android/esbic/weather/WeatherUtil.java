package com.eastsoft.android.esbic.weather;

import com.eastsoft.android.esbic.util.IpUtil;
import com.eastsoft.android.esbic.util.JsonUtil;
import com.eastsoft.android.esbic.util.http.HttpRequestMethodEnum;
import com.eastsoft.android.esbic.util.http.HttpResult;
import com.eastsoft.android.esbic.util.http.HttpServiceImpl;

import java.net.URLEncoder;

public class WeatherUtil
{
	public static WeatherInfo getWeather(String location)
	{
		try{
			String url = new String("http://api.map.baidu.com/telematics/v3/weather?location="+URLEncoder.encode(location, "UTF-8")+"&output=json&ak=850c2f05fbd82ccd0a77998a72c16e6a");

			HttpServiceImpl service = new HttpServiceImpl.Builder(url)
					.requestMethod(HttpRequestMethodEnum.GET).build();
			HttpResult result = service.getHttpResult(null);
			System.out.println(result);
			BaiduWeather weather = JsonUtil.fromJson(new String(result.content),
					BaiduWeather.class);
			
			return new BaiduWeatherAdapter().transform(weather);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getLocation()
	{
		try{
			String pubIp = IpUtil.getMyPublicIp();
			if(pubIp == null)
			{
				return pubIp;
			}
			
			String url = "http://api.map.baidu.com/location/ip?ak=850c2f05fbd82ccd0a77998a72c16e6a&ip="+pubIp+"&coor=bd09ll";

			HttpServiceImpl service = new HttpServiceImpl.Builder(url)
					.requestMethod(HttpRequestMethodEnum.GET).build();
			HttpResult result = service.getHttpResult(null);
			if(result.isSuccess() == false)
			{
				return null;
			}
			Location location = JsonUtil.fromJson(new String(result.content),
					Location.class);
			
			if(location == null || location.content.status !=0 || location.content.address_detail == null)
			{
				return null;
			}
			
			return location.content.address_detail.city;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args)
	{
		String tmp = getLocation();
		System.out.println(tmp);
		WeatherInfo w = getWeather(tmp);
		System.out.println(w);

		tmp = new String("\u5c71\u4e1c");
		System.out.println(tmp);
	}
}
