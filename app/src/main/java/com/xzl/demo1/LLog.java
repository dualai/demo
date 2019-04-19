package com.xzl.demo1;
import android.util.Log;

import com.orhanobut.logger.Logger;


public class LLog {
    private final static String TAG = "VideoDebug";
    public static void d(boolean isTrace, String args) {
        if (!isTrace) {
            Log.d(TAG, args);
        } else {
            Logger.t(TAG).d(args);
        }
    }

    public static void e(boolean isTrace, String args) {
        if (!isTrace) {
            Log.e(TAG, args);
        } else {
            Logger.t(TAG).e(args);
        }
    }

    public static void json(String args) {
        Logger.t(TAG).json(args);
    }

    public static void d(String args) {
        Log.d(TAG, args);
    }

    public static void e(String args) {
        Log.e(TAG, args);
    }


}