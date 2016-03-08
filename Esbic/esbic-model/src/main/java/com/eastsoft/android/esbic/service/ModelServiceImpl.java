package com.eastsoft.android.esbic.service;

import android.content.ContentValues;
import android.content.Context;

import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.IntercomTypeEnum;
import com.eastsoft.android.esbic.jni.IpAddressInfo;
import com.eastsoft.android.esbic.jni.JniUtil;
import com.eastsoft.android.esbic.table.AlarmInfo;
import com.eastsoft.android.esbic.table.IntercomInfo;
import com.eastsoft.android.esbic.table.MessageInfo;
import com.eastsoft.android.esbic.table.ParaInfo;
import com.eastsoft.android.esbic.util.AudioUtil;
import com.eastsoft.android.esbic.util.JsonUtil;
import com.eastsoft.android.esbic.util.LogUtil;
import com.eastsoft.android.esbic.util.TimeUtil;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ModelServiceImpl implements IModelService
{
    public ModelServiceImpl(Context context) {
        BroadcastUtil.getInstance().init(context);
    }

    public void init_intercom_core(DeviceInfo deviceInfo)
    {
        JniUtil.getInstance().init(deviceInfo);
    }

    public void ui_talk_answer() {
        JniUtil.getInstance().ui_talk_answer();
        AudioUtil.getInstance().startRecordAudio();
        IntercomInfo intercomInfo = new IntercomInfo(IntercomTypeEnum.RECEIVED.getType(), JniUtil.getInstance().getCurrentDeviceInfo().toString());
        intercomInfo.save();
    }

    public void active_hang_up() {
        JniUtil.getInstance().active_hang_up();
        AudioUtil.getInstance().stopRecordAudio();
    }

    public int active_call_user(DeviceInfo deviceInfo)
    {
        IntercomInfo intercomInfo = new IntercomInfo(IntercomTypeEnum.CALL_ROOM.getType(), deviceInfo.toString());
        intercomInfo.save();
        return JniUtil.getInstance().active_call_user(deviceInfo);
    }

    public int call_center_manager(String center_addr)
    {
        return JniUtil.getInstance().call_center_manager(center_addr);
    }

    public int unlock_door()
    {
        return JniUtil.getInstance().unlock_door();
    }

    public int ui_req_monitor(DeviceInfo deviceInfo)
    {
        return JniUtil.getInstance().ui_req_monitor(deviceInfo);
    }

    public int active_hang_up_monitor(DeviceInfo deviceInfo)
    {
        return JniUtil.getInstance().active_hang_up_monitor(deviceInfo);
    }

    public void setDeviceInfo(DeviceInfo deviceInfo)
    {
        ParaInfo paraInfo = new ParaInfo("deviceInfo", deviceInfo.toString());
        ParaInfo tmp = getParaInfo("deviceInfo");
        if(tmp == null)
        {
            paraInfo.save();
            JniUtil.getInstance().init(deviceInfo);
        }else
        {
            updateParaInfo(paraInfo);
            JniUtil.getInstance().modefy_device_info(deviceInfo);
        }
    }

    public DeviceInfo getDeviceInfo()
    {
        ParaInfo paraInfo = getParaInfo("deviceInfo");
        if(paraInfo == null)
        {
            return null;
        }
        return JsonUtil.fromJson(paraInfo.getValue(), DeviceInfo.class);
    }

    public void setIpAddressInfo(IpAddressInfo ipAddressInfo)
    {
        ParaInfo paraInfo = new ParaInfo("ipAddressInfo", ipAddressInfo.toString());
        ParaInfo tmp = getParaInfo("ipAddressInfo");
        if(tmp == null)
        {
            paraInfo.save();
            JniUtil.getInstance().init_imp_task(ipAddressInfo.getImpAdress());
        }else
        {
            updateParaInfo(paraInfo);
        }
        JniUtil.getInstance().modefy_add_info(ipAddressInfo.getImpAdress(), ipAddressInfo.getCenterAddress());
    }

    public IpAddressInfo getIpAddressInfo()
    {
        ParaInfo paraInfo = getParaInfo("ipAddressInfo");
        if(paraInfo == null)
        {
            return null;
        }
        return JsonUtil.fromJson(paraInfo.getValue(), IpAddressInfo.class);
    }

    public void setProjectPassword(String projectPassword)
    {
        ParaInfo paraInfo = new ParaInfo("projectPassword", projectPassword);
        ParaInfo tmp = getParaInfo("projectPassword");
        if(tmp == null)
        {
            paraInfo.save();
        }else
        {
            updateParaInfo(paraInfo);
        }
    }
    public String getProjectPassword()
    {
        ParaInfo paraInfo = getParaInfo("projectPassword");
        if(paraInfo == null)
        {
            return null;
        }
        return paraInfo.getValue();
    }
    public void setUserPassword(String userPassword)
    {
        ParaInfo paraInfo = new ParaInfo("userPassword", userPassword);
        ParaInfo tmp = getParaInfo("userPassword");
        if(tmp == null)
        {
            paraInfo.save();
        }else
        {
            updateParaInfo(paraInfo);
        }
    }
    public String getUserPassword()
    {
        ParaInfo paraInfo = getParaInfo("userPassword");
        if(paraInfo == null)
        {
            return null;
        }
        return paraInfo.getValue();
    }

    public List<IntercomInfo> getIntecomInfo()
    {
        return DataSupport.findAll(IntercomInfo.class);
    }

    public List<MessageInfo> getMessageInfo()
    {
        return DataSupport.findAll(MessageInfo.class);
    }

    public List<AlarmInfo> getAlarmInfo()
    {
        return DataSupport.findAll(AlarmInfo.class);
    }


    private void updateParaInfo(ParaInfo paraInfo)
    {
        ContentValues values = new ContentValues();
        values.put("value", paraInfo.getValue());
        DataSupport.updateAll(ParaInfo.class, values, "name = ?", paraInfo.getName());
        LogUtil.print(LogUtil.LogPriorityEnum.CORE_LOG_PRI_INFO, "UPDATE -> " + paraInfo.toString());
    }

    private ParaInfo getParaInfo(String colname)
    {
        List<ParaInfo> paraInfos = DataSupport.where("name=?", colname).find(ParaInfo.class);
        if(paraInfos == null || paraInfos.size() == 0)
        {
            return null;
        }
        return paraInfos.get(0);
    }
}
