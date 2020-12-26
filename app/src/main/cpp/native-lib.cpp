
#include <string>
#include <jni.h>
#include <vector>
#include <ctime>
#include <android/log.h>



static JNIEnv* env;
static jclass java_util_ArrayList;
static jclass java_lang_Integer;
static jmethodID  java_lang_Integer_intValue;
static jmethodID java_util_ArrayList_size;
static jmethodID java_util_ArrayList_get;



void init(JNIEnv* envIn);
std::vector<int> arrayListToVector(jobject arrayList);


extern "C"
jstring
Java_com_romankaranchuk_musicplayer_utils_JniUtils_stringFromJNI(
        JNIEnv* env,
        jobject){
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
jlong
Java_com_romankaranchuk_musicplayer_utils_JniUtils_sum(
        JNIEnv* env,
        jobject /*this*/,
        jobject arrayList){
    init(env);
    std::vector<int> vector = arrayListToVector(arrayList);

    std::clock_t start_time = std::clock();
    long long sum = 0;