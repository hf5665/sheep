LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := NativeMath
LOCAL_SRC_FILES := NativeMath.cpp

include $(BUILD_SHARED_LIBRARY)
