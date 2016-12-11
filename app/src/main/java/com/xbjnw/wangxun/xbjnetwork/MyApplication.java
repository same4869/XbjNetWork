package com.xbjnw.wangxun.xbjnetwork;

import android.app.Application;

import com.xbjnw.wangxun.xbjnetwork.webbase.WenbaWebLoader;

/**
 * Created by wangxun on 16/12/10.
 */

public class MyApplication extends Application{
    protected static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        WenbaWebLoader.initialize(this, true);
    }

    public static MyApplication getInstance() {
        return instance;
    }
}
