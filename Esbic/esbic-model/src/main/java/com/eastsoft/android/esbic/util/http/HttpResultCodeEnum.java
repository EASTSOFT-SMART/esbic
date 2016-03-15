package com.eastsoft.android.esbic.util.http;


public enum HttpResultCodeEnum
{
	SUCCESS  						("success", 0),
	
	COULD_NOT_UPLOAD_FILE			("could not upload file", 1001),
	COULD_NOT_DELETE_FILE			("could not delete file", 1002),
	COULD_NOT_OBTAIN_DEVICE_INFO	("could not obtain device info", 1004),
	CPULD_NOT_FIND_DOWNLOAD_FILE	("could not upload file", 1005),
	UPLOAD_FILE_EXIST				("upload file already exists", 1007),
	CLOUD_INTERNAL_PROCESS_TIMEOUT	("cloud platform internal processing timeout", 1008),
	
	USER_ASSOCIATED_DEVICE_NOT_EXIST("Could not find the user associated device", 2002),
	BIND_REQUEST_FAILURE			("bind request failure", 2005),
	BIND_REQUEST_TIMEOUT			("bind request timeout", 2007),
	GATEWAY_ALREADY_BINDED			("gateway already binded", 2008),
	
	DOWNLOAD_QUEUE_OVERFLOW			("download queue overflow", 2605),
	UPLOAD_QUEUE_OVERFLOW			("upload queue overflow", 2606),
	
	DEVICE_CAN_NOT_UPGRADE			("device could not be upgraded to the specified version", 3001),
	DEVICE_DOES_NOT_MATCH			("device does not match, can not recover data", 3002),
	
	UNKNOW							("unknow http result code", 0xFFFFFFFF),
	;
	
	private String name;
	private int resultCode;
	
	private HttpResultCodeEnum(String name, int resultCode)
	{
		this.resultCode = resultCode;
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}

	public int getResultCode()
	{
		return resultCode;
	}
	
	public static HttpResultCodeEnum find(int resultCode)
	{
		for(HttpResultCodeEnum e : HttpResultCodeEnum.values())
		{
			if(e.resultCode == resultCode)
			{
				return e;
			}
		}
		return UNKNOW;
	}
	
	@Override
	public String toString()
	{
		return resultCode + " - " + name;
	}
}
