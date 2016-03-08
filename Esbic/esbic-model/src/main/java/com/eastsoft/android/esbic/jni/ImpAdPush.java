package com.eastsoft.android.esbic.jni;

import org.litepal.crud.DataSupport;

import com.eastsoft.android.esbic.util.JsonUtil;

public class ImpAdPush extends DataSupport
{
    private int enable;
    private int play_time;
    private String url;

    public ImpAdPush(int enable, int play_time, String url) {
        this.enable = enable;
        this.play_time = play_time;
        this.url = url;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getPlay_time() {
        return play_time;
    }

    public void setPlay_time(int play_time) {
        this.play_time = play_time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @java.lang.Override
    public java.lang.String toString() {
    	 return JsonUtil.toString(this);
    }
}