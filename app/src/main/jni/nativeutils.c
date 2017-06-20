#include <jni.h>

JNIEXPORT void JNICALL
Java_com_example_demo_testgradle_util_NativeUtils_callJava(JNIEnv *env, jclass type) {

    // TODO


    //通过反射获取到类com.example.demo.testgradle.activity.MainActivity
    jclass  jclazz = (*env)->FindClass(env,"com/example/demo/testgradle/activity/MainActivity");
    //通过反射获取到方法
    /**
     * 第一个参数：虚拟机指针
     * 第二个参数：方法所在class
     * 第三个参数：方法名
     * 第四个参数：方法签名（javap -s class全类名（字节码的全类名））
     */
    jmethodID jmethod = (*env)->GetMethodID(env,jclazz,"printLog","()V");

    /**
     * 创建类实例
     */
    jobject  jobject1 = (*env)->AllocObject(env,jclazz);

    //调用方法
    /**
     * 第一个参数:java虚拟机
     * 第二个参数：调用此方法的实例
     * 第三个参数：jmethodID
     */
    (*env)->CallVoidMethod(env,jobject1,jmethod);

}