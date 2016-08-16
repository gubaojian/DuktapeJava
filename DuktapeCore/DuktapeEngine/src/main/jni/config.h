/*
 * utils.h
 *
 *  Created on: 2015年6月26日
 *      Author: jianbai.gbj
 */

#ifndef CONFIG_H_
#define CONFIG_H_
#include <android/log.h>

#define DUK_OPT_AUGMENT_ERRORS


//#define DEBUG

#define LOGV(TAG,...) __android_log_print(ANDROID_LOG_VERBOSE, TAG,__VA_ARGS__)
#define LOGD(TAG,...) __android_log_print(ANDROID_LOG_DEBUG, TAG,__VA_ARGS__)
#define LOGI(TAG,...) __android_log_print(ANDROID_LOG_INFO, TAG,__VA_ARGS__)
#define LOGW(TAG,...) __android_log_print(ANDROID_LOG_WARN, TAG,__VA_ARGS__)
#define LOGE(TAG,...) __android_log_print(ANDROID_LOG_ERROR, TAG,__VA_ARGS__)

#ifdef DEBUG
  #define DEBUG_LOG(TAG,...)  LOGE(TAG, __VA_ARGS__)
#else
  #define DEBUG_LOG(TAG,...)
#endif

#endif /* UTILS_H_ */

