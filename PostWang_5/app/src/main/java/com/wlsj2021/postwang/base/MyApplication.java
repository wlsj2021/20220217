package com.wlsj2021.postwang.base;

import android.annotation.SuppressLint;
import android.content.Context;

import org.litepal.LitePal;

public class MyApplication extends android.app.Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        LitePal.initialize(this);

    }

    public static Context getContext() {
        return mContext;
    }
}