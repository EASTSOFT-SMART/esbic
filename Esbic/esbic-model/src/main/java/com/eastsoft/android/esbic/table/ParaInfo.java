package com.eastsoft.android.esbic.table;

import org.litepal.crud.DataSupport;

import com.eastsoft.android.esbic.util.JsonUtil;

public class ParaInfo extends DataSupport
{
	private String name;
	private String value;

	public ParaInfo(String name, String value)
	{
		this.name = name;
		this.value = value;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	@java.lang.Override
	public java.lang.String toString()
	{
		return JsonUtil.toString(this);
	}
}
