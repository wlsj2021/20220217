package com.wlsj2021.postwang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.wlsj2021.postwang.R;
import com.wlsj2021.postwang.base.Event;
import com.wlsj2021.postwang.base.Utils;

import org.greenrobot.eventbus.EventBus;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        EventBus.getDefault().register(this);

    }

    public void setColor(View view) {
        Utils.setColor(this, Color.RED);

        Event splashEvent = new Event();
        splashEvent.target= Event.TARGET_SPLASH;
        splashEvent.type= Event.TYPE_REFRESH_COLOR;
        EventBus.getDefault().post(splashEvent);

        Event mainEvent = new Event();
        splashEvent.target= Event.TARGET_MAIN;
        splashEvent.type= Event.TYPE_REFRESH_COLOR;
        EventBus.getDefault().post(mainEvent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}