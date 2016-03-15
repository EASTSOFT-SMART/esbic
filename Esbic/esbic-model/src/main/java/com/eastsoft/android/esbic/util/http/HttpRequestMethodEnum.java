package com.eastsoft.android.esbic.util.http;

public enum HttpRequestMethodEnum
{
	GET		("GET"),
	PUT		("PUT"), 
	POST	("POST"), 
	;
	
	private final String value;

	private HttpRequestMethodEnum(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}
}
