package com.eastsoft.android.esbic.weather;

import com.eastsoft.android.esbic.util.JsonUtil;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class WeatherInfo
{
	public final WeatherEnum weather;
	public final String description;
	public final byte lowTemperate;
	public final byte hightTemperate;
	public final String location;
	public final String wind;

	public WeatherInfo(WeatherEnum weather, String description, byte lowTemperate,
					   byte hightTemperate, String location, String wind)
	{
		this.weather = weather;
		this.description = description;
		this.lowTemperate = lowTemperate;
		this.hightTemperate = hightTemperate;
		this.location = location;
		this.wind = wind;
	}

	public byte[] getBytes()
	{
		try
		{
			ByteBuffer buffer = ByteBuffer.allocate(0x100);
			buffer.put((byte) weather.sno);

			buffer.put(description.getBytes("GBK"));
			buffer.put((byte) 0);
			buffer.put(lowTemperate);
			buffer.put(hightTemperate);
			buffer.put(location.getBytes("GBK"));
			buffer.put((byte) 0);
			return Arrays.copyOf(buffer.array(), buffer.position());
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
		
	}

	public String toString()
	{
		return JsonUtil.toString(this);
	}
}
