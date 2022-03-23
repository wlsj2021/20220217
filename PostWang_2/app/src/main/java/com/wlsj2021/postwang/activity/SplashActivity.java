package com.wlsj2021.postwang.activity;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


import com.wlsj2021.postwang.MainActivity;
import com.wlsj2021.postwang.R;
import com.wlsj2021.postwang.base.BaseActivity;
import com.wlsj2021.postwang.base.BasePresenter;
import com.wlsj2021.postwang.base.Event;
import com.wlsj2021.postwang.base.MyApplication;
import com.wlsj2021.postwang.base.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


/**
 * 启动动画
 */
public class SplashActivity extends BaseActivity {

    private ViewGroup mSplashContainer;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mSplashContainer = findViewById(R.id.splash_container);

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        EventBus.getDefault().register(this);

        intoMain();

//        mHandler.sendEmptyMessage(0);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);


    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 初始化进场动画
     */
    private void intoMain() {
//        mSplashContainer.setBackgroundColor(Utils.getColor(mContext));
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, 1000);
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_SPLASH) {
            if (event.type == Event.TYPE_REFRESH_COLOR) {
                mSplashContainer.setBackgroundColor(Utils.getColor(MyApplication.getContext()));
            }
        }
    }

    @Override
    public void callBack(Message msg) {


        switch (msg.what) {
            case 0:
// Intent intent = new Intent(SplashActivity.this, RegisterActivity.class);
                break;
            case 1:
// do
                break;
            case 2:
// do
                break;
        }


    }
}
