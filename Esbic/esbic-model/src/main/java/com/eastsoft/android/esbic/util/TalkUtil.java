package com.eastsoft.android.esbic.util;

import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.IntercomTypeEnum;
import com.eastsoft.android.esbic.table.IntercomInfo;

import java.util.Date;

public class TalkUtil
{
    private static TalkUtil instance = new TalkUtil();
    private IntercomTypeEnum intercomTypeEnum;
    private DeviceInfo deviceInfo;
    private boolean isTalking;
    private String time;
    private long startTime;
    private long endTime;
    private long talkTime;

    private TalkUtil()
    {
        init();
    }

    private void init()
    {
        intercomTypeEnum = null;
        deviceInfo = null;
        isTalking = false;
        time = "";
        startTime = endTime = talkTime = 0;
    }

    public static TalkUtil getInstance()
    {
        if (instance == null)
        {
            synchronized (TalkUtil.class)
            {
                if (instance == null)
                {
                    instance = new TalkUtil();
                }
            }
        }
        return instance;
    }

    public void start(IntercomTypeEnum intercomTypeEnum, DeviceInfo deviceInfo)
    {
        time = TimeUtil.getDateTimeofNow3();
        this.intercomTypeEnum = intercomTypeEnum;
        this.deviceInfo = deviceInfo;
    }

    public void talk()
    {
        isTalking = true;
        startTime = new Date().getTime();
    }

    public void stop()
    {
        if(intercomTypeEnum == null || deviceInfo == null)
        {
            LogUtil.print(LogUtil.LogPriorityEnum.CORE_LOG_PRI_ERROR, "TalkUtil intercomTypeEnum or deviceInfo is null!");
            return;
        }
        endTime = new Date().getTime();
        if(isTalking == true)
        {
            if(intercomTypeEnum == IntercomTypeEnum.MISSED)
            {
                intercomTypeEnum = IntercomTypeEnum.RECEIVED;
            }
            talkTime = endTime - startTime;
        }
        IntercomInfo intercomInfo = new IntercomInfo(intercomTypeEnum.getType(), time, deviceInfo.toString(), talkTime);
        intercomInfo.save();
        init();
    }
}
