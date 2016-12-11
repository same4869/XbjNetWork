package com.xbjnw.wangxun.xbjnetwork;

import android.app.ActivityManager;
import android.content.Context;

/**
 * Created by wangxun on 16/12/11.
 */

public class AppInfoUtils {
    /**
     * 获取单个应用内存限制
     *
     * @param context
     * @return
     */
    public static int getAppMemory(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        return activityManager.getMemoryClass();
    }
}
