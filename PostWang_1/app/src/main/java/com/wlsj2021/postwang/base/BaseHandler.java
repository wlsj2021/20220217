package com.wlsj2021.postwang.base;

import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class BaseHandler  extends Handler {

    final WeakReference<AppCompatActivity> act;
    final WeakReference<BaseHandlerCallBack> callBack;

    public BaseHandler(AppCompatActivity c, BaseHandlerCallBack b) {
        act = new WeakReference<>(c);
        callBack = new WeakReference<>(b);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        AppCompatActivity c = act.get();
        BaseHandlerCallBack b = callBack.get();
        if (c != null && b != null) {
            b.callBack(msg);
        }
    }

    public interface BaseHandlerCallBack {
        void callBack(Message msg);
    }
}


