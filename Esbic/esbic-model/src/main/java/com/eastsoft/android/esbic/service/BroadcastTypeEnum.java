package com.eastsoft.android.esbic.service;

/**
 * Created by yangrm on 2016/2/19 13:22.
 */
public enum BroadcastTypeEnum
{
    CALL_REQUEST						(1, "CALL_REQUEST"),          //  有人呼叫啦
    PLAY_VIDEO          				(2, "PLAY_VIDEO"),            //  有视频来啦
    HANG_UP     						(3, "HANG_UP"),               //  对方挂断啦
    CALL_CONFIRM                     	(4, "CALL_CONFIRM"),          //  找到被呼叫设备
    OPEN_LOCK_CONFIRM					(5, "OPEN_LOCK_CONFIRM"),     //  开锁确认
    MONITOR_CONFIRM 					(6, "MONITOR_CONFIRM"),       //  监视确认
    DEVICE_BUSY     					(7, "DEVICE_BUSY"),           //  设备忙
    MONITOR_HANG_UP 					(8, "MONITOR_HANG_UP"),       //  被监视门口机主动挂断
    CALL_ANSWER_CONFIRM					(9, "CALL_ANSWER_CONFIRM"),   //  主动呼叫，对方应答
    RECEIVE_MESSAGE 					(10, "RECEIVE_MESSAGE"),      //  收到消息
    RECEIVE_AD      					(11, "RECEIVE_AD"),           //  收到广告
    RECEIVE_ALARM   					(12, "RECEIVE_ALARM"),        //  收到报警
    UNKNOWN      						(-1, "UNKNOWN"),              //  出事啦
    ;

    int type;
    String name;

    private BroadcastTypeEnum(int type, String name)
    {
        this.type = type;
        this.name = name;
    }

    public int getType() {

        return type;
    }

    public String getName() {
        return name;
    }

    public static BroadcastTypeEnum find(int type)

    {
        for(BroadcastTypeEnum i : BroadcastTypeEnum.values())
        {
            if(i.type == type)
            {
                return i;
            }
        }
        return UNKNOWN;
    }
}
