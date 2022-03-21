package com.wlsj2021.postwang.base;

import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class BaseHandler  extends Handler {

    final WeakReference<BaseActivity> activity;
    final WeakReference<BaseHandlerCallBack> callBack;

    public BaseHandler(BaseActivity appCompatActivity, BaseHandlerCallBack baseHandlerCallBack) {
        activity = new WeakReference<BaseActivity>(appCompatActivity);
        callBack = new WeakReference<BaseHandlerCallBack>(baseHandlerCallBack);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        BaseActivity appCompatActivity = activity.get();
        BaseHandlerCallBack baseHandlerCallBack = callBack.get();

        if (appCompatActivity != null && baseHandlerCallBack != null) {

            baseHandlerCallBack.callBack(msg);


        }
    }

    public interface BaseHandlerCallBack {
        void callBack(Message msg);
    }
}


