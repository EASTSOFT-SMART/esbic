package com.eastsoft.android.esbic.jni;

public enum MessageInfoEnum
{
    MESSAGE					(0, "message"),
    AD						(1, "ad"),
    UNKNOWN					(-1, "unknown"),
    ;

    int type;
    String value;

    MessageInfoEnum(int type, String value)
    {
        this.type = type;
        this.value = value;
    }

    public int getType()
    {
        return type;
    }

    public String getValue()
    {
        return value;
    }

    public static MessageInfoEnum find(int type)
    {
        for(MessageInfoEnum i : MessageInfoEnum.values())
        {
            if(i.type == type)
            {
                return i;
            }
        }
        return UNKNOWN;
    }
}
