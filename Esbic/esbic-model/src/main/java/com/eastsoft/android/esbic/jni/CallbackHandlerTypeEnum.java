package com.eastsoft.android.esbic.jni;

public enum CallbackHandlerTypeEnum
{
    CALLBACK_ALARM_IN               	("", 100),
    CALLBACK_IMP_TEXT_PUSH          	("", 200),
    CALLBACK_IMP_AD_PUSH           		("", 201),
    CALLBACK_INACTIVE_CALL          	("", 300),
    CALLBACK_VIDEO_URL              	("", 301),
    CALLBACK_AUDIO_DATA             	("", 302),
    CALLBACK_INACTIVE_CALL_HANG_UP  	("", 303),
    CALLBACK_INACTIVE_TALK_HANG_UP  	("", 304),
    CALLBACK_TALK_CONFIRM            	("", 305),
    CALLBACK_CALL_RESPONSE_BUSY     	("", 306),
    CALLBACK_UNLOCK_CONFIRM         	("", 307),
    CALLBACK_MONITOR_CONFIRM        	("", 308),
    CALLBACK_INACTIVE_HANG_UP_MONITOR   ("", 309),
    CALLBACK_MONITOR_RESPONSE_BUSY  	("", 310),
    CALLBACK_UNKNOWN                 	("", -1),
    ;

    private String name;
    private int type;

    private CallbackHandlerTypeEnum(String name, int type)
    {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public static CallbackHandlerTypeEnum find(int type)
    {
        for (CallbackHandlerTypeEnum i : CallbackHandlerTypeEnum.values())
        {
            if(i.type == type)
            {
                return i;
            }
        }
        return CALLBACK_UNKNOWN;
    }
}