package com.eastsoft.android.esbic.ativity;

import android.app.Application;

import com.eastsoft.android.esbic.service.IModelService;

import org.litepal.LitePalApplication;

public class MyApplication extends Application
{
    private IModelService modelService;

    @Override
    public void onCreate()
    {
        super.onCreate();
        LitePalApplication.initialize(this);
    }

    public IModelService getModelService() {
        return modelService;
    }

    public void setModelService(IModelService modelService) {
        this.modelService = modelService;
    }
}
