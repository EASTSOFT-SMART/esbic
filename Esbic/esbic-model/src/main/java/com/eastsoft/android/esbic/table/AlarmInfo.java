package com.eastsoft.android.esbic.table;

import org.litepal.crud.DataSupport;

import com.eastsoft.android.esbic.util.JsonUtil;
import com.eastsoft.android.esbic.util.TimeUtil;

public class AlarmInfo extends DataSupport
{
	private int type;
	private String time;
	
	public AlarmInfo(int type)
	{
		this.type = type;
		this.time = TimeUtil.getDateTimeofNow3();
		this.maxRecord(500);
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

	@java.lang.Override
	public java.lang.String toString()
	{
		return JsonUtil.toString(this);
	}
}
