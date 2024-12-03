#ifndef MYLIBRARY_H
#define MYLIBRARY_H

#include <jni.h>

JNIEXPORT jstring JNICALL Java_com_example_spectr_Fragmen_getProcessedData(JNIEnv *env, jobject obj, jstring itemTitle, jstring itemText);

#endif // MYLIBRARY_H
