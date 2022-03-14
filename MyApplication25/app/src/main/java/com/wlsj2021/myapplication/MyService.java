package com.wlsj2021.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }
    class MyBinder extends Binder{

        public MyService getSe(){
            return MyService.this;
        }
        //提供犯法付

    }










}
