package com.wlsj2021.myapplication.common;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);


        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.GCJ02);
    }
//低内存
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
//over
    @Override
    public void onTerminate() {
        super.onTerminate();
    }
//TODO API
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
//设置发生变化
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
