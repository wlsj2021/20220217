package com.wlsj2021.myapplication.common;

import android.os.Bundle;

import com.trello.rxlifecycle.components.RxActivity;

public class MyActivity extends RxActivity {

    public MyActivity(MyActivity context) {

        context = this;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
