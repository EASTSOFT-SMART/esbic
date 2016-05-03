#ifndef _INTERCOM_H_
#define _INTERCOM_H_

#ifdef __cplusplus
extern "C" {
#endif

#include <arpa/inet.h>

#include "core_log.h"

typedef enum callback_handler_type_t
{
    CALLBACK_ALARM_IN = 100,
    CALLBACK_IMP_TEXT_PUSH = 200,
    CALLBACK_IMP_AD_PUSH,
    /***********************    talk    ***********************************************/
    CALLBACK_INACTIVE_CALL = 300,
    CALLBACK_VIDEO_URL,
    CALLBACK_PLAY_VIDEO_FRAME,
    CALLBACK_AUDIO_DATA,
    CALLBACK_INACTIVE_CALL_HANG_UP,
    CALLBACK_INACTIVE_TALK_HANG_UP,
    CALLBACK_TALK_CONFIM,
    CALLBACK_CALL_RESPONSE_BUSY,
    CALLBACK_UNLOCK_CONFIRM,
    CALLBACK_CALL_ANSWER_FOUND,
    CALLBACK_TALK_ANSWER,
    CALLBACK_TALK_TASK_ERROR,
    /***********************    monitor    ***********************************************/
    CALLBACK_MONITOR_CONFIRM,
    CALLBACK_INACTIVE_HANG_UP_MONITOR,
    CALLBACK_MONITOR_RESPONSE_BUSY,
    CALLBACK_REQ_MONITOR_TIMEOUT,
    /***********************    fatal error    *******************************************/
    CALLBACK_FATAL_ERROR = 1000,
    /***********************    log    ***************************************************/
    CALLBACK_CORE_LOG = 1100,
}callback_handler_type_t;

typedef struct device_info_s device_info_t;
typedef struct imp_ad_push_s imp_ad_push_t;

/********************************************************************************
 * intercom core
 *******************************************************************************/
int init_intercom_core(device_info_t *device);

int destory_intercom_core(void);

/*
 * 修改设备信息
 */
int modefy_device_info(device_info_t *device);

/********************************************************************************
 * talk task
 *******************************************************************************/
enum device_type_e
{
    DT_Room_Machine = 0,
    DT_Unit_Door_Machine,
    DT_Wall_Machine,
    DT_Secondary_Confirmation_Machine,
    DT_Center_Machine,
};

struct device_info_s
{/*结构体成员的排列顺序和网络报文相关，因此不可随便颠倒顺序*/
    int building_no;
    char unit_no;
    char layer_no;
    char room_no;
    unsigned int dev_no;
    unsigned int device_type;
};

enum talk_task_error_code_e
{//按照袁堂亮的要求，呼叫用户超时和接听超时都使用talk_task_error回调函数
    TTE_CALL_USER_TIMEOUT = 1,      //包括呼叫用户，呼叫中心管理机
    TTE_TALK_ANSWER_TIMEOUT,
};

/*
 * 初始化对讲通信库
 * return: 0 - 成功；-1 - 失败
 */
int init_talk_task(void);

/*
 * 收到呼叫消息
 * 函数原型：void inactive_call_handler(device_info_t *device_info)
 */
void register_inactive_call_handler(void (*inactive_call_handler)(device_info_t *));

/*
 * 收到设备找到消息（call_answer_found）
 * 回调函数原型：void call_answer_found_handler(device_info_t *device_info)
 */
void register_call_answer_found_handler(void (*call_answer_found_handler)(device_info_t *));

/*
 * video
 */
void push_video_frame(int len, char *buf);

/*
 * 注册获取视频url的方法
 * 函数原型：void video_url_handler(char *url)
 */
void register_video_url_handler(void (*url_handler)(char *));

/*
 * 注册视频数据处理函数
 * 函数原型：void play_video_frame_handler(char *data, int len)
 */
void register_play_video_frame_handler(void (*play_video_handler)(char *, int));

/*
 * push audio data
 */
void push_audio_data(int len, char *data);

/*
 * 注册音频处理函数
 * 函数原型：audio_data_handler(int len, char *data)
 */
void register_audio_data_handler(void (*audio_handler)(int ,char *));

/*
 * 呼叫过程中对方挂断
 * 函数原型void inactive_call_hang_up_handler(void)
 */
void register_inactive_call_hang_up_handler(void (*inactive_call_hang_up_handler)(void));

/*
 * ui应答呼叫请求
 */
int ui_talk_answer(void);

/*
 * 收到对讲应答
 * 回调函数原型：void talk_answer_handler(void)
 */
void register_talk_answer_handler(void (*talk_answer_handler)(void));

/*
 * 对讲服务收到对方确认对讲的报文后，通过回调通知ui开始对讲
 * 函数原型：talk_confim_handler(void)
 */
void register_talk_confim_handler(void (*talk_confim_handler)(void));

/*
 * ui挂断（未接听)
 */
int call_hang_up(void);

/*
 * ui挂断（已接听）
 */
int talk_hang_up(void);

/*
 * 对讲过程中如果对方主动挂断，则对讲服务通过回调通知ui此消息
 * 函数原型：void otherside_talk_hang_up_handler(void)
 */
void register_inactive_talk_hang_up_handler(void (*inactive_talk_hang_up_handler)(void));

/*
 * 主动呼叫用户
 */
int active_call_user(device_info_t *device);

/*
 * 用户回复忙
 * 回调函数原型：void call_busy_handler(void)
 */
void register_call_busy_handler(void (*call_busy_handler)(void));

/*
 * 对讲任务出错（替代其他的timeout回调）
 * 回调函数原型：void talk_task_error_handler(int error_code, device_info_t *peer)
 */
void register_talk_task_error_handler(void (*error_handler)(int, device_info_t *));

/*
 * 呼叫中心管理机
 */
int call_center_manager(struct in_addr *addr);

/*
 * 开锁
 */
int unlock_door(void);

/*
 * 开锁确认
 * 回调函数原型：void unlock_door_confirm_handler(int result)
 * 回调函数说明：参数result:1-开锁成功; 0-超时
 */
void register_unlock_door_confirm_handler(void (*unlock_confirm_hanler)(int));

/***********************    monitor    ***********************************************/
/*
 * 监视门口机
 */
int ui_req_monitor(device_info_t *device);

/*
 * 监视确认
 * 回调函数原型：void monitor_confirm_handler(device_info_t *device)
 */
void register_monitor_confirm_handler(void (*monitor_confirm_handler)(device_info_t *));

/*
 * 主动挂断监视
 */
int active_hang_up_monitor(device_info_t *device);

/*
 * 被动挂断监视
 * 回调函数原型：void inactive_hang_up_monitor_handler(device_info_t *device)
 */
void register_inactive_hang_up_monitor_handler(void (*hang_up_handler)(device_info_t *));

/*
 * 请求监视门口机时门口机忙
 * 回调函数原型：void monitor_response_busy_handler(device_info_t *device)
 */
void register_monitor_response_busy_handler(void (*monitor_busy_handler)(device_info_t *));

/*
 * 请求监视门口机超时
 * 回调函数原型：void req_monitor_timeout_handler(device_info_t *device)
 */
void register_req_monitor_timeout_handler(void (*req_monitor_timeout_handler)(device_info_t *));

/********************************************************************************
 * alarm task
 *******************************************************************************/
#define MAX_DEFENSE_AREA_NUM    8
typedef struct defences_switch_s
{
    int onoff;  //总开关
    char area[MAX_DEFENSE_AREA_NUM];    //1:防区开，0:防区关
}defences_switch_t;

int init_alarm_in(defences_switch_t *defence);

int modify_alarm_in_defences_area(defences_switch_t *defence);

int destory_alarm_in(void);

/*
 * 注册安防报警处理函数
 * 回调函数原型：void alarm_in_handler(int channel)
 */
int register_alarm_handler(void (*alarm_handler)(int));


/********************************************************************************
 * IMP task
 *******************************************************************************/
#define IMP_AD_PUSH_URL_MAX_LEN 1024

struct imp_ad_push_s
{
    int enable;
    int play_time;
    char url[IMP_AD_PUSH_URL_MAX_LEN];
};

enum imp_event_type_enum
{
    IMP_ET_CALL_RECORD = 1,
    IMP_ET_ALARM_IN,
    IMP_ET_ELEVATOR_GUEST,
    IMP_ET_ELEVATOR_CALL,
};

enum imp_result_enum
{
    IMP_RESULT_TIMEOUT = -1,
    IMP_RESULT_OTHRE_ERROR = -2,
    IMP_RESULT_SUCCESS = 200,
    IMP_RESULT_FAILED = 480,
};

/*
 * 初始化IMP任务
 * 创建线程接收IMP报文
 */
int init_imp_task(struct in_addr *imp_addr);

/*
 * 销毁IMP任务
 */
int destory_imp_task(void);

/*
 * 修改地址信息
 */
int modify_imp_addr(struct in_addr *imp_addr);

/*
 * IMP文本推送
 * 函数原型：void imp_text_push_handler(char *text)
 */
int register_text_push_handler(void (*text_push_handler)(char *));

/*
 * IMP广告推送
 * 回调函数原型：void imp_ad_push_handler(imp_ad_push_t *ad)
 */
int register_ad_push_handler(void (*ad_push_handler)(imp_ad_push_t *));

/*
 * IMP回复消息
 * 回调函数原型：void imp_response_handler(int event_type, int result)
 */
int register_imp_response_handler(void (*response_handler)(int, int));

/*
 * 收到报警信息（中心管理机）
 * 回调函数原型：void user_alarm_trigger_handler(device_info_t *device, int type, int data)
 */
int register_user_alarm_trigger_handler(void (*alarm_handler)(device_info_t *,int, int));

/*
 * 发送对讲记录
 */
int send_imp_call_record(device_info_t *peer_info, int is_answer, int is_unlock);

/*
 * 呼梯
 */
int send_imp_call_elevator(int floor, int room);

/*
 * 发送报警记录
 * input:type:0~n 防区类型
 *       data:0撤警，1报警
 */
int send_imp_alarm_in_record(int type, int data);

/*
 * 发送报警信息到中心管理机
 * input:addr:中心管理机地址
 *       peer_info:中心管理机设备信息
 *       type:0~n 防区类型
 *       data:0撤警，1报警
 */
int send_center_manager_alarm_record(struct in_addr *addr, device_info_t *peer_info, int type, int data);

/********************************************************************************
 * fatal error
 *******************************************************************************/
enum
{
    FE_NET_JOIN_GROUP = 1,

    FE_INIT_IMP_FAILED = 101,

    FE_INIT_ALARM_TASK_FAILED = 201,
    FE_OPEN_ALARM_DRIVER_ERROR = 202,

    FE_OTHER_ERROR,
};
/*
 * 致命错误
 * 回调函数原型：void fatal_error_handler(int error_code)
 */
int register_fatal_error_handler(void (*error_handler)(int));

#ifdef __cplusplus
}
#endif

#endif
