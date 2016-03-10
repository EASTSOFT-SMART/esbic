package com.eastsoft.android.esbic.jni;

import com.eastsoft.android.esbic.service.BroadcastTypeEnum;
import com.eastsoft.android.esbic.service.BroadcastUtil;
import com.eastsoft.android.esbic.table.AlarmInfo;
import com.eastsoft.android.esbic.table.IntercomInfo;
import com.eastsoft.android.esbic.table.MessageInfo;
import com.eastsoft.android.esbic.util.AudioUtil;
import com.eastsoft.android.esbic.util.LogUtil;
import com.eastsoft.android.esbic.util.TalkUtil;

/**
 * 34 native
 * 16 register
 *
 */
public class JniUtil
{
	private static JniUtil instance = new JniUtil();

	private JniUtil()
	{
		try
		{
			System.loadLibrary("IntercomCoreJni");
			LogUtil.print(LogUtil.LogPriorityEnum.CORE_LOG_PRI_INFO, "libIntercomCoreJni.so load success!");
		} catch (UnsatisfiedLinkError e)
		{
			LogUtil.print(LogUtil.LogPriorityEnum.CORE_LOG_PRI_ERROR, "Cannot load library : " + e.toString());
		}
	}

	public static JniUtil getInstance()
	{
		if (instance == null)
		{
			synchronized (JniUtil.class)
			{
				if (instance == null)
				{
					instance = new JniUtil();
				}
			}
		}
		return instance;
	}

	public void init(DeviceInfo deviceInfo)
	{
		register_log_handler();
		init_intercom_core(deviceInfo);
		init_talk_task();
		register_inactive_call_handler();
		register_call_answer_found_handler();
		register_video_url_handler();
		register_inactive_call_hang_up_handler();
		register_talk_answer_handler();
		register_talk_confim_handler();
		register_audio_data_handler();
		register_inactive_talk_hang_up_handler();
		register_call_busy_handler();
		register_unlock_door_confirm_handler();
		register_monitor_confirm_handler();
		register_inactive_hang_up_monitor_handler();
		register_monitor_response_busy_handler();
		register_text_push_handler();
		register_ad_push_handler();
		register_alarm_handler();
		register_talk_task_error_handler();
	}

	public void destory()
	{
		destory_imp_task();
		destory_alarm_in();
		destory_intercom_core();
//		unregister_handler(null);
	}

	// lib related
	public native int init_intercom_core(DeviceInfo deviceInfo);
	public native int destory_intercom_core();
	public native int modefy_device_info(DeviceInfo deviceInfo);

