package com.eastsoft.android.esbic.jni;

public enum IntercomTypeEnum
{
	RECEIVED					(0, "已接呼叫"),			//	已接
	MISSED						(1, "未接呼叫"),			//	未接
	CALL_ROOM					(2, "已拨呼叫"),			//	已拨室内机
	CALL_CENTER					(3, "center"),			//	呼叫中心管理机
	MONITOR_DOOR				(4, "door"),			//	监视门口机
	OPEN_LOCK					(5, "lock"),			//	开锁
	UNKNOWN						(-1, "unknown"),		//	未知
	;
	
	int type;
	String name;
	
	private IntercomTypeEnum(int type, String name)
	{
		this.type = type;
		this.name = name;
	}

	public int getType()
	{
		return type;
	}

	public String getName()
	{
		return name;
	}

	public static IntercomTypeEnum find(int type)
	{
		for(IntercomTypeEnum i : IntercomTypeEnum.values())
		{
			if(i.type == type)
			{
				return i;
			}
		}
		return UNKNOWN;
	}
}
