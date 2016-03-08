package com.eastsoft.android.esbic.jni;

public enum DeviceTypeEnum
{
	DT_ROOM_MACHINE						("室内机", 0),
	DT_UNIT_DOOR_MACHINE				("门口机", 1),
	DT_WALL_MACHINE						("门墙机", 2),
	DT_SECONDARY_CONFIRMATION_MACHINE	("二次确认机", 3),
	DT_CENTER_MACHINE					("中心管理机", 4),
	UNKNOWN_MACHINE						("未知设备", -1),
	;
	
	String name;
	int type;
	
	private DeviceTypeEnum(String name, int type)
	{
		this.name = name;
		this.type = type;
	}

	public String getName()
	{
		return name;
	}

	public int getType()
	{
		return type;
	}
	
	public static DeviceTypeEnum find(int type)
	{
		for(DeviceTypeEnum i : DeviceTypeEnum.values())
		{
			if(i.type == type)
			{
				return i;
			}
		}
		return UNKNOWN_MACHINE;
	}
}
