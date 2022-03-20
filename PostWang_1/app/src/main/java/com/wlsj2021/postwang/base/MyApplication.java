package com.wlsj2021.postwang.base;

import android.annotation.SuppressLint;
import android.content.Context;

public class MyApplication extends android.app.Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}