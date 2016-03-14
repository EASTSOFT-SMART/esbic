package com.eastsoft.android.esbic.util;

import android.util.Log;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by sofa on 2016/1/21.
 */
public class QueryWeatherInformation {
    //private String cityName;
    private String cityCode;
    private String city="青岛";
    private String weatherUrl="http://php.weather.sina."+
            "com.cn/xml.php?password=DJOYnieT8234jlsK&day=0";

    public Weather getXml(){
        URL url;
        try {
            weatherUrl+="&city="+ URLEncoder.encode(city,"GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.v("weatherUrl","weatherUrl出现异常");
        }
        Weather weather=null;

        try {
            url=new URL(weatherUrl);
            XmlPullParser parser= Xml.newPullParser();
            parser.setInput(url.openStream(),"UTF-8");
            int event =parser.getEventType();
            while(event!=XmlPullParser.END_DOCUMENT){
                switch (event){
                   case XmlPullParser.START_DOCUMENT:
                       break;
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("Weather")){
                            weather=new Weather();
                        }
                        if (null!=weather){
                            if (parser.getName().equals("status1")){
                                weather.setStatus(parser.nextText());
                            }else if (parser.getName().equals("temperature1")){
                                weather.setHighTemperature(parser.nextText());
                            }else if (parser.getName().equals("temperature2")){
                                weather.setLowTemperature(parser.nextText());
                            }else if (parser.getName().equals("savedate_weather")){
                                weather.setSavedateWeather(parser.nextText());
                            }
                        }
                       break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                event=parser.next();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
               return weather;
    }
}
