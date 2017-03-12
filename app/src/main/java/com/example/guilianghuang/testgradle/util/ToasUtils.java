package com.example.guilianghuang.testgradle.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by guilianghuang on 2017/3/12.
 */

public class ToasUtils {

    public static void toast(Context context, String str) {

        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

}
