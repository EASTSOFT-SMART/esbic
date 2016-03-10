package com.eastsoft.android.esbic.table;

import org.litepal.crud.DataSupport;

import com.eastsoft.android.esbic.util.JsonUtil;
import com.eastsoft.android.esbic.util.TimeUtil;

public class IntercomInfo extends DataSupport
{
	private int type;
	private String time;
	private String device;
	private long talkTime;

	public IntercomInfo(int type, String time, String device, long talkTime)
	{
		this.type = type;
		this.time = time;
		this.device = device;
		this.talkTime = talkTime;

		this.maxRecord(500);
	}

	public long getTalkTime()
	{
		return talkTime;
	}

	public void setTalkTime(long talkTime)
	{
		this.talkTime = talkTime;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getTime()
	{
		return time;
	}

	public void setTime(String time)
	{
		this.time = time;
	}
	
	public String getDevice()
	{
		return device;
	}

	public void setDevice(String device)
	{
		this.device = device;
	}

	@java.lang.Override
	public java.lang.String toString()
	{
		return JsonUtil.toString(this);
	}
}
