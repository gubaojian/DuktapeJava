

LOCAL_PATH := $(call my-dir)

APP_ABI := all

include $(CLEAR_VARS)

LOCAL_MODULE    := DuktapeEngine

LOCAL_C_INCLUDES_FILES :=config.h
LOCAL_C_INCLUDES_FILES :=duk_config.h
LOCAL_C_INCLUDES_FILES +=DuktapeEngine.h
LOCAL_C_INCLUDES_FILES +=refs.h
LOCAL_C_INCLUDES_FILES +=duktape.h


LOCAL_SRC_FILES :=duktape.c
LOCAL_SRC_FILES +=refs.c
LOCAL_SRC_FILES +=DuktapeEngine.c



# Optional compiler flags.
LOCAL_LDLIBS     = -lz -lm
LOCAL_LDLIBS    := -llog
LOCAL_CFLAGS := -g -std=c99

#debug
#APP_OPTIM := debug
APP_OPTIM := release

include $(BUILD_SHARED_LIBRARY)
