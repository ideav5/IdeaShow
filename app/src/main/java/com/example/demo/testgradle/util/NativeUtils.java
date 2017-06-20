package com.example.demo.testgradle.util;

/**
 * Created  on 2017/6/20.
 */

public class NativeUtils {
    static {
        System.loadLibrary("nativeutils");
    }

    public static native void callJava();
}
