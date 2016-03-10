package com.eastsoft.android.esbic.jni;

import com.eastsoft.android.esbic.util.JsonUtil;

public class DefencesSwitchInfo
{
    private int masterSwitch;
    private int[] areaSwitch;

    public DefencesSwitchInfo(int masterSwitch, int[] areaSwitch)
    {
        this.masterSwitch = masterSwitch;
        this.areaSwitch = areaSwitch;
    }

    public int getMasterSwitch()
    {
        return masterSwitch;
    }

    public int[] getAreaSwitch()
    {
        return areaSwitch;
    }

    @Override
    public String toString()
    {
       return JsonUtil.toString(this);
    }
}
