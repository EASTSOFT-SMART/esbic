package com.eastsoft.android.esbic.service;

import android.content.Context;
import android.content.Intent;

import com.eastsoft.android.esbic.util.LogUtil;

/**
 * Created by yangrm on 2016/2/20 9:31.
 */
public class BroadcastUtil
{
    private static BroadcastUtil instance = new BroadcastUtil();
    private Context context;

    private BroadcastUtil()
    {

    }

    public static BroadcastUtil getInstance()
    {
        if (instance == null)
        {
            synchronized (BroadcastUtil.class)
            {
                if (instance == null)
                {
                    instance = new BroadcastUtil();
                }
            }
        }
        return instance;
    }

    public void init(Context context)
    {
        this.context = context;
    }

    public boolean sendBoardcast(BroadcastTypeEnum e, String value) {
        LogUtil.print(LogUtil.LogPriorityEnum.CORE_LOG_PRI_INFO, "Model send broadcast type: " + e.name + ", value: " + value);
        if (context == null) {
            LogUtil.print(LogUtil.LogPriorityEnum.CORE_LOG_PRI_ERROR, "BroadcastUtil context is null !!");
            return false;
        }
        Intent intent = new Intent();
        intent.putExtra("cmd", e.getType());
        intent.putExtra("value", value);
        intent.setAction("com.eastsoft.android.esbic.model");
        context.sendBroadcast(intent);
        return true;
    }
}
