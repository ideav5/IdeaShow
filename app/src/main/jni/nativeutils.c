#include <jni.h>
#include <stdlib.h>

JNIEXPORT void JNICALL
Java_com_example_demo_testgradle_util_NativeUtils_callJava(JNIEnv *env, jclass type) {

    // TODO


    //通过反射获取到类com.example.demo.testgradle.activity.MainActivity
    jclass jclazz = (*env)->FindClass(env, "com/example/demo/testgradle/activity/MainActivity");
    //通过反射获取到方法
    /**
     * 第一个参数：虚拟机指针
     * 第二个参数：方法所在class
     * 第三个参数：方法名
     * 第四个参数：方法签名（javap -s class全类名（字节码的全类名））
     */
    jmethodID jmethod = (*env)->GetMethodID(env, jclazz, "printLog", "()V");

    /**
     * 创建类实例
     */
    jobject jobject1 = (*env)->AllocObject(env, jclazz);

    //调用方法
    /**
     * 第一个参数:java虚拟机
     * 第二个参数：调用此方法的实例
     * 第三个参数：jmethodID
     */
    (*env)->CallVoidMethod(env, jobject1, jmethod);

}

JNIEXPORT jstring JNICALL
Java_com_example_demo_testgradle_util_NativeUtils_getPwd(JNIEnv *env, jclass obj) {

    // TODO  jwFolkcam$


    //public class com.example.demo.testgradle.util.StringTest {
//    public static java.lang.String key;
//    descriptor: Ljava/lang/String;
//    public com.example.demo.testgradle.util.StringTest();
//    descriptor: ()V
//
//    static {};
//    descriptor: ()V
//}

    //因为test不是静态函数，所以传进来的就是调用这个函数的对象
    //否则就传入一个jclass对象表示native()方法所在的类
    //通过反射获取到类com.example.demo.testgradle.activity.MainActivity
    jclass native_clazz = (*env)->FindClass(env, "com/example/demo/testgradle/util/StringTest");
//    jclass native_clazz = (*env)->GetObjectClass(env,obj);


    //得到jfieldID
    jfieldID fieldID_prop = (*env)->GetStaticFieldID(env, native_clazz, "key",
                                                     "Ljava/lang/String;");
//    jfieldID fieldID_num = (*env)->GetFieldID(native_clazz,"number","I");

    //得到jmethodID
//    jmethodID methodID_func=env->GetMethodID(native_clazz,"signTest","(ILjava/util/Date;[I)I");
//    //调用signTest方法
//    env->CallIntMethod(obj,methodID_func,1L,NULL,NULL);
//    jobject sttringTest = (*env)->AllocObject(env, native_clazz);
    jstring key = (*env)->GetStaticObjectField(env, native_clazz, fieldID_prop);
//    return key;
//    return (*env)->NewStringUTF(env, key);
    //得到name属性
//    jobject name = env->GetObjectField(obj,fieldID_name);
//    //得到number属性
//    jint number= env->GetIntField(obj,fieldID_num);
//
//    cout<<number<<endl;//100
//    //修改number    属性的值
//    env->SetIntField(obj,fieldID_num,18880L);
//    number= env->GetIntField(obj,fieldID_num);


    jclass md5Class = (*env)->FindClass(env, "com/example/demo/testgradle/util/Md5Utils");
//    /**
//   * 创建类实例
//   */
//    jobject  jobject1 = (*env)->AllocObject(env,md5Class);
//
//    //得到jmethodID
    jmethodID methodID_func = (*env)->GetStaticMethodID(env, md5Class, "md5",
                                                        "(Ljava/lang/String;)Ljava/lang/String;");
//    if (methodID_func) {
//        jstring result = (*env)->CallStaticObjectMethod(env, md5Class, methodID_func, key);
//        return result;
//    }
//



//    char *cstr = funToJstring(env, key);



    char *cstr = NULL;
    jclass clsstring = (*env)->FindClass(env, "java/lang/String");

    jstring strencode = (*env)->NewStringUTF(env, "GB2312");

    jmethodID mid =    (*env)->GetMethodID(env, clsstring, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray barr =  (jbyteArray) (*env)->CallObjectMethod(env, key, mid, strencode);

    jsize alen = (*env)->GetArrayLength(env, barr);

    jbyte *ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);

    if (alen > 0) {

        cstr = (char *) malloc(alen + 1); //"\0"

        memcpy(cstr, ba, alen);

        cstr[alen] = 0;

    }

    (*env)->ReleaseByteArrayElements(env, barr, ba, 0); //


    char *hellostr = "World";

    char *c = (char *) malloc(strlen(hellostr) + strlen(cstr) + 1); //局部变量，用malloc申请内存
    if (c == NULL) exit (1);
    char *tempc = c; //把首地址存下来
    while (*hellostr != '\0') {
        *c++ = *hellostr++;
    }
    while ((*c++ = *cstr++) != '\0') {
        ;
    }



//    strcat( hellostr,cstr); //拼接两个字符串

    jstring jstring1 = (*env)->NewStringUTF(env, tempc);
    jstring result = (*env)->CallStaticObjectMethod(env, md5Class, methodID_func, jstring1);
    return result;
//    return key;
    /*
   Compiled from "Md5Utils.java"
public class com.example.demo.testgradle.util.Md5Utils {
  public com.example.demo.testgradle.util.Md5Utils();
    descriptor: ()V

  public static java.lang.String md5(java.lang.String);
    descriptor: (Ljava/lang/String;)Ljava/lang/String;}
    */

//    return (*env)->NewStringUTF(env, result);
}


/**

* 工具方法

* 作用: 把java中的string 转化成一个c语言中的char数组

* 接受的参数 envjni环境的指针

* jstr 代表的是要被转化的java的string 字符串

* 返回值 : 一个c语言中的char数组的首地址 (char 字符串)

*/

//char *funToJstring(JNIEnv *env, jstring jstr) {
//    char *rtn = NULL;
//    jclass clsstring = (*env)->FindClass(env, "java/lang/String");
//
//    jstring strencode = (*env)->NewStringUTF(env, "GB2312");
//
//    jmethodID mid =    (*env)->GetMethodID(env, clsstring, "getBytes", "(Ljava/lang/String;)[B");
//
//
//    jbyteArray barr =  (jbyteArray) (*env)->CallObjectMethod(env, jstr, mid, strencode);
//
//    jsize alen = (*env)->GetArrayLength(env, barr);
//
//    jbyte *ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
//
//    if (alen > 0) {
//
//        rtn = (char *) malloc(alen + 1); //"\0"
//
//        memcpy(rtn, ba, alen);
//
//        rtn[alen] = 0;
//
//    }
//
//    (*env)->ReleaseByteArrayElements(env, barr, ba, 0); //
//
//    return rtn;
//
//}
