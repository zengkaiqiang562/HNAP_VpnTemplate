package com.template.vpn;

import android.util.Log;

import com.template.config.LibConfig;

public class Glog {
    private static final boolean DEBUG = LibConfig.ENABLE_LOG;

    public static void d(String tag, String message) {
        if (DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (DEBUG) {
            Log.e(tag, message);
        }
    }
}
