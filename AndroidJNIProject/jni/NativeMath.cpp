#include <jni.h>
#include <stdio.h>
#include "com_example_androidjniproject_MainActivity.h"

JNIEXPORT jstring JNICALL Java_org_magiclen_androidjni_MainActivity_helloString(
		JNIEnv* env, jobject obj, jstring str){
	const char* toWhat = env->GetStringUTFChars(str, JNI_FALSE);
	char hello[80];
	sprintf(hello,"Hello, %s!", toWhat);
	return env->NewStringUTF(hello);
} 
