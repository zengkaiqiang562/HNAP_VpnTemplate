#include <jni.h>
#include <android/log.h>
#include <string>
#define TAG "SecureTools"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, TAG, __VA_ARGS__) // 定义LOGE类型

static const char* clazzPath_SecureTools = "com/template/vpn/SecureTools";

jbyteArray encrypt(JNIEnv *env, jobject instance, jstring origin) {
    LOGE("--> encrypt()");
//    LOGE("--> encrypt() SIGN=%s", SIGN);
//    LOGE("--> encrypt() CKEY=%s", CKEY);
    jclass clazz_SecureTools = env->FindClass(clazzPath_SecureTools);

    jmethodID mid_encrypt = env->GetStaticMethodID(clazz_SecureTools, "encrypt", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)[B");

    jstring sign = env ->NewStringUTF(SIGN);
    jstring key = env ->NewStringUTF(CKEY);

    jbyteArray result = (jbyteArray) env ->CallStaticObjectMethod(clazz_SecureTools, mid_encrypt, sign, origin, key);

    env->DeleteLocalRef(sign);
    env->DeleteLocalRef(key);
    env->DeleteLocalRef(clazz_SecureTools);

    return result;
}

jbyteArray decrypt(JNIEnv *env, jobject instance, jbyteArray jdata) {
    LOGE("--> decrypt()");
//    LOGE("--> encrypt() SKEY=%s", SKEY);
    jclass clazz_CryptoGuard = env->FindClass(clazzPath_SecureTools);

    jmethodID mid_decrypt = env->GetStaticMethodID(clazz_CryptoGuard, "decrypt", "([BLjava/lang/String;)[B");

    jstring key = env ->NewStringUTF(SKEY);

    jbyteArray result = (jbyteArray) env ->CallStaticObjectMethod(clazz_CryptoGuard, mid_decrypt, jdata, key);

    env->DeleteLocalRef(key);
    env->DeleteLocalRef(clazz_CryptoGuard);

    return result;
}

jstring getCert(JNIEnv *env, jobject instance) {
    LOGE("--> getCert()");
    jclass cls_Securer = env->FindClass(clazzPath_SecureTools);

    jmethodID mid_decrypt = env->GetStaticMethodID(cls_Securer, "decrypt", "(Ljava/lang/String;)Ljava/lang/String;");

    jstring enCert = env ->NewStringUTF(OVPN_CERT);
    jstring deCert = (jstring) env ->CallStaticObjectMethod(cls_Securer, mid_decrypt, enCert);
    env->DeleteLocalRef(enCert);
    env->DeleteLocalRef(cls_Securer);
    return deCert;
}

jstring getConfig(JNIEnv *env, jobject instance) {
    LOGE("--> getConfig()");
    jclass cls_Securer = env->FindClass(clazzPath_SecureTools);

    jmethodID mid_decrypt = env->GetStaticMethodID(cls_Securer, "decrypt", "(Ljava/lang/String;)Ljava/lang/String;");

    jstring enProfile = env ->NewStringUTF(CONFIG_CACHE);
    jstring deProfile = (jstring) env ->CallStaticObjectMethod(cls_Securer, mid_decrypt, enProfile);
    env->DeleteLocalRef(enProfile);
    env->DeleteLocalRef(cls_Securer);
    return deProfile;
}

static const JNINativeMethod methods[] = {
        {"nEncrypt",   "(Ljava/lang/String;)[B", (jbyteArray *) encrypt},
        {"nDecrypt", "([B)[B", (jbyteArray *) decrypt},
        {"nGetCert", "()Ljava/lang/String;", (jbyteArray *) getCert},
        {"nGetConfig", "()Ljava/lang/String;", (jbyteArray *) getConfig},
};

int register_SecureTools(JNIEnv *env) {
    jclass jcls = env->FindClass(clazzPath_SecureTools);
    return env->RegisterNatives(jcls, methods, sizeof(methods) / sizeof(JNINativeMethod));
}

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    LOGE("--> JNI_OnLoad()");
    JNIEnv *env = nullptr;
    jint result = -1;

    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        LOGE("--> JNI_OnLoad() GetEnv failed");
        goto error;
    }

    if (register_SecureTools(env) < 0) {
        LOGE("--> JNI_OnLoad() register_SecureTools failed !!!");
        goto error;
    }

    /* success -- return valid version number */
    result = JNI_VERSION_1_4;

    error:
    return result;
}