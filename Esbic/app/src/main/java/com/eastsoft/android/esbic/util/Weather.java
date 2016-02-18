package com.eastsoft.android.esbic.util;

/**
 * Created by sofa on 2016/1/21.
 */
public class Weather {
    private  String status;
    private  String lowTemperature;
    private  String highTemperature;
    private  String savedateWeather;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLowTemperature() {
        return lowTemperature;
    }

    public void setLowTemperature(String lowTemperature) {
        this.lowTemperature = lowTemperature;
    }

    public String getHighTemperature() {
        return highTemperature;
    }

    public void setHighTemperature(String highTemperature) {
        this.highTemperature = highTemperature;
    }

    public String getSavedateWeather() {
        return savedateWeather;
    }

    public void setSavedateWeather(String savedateWeather) {
        this.savedateWeather = savedateWeather;
    }
}
