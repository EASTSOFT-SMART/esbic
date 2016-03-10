package com.eastsoft.android.esbic.table;

import org.litepal.crud.DataSupport;

import com.eastsoft.android.esbic.util.JsonUtil;
import com.eastsoft.android.esbic.util.TimeUtil;

public class MessageInfo extends DataSupport
{
	private int id;
	private int type;
	private String time;
	private boolean isRead;
	private int playTime;
	private String message;
	
	public MessageInfo(int type, int playTime, String message)
	{
		this.type = type;
		this.time = TimeUtil.getDateTimeofNow3();
		this.playTime = playTime;
		this.message = message;
		this.maxRecord(500);
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public boolean isRead()
	{
		return isRead;
	}

	public void setRead(boolean read)
	{
		isRead = read;
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

	public int getPlayTime()
	{
		return playTime;
	}

	public void setPlayTime(int playTime)
	{
		this.playTime = playTime;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	@java.lang.Override
	public java.lang.String toString()
	{
		return JsonUtil.toString(this);
	}
}
