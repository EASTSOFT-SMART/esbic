package com.eastsoft.android.esbic.ativity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.eastsoft.android.esbic.service.IModelService;

import org.litepal.LitePalApplication;

import java.util.Locale;

public class MyApplication extends Application
{
    private IModelService modelService;
    private static MyApplication instance;
    @Override
    public void onCreate()
    {
        super.onCreate();
        LitePalApplication.initialize(this);

        instance = this;
    }

    public IModelService getModelService() {
        return modelService;
    }

    public void setModelService(IModelService modelService) {
        this.modelService = modelService;
    }

    public static Context getAppContext()
    {
        return instance;
    }

    public static Resources getAppResources()
    {
        return instance.getResources();
    }
}
