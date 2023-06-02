package com.template.vpn;

import android.app.Application;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.LogLevel;
import com.template.config.LibConfig;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class SdkHelper {

    private static final String TAG = "SdkHelper";

    public static void setFirebase(Application application) {
        // Analytics
        FirebaseAnalytics.getInstance(application).setAnalyticsCollectionEnabled(true);
        // Crashlytics: release 传 true，debug 传 false
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(!LibConfig.VPN_DEBUG);
    }

    public static void setFacebook(Application application) {
        String id = LibConfig.FACEBOOK_ID;
        String token = LibConfig.FACEBOOK_TOKEN;
        Glog.e(TAG, "--> setFacebook()  id=" + id + "  token=" + token);
        FacebookSdk.setApplicationId(id); // app id（跟 AndroidManifest.xml 中配置的一样）
        FacebookSdk.setClientToken(token); // token
        FacebookSdk.sdkInitialize(application);
        AppEventsLogger.activateApp(application);
        FacebookSdk.setAutoLogAppEventsEnabled(true);
    }

    public static void setAdjust(Application application) {
        String token = LibConfig.ADJUST_TOKEN;
        Glog.e(TAG, "--> setAdjust()  token=" + token);
        /*
        Debug 时 environment 设置为 AdjustConfig.ENVIRONMENT_SANDBOX 。
        Release 时 environment 设置为 AdjustConfig.ENVIRONMENT_PRODUCTION。
         */
        String env = LibConfig.VPN_DEBUG ? AdjustConfig.ENVIRONMENT_SANDBOX : AdjustConfig.ENVIRONMENT_PRODUCTION;
        AdjustConfig config = new AdjustConfig(application, token, env);
        config.setLogLevel(LibConfig.VPN_DEBUG ? LogLevel.WARN : LogLevel.SUPRESS);
        Adjust.onCreate(config);
    }
}
