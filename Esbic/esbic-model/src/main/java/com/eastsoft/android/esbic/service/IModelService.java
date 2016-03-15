package com.eastsoft.android.esbic.service;

import com.eastsoft.android.esbic.jni.DeviceInfo;
import com.eastsoft.android.esbic.jni.IpAddressInfo;
import com.eastsoft.android.esbic.table.AlarmInfo;
import com.eastsoft.android.esbic.table.IntercomInfo;
import com.eastsoft.android.esbic.table.MessageInfo;
import com.eastsoft.android.esbic.table.ParaInfo;

import java.util.List;

public interface IModelService
{
    public void init_intercom_core(DeviceInfo deviceInfo);      //  初始化楼宇对讲库
    public void init_imp_task(String impAddress);               //  初始化IMP服务器
    public void ui_talk_answer();                               //  接听呼叫
    public void active_hang_up();                               //  挂断接听和呼叫
    public int active_call_user(DeviceInfo deviceInfo);         //  呼叫室内机
    public int call_center_manager(String center_addr);         //  呼叫中心管理机
    public int unlock_door();                                   //  开锁
    public int ui_req_monitor(DeviceInfo deviceInfo);           //  监视门口机
    public int active_hang_up_monitor(DeviceInfo deviceInfo);   //  挂断监视门口机

    public void setDeviceInfo(DeviceInfo deviceInfo);           //  设置设备信息
    public DeviceInfo getDeviceInfo();                          //  获取设备信息

    public void setIpAddressInfo(IpAddressInfo ipAddressInfo);  //  设置设备ip地址，服务器地址，IMP地址
    public IpAddressInfo getIpAddressInfo();                    //  获取设备ip地址，服务器地址，IMP地址

    public void setProjectPassword(String projectPassword);     //  设置工程密码
    public String getProjectPassword();                         //  获取工程密码

    public void setUserPassword(String userPassword);           //  设置用户密码
    public String getUserPassword();                            //  获取用户密码

    public void saveParaInfo(ParaInfo paraInfo);                //  保存para信息
    public ParaInfo getParaInfoByName(String colname);          //  获取para中的信息
    public List<IntercomInfo> getIntecomInfo();                 //  获取对讲记录
    public List<MessageInfo> getMessageInfo();                  //  获取消息和广告记录
    public List<AlarmInfo> getAlarmInfo();                      //  获取报警记录
}
