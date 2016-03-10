package com.eastsoft.android.esbic.jni;

public enum ErrorCodeEnum
{
    TTE_SEARCH_DEVICE_TIMEOUT           (1, "搜索设备超时"),
    TTE_CALL_HANG_UP_TIMEOUT            (2, "呼叫挂断超时"),
    TTE_TALK_HANG_UP_TIMEOUT            (3, "通话挂断超时"),
    TTE_TALK_ANSWER_TIMEOUT             (4, "通话应答超时"),
    TTE_MONITOR_HANG_UP_TIMEOUT         (5, "监视挂断超时"),
    UNKNOWN                             (-1,"未知错误"),
    ;

    private int type;
    private String info;

    ErrorCodeEnum(int type, String info)
    {

        this.type = type;
        this.info = info;
    }

    public int getType()
    {
        return type;
    }

    public String getInfo()
    {
        return info;
    }

    public static ErrorCodeEnum find(int type)
    {
        for(ErrorCodeEnum i : ErrorCodeEnum.values())
        {
            if(i.type == type)
            {
                return i;
            }
        }
        return UNKNOWN;
    }
}
