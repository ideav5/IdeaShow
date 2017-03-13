package com.example.guilianghuang.testgradle.util;

import android.util.Log;

import com.orhanobut.logger.Logger;

/**
 * Created by idea on 2017/3/13.
 */

public class LoggerUtils {


    public static void debug(String string) {
        Log.d("Log", string);
    }

    public static void logger(String string) {
        Logger.d(string);
    }


}
