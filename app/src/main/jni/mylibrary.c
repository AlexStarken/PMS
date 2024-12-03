#include <jni.h>
#include <string.h>
#include <stdio.h>

JNIEXPORT jstring JNICALL
Java_com_example_spectr_Fragment_getProcessedData(JNIEnv *env, jobject obj, jstring itemTitle, jstring itemText) {
    const char *title = (*env)->GetStringUTFChars(env, itemTitle, 0);
    const char *text = (*env)->GetStringUTFChars(env, itemText, 0);

    // Создаем строку с обработанными данными
    char result[256];
    snprintf(result, sizeof(result), "Title: %s\n\n\nText: %s", title, text);

    // Освобождаем память
    (*env)->ReleaseStringUTFChars(env, itemTitle, title);
    (*env)->ReleaseStringUTFChars(env, itemText, text);

    return (*env)->NewStringUTF(env, result);
}