	// Intercom communication
	// 3.1
	public native int init_talk_task();
	// 3.2
	public native int unregister_handler(int callbackHandlerType);
	// 3.3
	public native void register_inactive_call_handler(String classPath, String methodName, String paraType);
	public void register_inactive_call_handler()
	{
		register_inactive_call_handler(this.getClass().getName().replace(".","/"), "inactive_call_handler", "("+generateClassPath(DeviceInfo.class)+")V");
	}
	public void inactive_call_handler(DeviceInfo deviceInfo)
	{
		BroadcastUtil.getInstance().sendBoardcast(BroadcastTypeEnum.CALL_REQUEST, deviceInfo.toString());
		TalkUtil.getInstance().start(IntercomTypeEnum.MISSED, deviceInfo);
	}
	// 3.4
	public native void register_call_answer_found_handler(String classPath, String methodName, String paraType);
	public void register_call_answer_found_handler()
	{
		register_call_answer_found_handler(this.getClass().getName().replace(".","/"), "call_answer_found_handler", "("+generateClassPath(DeviceInfo.class)+")V");
	}
	public void call_answer_found_handler(DeviceInfo deviceInfo)
	{
		BroadcastUtil.getInstance().sendBoardcast(BroadcastTypeEnum.CALL_CONFIRM, deviceInfo.toString());
	}
	// 3.5
	public native int register_video_url_handler(String classPath, String methodName, String paraType);
	public void register_video_url_handler()
	{
		register_video_url_handler(this.getClass().getName().replace(".","/"), "video_url_handler", "(Ljava/lang/String;)V");
	}
	public void video_url_handler(String url)
	{
		BroadcastUtil.getInstance().sendBoardcast(BroadcastTypeEnum.PLAY_VIDEO, url);
	}
	// 3.6
	public native void register_inactive_call_hang_up_handler(String classPath, String methodName, String paraType);
	public void register_inactive_call_hang_up_handler()
	{
		register_inactive_call_hang_up_handler(this.getClass().getName().replace(".","/"), "inactive_call_hang_up_handler", "()V");
	}
	public void inactive_call_hang_up_handler()
	{
		AudioUtil.getInstance().stopRecordAudio();
		TalkUtil.getInstance().stop();
		BroadcastUtil.getInstance().sendBoardcast(BroadcastTypeEnum.HANG_UP, "null");
	}
	// 3.7
	public native int ui_talk_answer();
	// 3.8
	public native void register_talk_answer_handler(String classPath, String methodName, String paraType);
	public void register_talk_answer_handler()
	{
		register_talk_answer_handler(this.getClass().getName().replace(".","/"), "talk_answer_handler", "()V");
	}
	public void talk_answer_handler()
	{
		TalkUtil.getInstance().talk();
		BroadcastUtil.getInstance().sendBoardcast(BroadcastTypeEnum.CALL_ANSWER_CONFIRM, "null");
	}
	// 3.9
	public native void register_talk_confim_handler(String classPath, String methodName, String paraType);
	public void register_talk_confim_handler()
	{
		register_talk_confim_handler(this.getClass().getName().replace(".","/"), "talk_confim_handler", "()V");
	}
	public void talk_confim_handler()
	{
		AudioUtil.getInstance().startRecordAudio();
	}
	// 3.10
	public native void register_audio_data_handler(String classPath, String methodName, String paraType);
	public void register_audio_data_handler()
	{
		register_audio_data_handler(this.getClass().getName().replace(".","/"), "audio_data_handler", "([B)V");
	}
	public void audio_data_handler(byte[] data)
	{
		AudioUtil.getInstance().startPlayAudio(data);
	}
	// 3.11
	public native void push_audio_data(byte[] data);
	// 3.12
	public native void active_hang_up();
	// 3.13
	public native void register_inactive_talk_hang_up_handler(String classPath, String methodName, String paraType);
	public void register_inactive_talk_hang_up_handler()
	{
		register_inactive_talk_hang_up_handler(this.getClass().getName().replace(".","/"), "inactive_talk_hang_up_handler", "()V");
	}
	public void inactive_talk_hang_up_handler()
	{
		AudioUtil.getInstance().stopRecordAudio();
		TalkUtil.getInstance().stop();
		BroadcastUtil.getInstance().sendBoardcast(BroadcastTypeEnum.HANG_UP, "null");
	}
	// 3.14
	public native int active_call_user(DeviceInfo deviceInfo);
	// 3.15
	public native void register_call_busy_handler(String classPath, String methodName, String paraType);
	public void register_call_busy_handler()
	{
		register_call_busy_handler(this.getClass().getName().replace(".","/"), "call_busy_handler", "()V");
	}
	public void call_busy_handler()
	{
		BroadcastUtil.getInstance().sendBoardcast(BroadcastTypeEnum.DEVICE_BUSY, "null");
	}
	// 3.16
	public native int call_center_manager(String center_addr);
	// 3.17
	public native int unlock_door();
	// 3.18
	public native void register_unlock_door_confirm_handler(String classPath, String methodName, String paraType);
	public void register_unlock_door_confirm_handler()
	{
		register_unlock_door_confirm_handler(this.getClass().getName().replace(".","/"), "unlock_door_confirm_handler", "()V");
	}
	public void unlock_door_confirm_handler()
	{
		BroadcastUtil.getInstance().sendBoardcast(BroadcastTypeEnum.OPEN_LOCK_CONFIRM, "null");
	}
	// 3.19
	public native int ui_req_monitor(DeviceInfo deviceInfo);
	// 3.20
	public native void register_monitor_confirm_handler(String classPath, String methodName, String paraType);
	public void register_monitor_confirm_handler()
	{
		register_monitor_confirm_handler(this.getClass().getName().replace(".","/"), "monitor_confirm_handler", "("+generateClassPath(DeviceInfo.class)+")V");
	}
	public void monitor_confirm_handler(DeviceInfo deviceInfo)
	{
		BroadcastUtil.getInstance().sendBoardcast(BroadcastTypeEnum.MONITOR_CONFIRM, deviceInfo.toString());
	}
	// 3.21
	public native int active_hang_up_monitor(DeviceInfo deviceInfo);
	// 3.22
	public native void register_inactive_hang_up_monitor_handler(String classPath, String methodName, String paraType);
	public void register_inactive_hang_up_monitor_handler()
	{
		register_inactive_hang_up_monitor_handler(this.getClass().getName().replace(".","/"), "inactive_hang_up_monitor_handler", "("+generateClassPath(DeviceInfo.class)+")V");
	}
	public void inactive_hang_up_monitor_handler(DeviceInfo deviceInfo)
	{
		BroadcastUtil.getInstance().sendBoardcast(BroadcastTypeEnum.MONITOR_HANG_UP, deviceInfo.toString());
	}
	// 3.23
	public native void register_monitor_response_busy_handler(String classPath, String methodName, String paraType);
	public void register_monitor_response_busy_handler()
	{
		register_monitor_response_busy_handler(this.getClass().getName().replace(".","/"), "monitor_response_busy_handler", "("+generateClassPath(DeviceInfo.class)+")V");
	}
	public void monitor_response_busy_handler(DeviceInfo deviceInfo)
	{
		BroadcastUtil.getInstance().sendBoardcast(BroadcastTypeEnum.DEVICE_BUSY, deviceInfo.toString());
	}
	// 4.1
	public native int init_imp_task(String imp_addr);
	public native int modify_imp_addr(String imp_addr);

