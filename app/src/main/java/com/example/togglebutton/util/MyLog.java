package com.example.togglebutton.util;

import android.util.Log;

/**
 * Created by Biao - Li on 2017/7/6.
 * Welbell
 * 207563927@qq.com
 */
public class MyLog
{
    private static boolean isDebug = true;
    public static void setDebug(boolean debug)
    {
        if(debug)
        {
            isDebug = true;
        }
        else
        {
            isDebug = false;
        }
    }
    public static void e(String info)
    {
        if(isDebug)
        {
            Log.e("TAG",info);
        }
    }
    public static void d(String info)
    {
        if(isDebug)
        {
            Log.d("TAG",info);
        }
    }
}
