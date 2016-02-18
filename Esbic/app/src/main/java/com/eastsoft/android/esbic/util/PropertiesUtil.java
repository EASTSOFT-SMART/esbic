package com.eastsoft.android.esbic.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Created by sofa on 2016/1/21.
 */
public class PropertiesUtil {
    private static Properties properties;
    private static PropertiesUtil propertiesUtil;
    private List<String> keyList=new ArrayList<>();
    private Context context;
    private String propertiesPath="weather.properties";
    private InputStream in;

    private PropertiesUtil(Context context){
        this.context=context;
        try {
            in=this.context.getAssets().open(propertiesPath);
        } catch (IOException e) {
            Log.v("解析Properties文件:","出现问题!");
        }
    }
    //获取PropertiesUtil新的实例
    public PropertiesUtil getInstance(Context context){
        if (null!=propertiesUtil){
            propertiesUtil=new PropertiesUtil(context);
            properties=new Properties();
            Reader reader=null;
            try {
                reader=new BufferedReader(new InputStreamReader(in,"GBK"));
            } catch (UnsupportedEncodingException e) {
                Log.v("获取BufferReader数据流时:","出现异常");
            }
            try {
                properties.load(reader);
            } catch (IOException e) {
                Log.v("properties.load(read)","出现异常");
            }
        }
        return propertiesUtil;
    }
   //根据value获取Key
   public String getValue(String key){
       return properties.getProperty(key);
   }

  //根据城市获取城市对应的查询天气的序列号
    public String getKey(String value){
        String val="";
        Set<Object> keys=properties.keySet();
        for (Object obj:keys){
            val=getValue(obj.toString());
            if (val.equals(value)){
                val=obj.toString();
                break;
            }
        }
        return val;
    }
















}
