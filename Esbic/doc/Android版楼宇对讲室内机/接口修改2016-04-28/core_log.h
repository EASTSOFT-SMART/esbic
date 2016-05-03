#ifndef _CORE_LOG_H_
#define _CORE_LOG_H_

enum core_log_priority
{
    CORE_LOG_PRI_ERROR = 0,
    CORE_LOG_PRI_WARN,
    CORE_LOG_PRI_NOTICE,
    CORE_LOG_PRI_INFO,
    CORE_LOG_PRI_DEBUG,
    CORE_LOG_PRI_TRACE,
};

extern void intercom_core_log_msg(int priority, const char *fmt, ...);
extern void intercom_core_log_array(const unsigned char *data, int len, const char *fmt);
//extern void _core_log_array_(const unsigned char *cmd, int cmdlen, const char *fmt);
//extern void _core_log_trace_(const char *file, int line, const char *func, const char *fmt, ...);

#define core_log_error(fmt , args...)    intercom_core_log_msg(CORE_LOG_PRI_ERROR, fmt,##args)
#define core_log_warn(fmt, args...)      intercom_core_log_msg(CORE_LOG_PRI_WARN, fmt , ##args)
#define core_log_notice(fmt , args...)   intercom_core_log_msg(CORE_LOG_PRI_NOTICE, fmt , ##args)
#define core_log_info(fmt, args...)      intercom_core_log_msg(CORE_LOG_PRI_INFO, fmt, ##args)
#define core_log_debug(fmt , args...)    intercom_core_log_msg(CORE_LOG_PRI_DEBUG, fmt,##args)
//#define log_trace(fmt, args...)     _core_log_trace_(__FILE__ , __LINE__ , __FUNCTION__ , fmt ,##args)
#define core_log_array(data, len, fmt)   intercom_core_log_array(data, len, fmt)

/*
 * 注册log打印函数
 * 函数原型: core_log(int priority, char *msg)
 */
int register_core_log_handler(void (*log_handler)(int, char *));

#endif