	// 4.2
	public native int destory_imp_task();
	// 4.3
	public native int register_text_push_handler(String classPath, String methodName, String paraType);
	public void register_text_push_handler()
	{
		register_text_push_handler(this.getClass().getName().replace(".","/"), "imp_text_push_handler", "(Ljava/lang/String;)V");
	}
	public void imp_text_push_handler(String str)
	{
		BroadcastUtil.getInstance().sendBoardcast(BroadcastTypeEnum.RECEIVE_MESSAGE, str);
		MessageInfo info = new MessageInfo(MessageInfoEnum.MESSAGE.getType(),0,str);
		info.maxRecord(500);
		info.save();
	}
	// 4.4
	public native int register_ad_push_handler(String classPath, String methodName, String paraType);
	public void register_ad_push_handler()
	{
		register_ad_push_handler(this.getClass().getName().replace(".","/"), "imp_ad_push_handler", "("+generateClassPath(ImpAdPush.class)+")V");
	}
	public void imp_ad_push_handler(ImpAdPush push)
	{
		BroadcastUtil.getInstance().sendBoardcast(BroadcastTypeEnum.RECEIVE_AD, push.toString());
		MessageInfo info = new MessageInfo(MessageInfoEnum.MESSAGE.getType(),push.getPlay_time(),push.getUrl());
		info.maxRecord(500);
		info.save();
	}
	// 5.1
	public native int init_alarm_in(DefencesSwitchInfo defencesSwitchInfoInfo);
	public native int modify_alarm_in_defences_area(DefencesSwitchInfo defencesSwitchInfoInfo);
	// 5.2
	public native int destory_alarm_in();
	// 5.3
	public native int register_alarm_handler(String classPath, String methodName, String paraType);
	public void register_alarm_handler()
	{
		register_alarm_handler(this.getClass().getName().replace(".","/"), "alarm_in_handler", "(I)V");
	}
	public void alarm_in_handler(int channel)
	{
		BroadcastUtil.getInstance().sendBoardcast(BroadcastTypeEnum.RECEIVE_ALARM, channel+"");
		AlarmInfo alarmInfo = new AlarmInfo(channel);
		alarmInfo.maxRecord(500);
		alarmInfo.save();
	}

	// error
	public native void register_talk_task_error_handler(String classPath, String methodName, String paraType);
	public void register_talk_task_error_handler()
	{
		register_talk_task_error_handler(this.getClass().getName().replace(".","/"), "talk_task_error_handler", "(I"+generateClassPath(DeviceInfo.class)+")V");
	}
	public void talk_task_error_handler(int codeType, DeviceInfo deviceInfo)
	{
		LogUtil.print("Recv error code :" + ErrorCodeEnum.find(codeType).getInfo() + "\n" + deviceInfo.toString());
		BroadcastUtil.getInstance().sendBoardcast(BroadcastTypeEnum.TALK_TASK_ERROR, codeType+"");
	}

	//	log
	public native int register_log_handler(String classPath, String methodName, String paraType);
	public void register_log_handler()
	{
		register_log_handler(this.getClass().getName().replace(".","/"), "core_log", "(ILjava/lang/String;)V");
	}
	public void core_log(int priority, String msg)
	{
		LogUtil.print(LogUtil.LogPriorityEnum.find(priority), "libIntercomCoreJni.so -> " + msg);
	}

	private String generateClassPath(Class clazz)
	{
		return "L" + clazz.getName().replace(".", "/") + ";";
	}
}
