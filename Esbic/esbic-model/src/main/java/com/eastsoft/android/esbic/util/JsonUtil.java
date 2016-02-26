package com.eastsoft.android.esbic.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonUtil
{
	private static Gson gson = new Gson();
	
	public static String toString(Object obj)
	{
		if(obj == null)
		{
			throw new IllegalArgumentException("object is null");
		}
		
		return gson.toJson(obj);
	}
	
	public static <T> T fromJson(String json, Class<T> classOfT)
	{
		try
		{
			return gson.fromJson(json, classOfT);
		} catch (Exception e)
		{
			LogUtil.print(LogUtil.LogPriorityEnum.CORE_LOG_PRI_ERROR, e.getMessage());
			return null;
		}
	}

}
