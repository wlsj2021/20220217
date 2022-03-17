package com.wlsj2021.postwang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.wlsj2021.postwang.R;

import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

public class LiveActivity extends AppCompatActivity {
private DanmakuView mDanmakuView;
private GSYVideoPlayer mVideoPlayer;

    private DanmakuContext mDanmakuContext;
    //3 String、XML、JSON(包含同一时间多个弹幕) 解析的不同
    private BaseDanmakuParser mParser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };
    //4 显示隐藏
    private boolean showHide;
    //5 GSY播放
//    private StandardGSYVideoPlayer mVideoPlayer;
//    private LinearLayout mLinearLayout;
//    private EditText mEditText;
//    private Button mSendDM;


    @Override
    protected void onResume() {
        super.onResume();
        //在获取焦点的时候初始化
        if (mDanmakuView != null && mDanmakuView.isPrepared()) {
            mDanmakuView.resume();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);

        mDanmakuView = findViewById(R.id.danmu_view);
        mVideoPlayer = findViewById(R.id.gsy_player);


        //gsyPlayer的启动 获取焦点的时候
        mVideoPlayer.onVideoResume();
        //gsyPlayer的设置  SCREEN_TYPE_FULL全屏的播放
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL);
        //设置 URL 是否缓存 和 标题
        mVideoPlayer.setUp("http://godmusic.bs2dl.yy.com/dHZmNDdjZjBlOTNhODU0OGQ0YjFmMThmMGIzMzAzY2EyNzY2NTY5MzczMg==", false, "有弹幕吗");
        //开始播放
        mVideoPlayer.startPlayLogic();
        //开启弹幕绘制缓存
        mDanmakuView.enableDanmakuDrawingCache(true);
        //开启handler Callback
        mDanmakuView.setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                //是否要显示
                showHide = true;
                //开始显示
                mDanmakuView.start();
                createDanmu();

            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        mDanmakuContext = DanmakuContext.create();
        //播放需要
        mDanmakuView.prepare(mParser, mDanmakuContext);

//沉浸式
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int i) {
                if (i == View.SYSTEM_UI_FLAG_VISIBLE) {
                    onWindowFocusChanged(true);
                }
            }
        });

    }

    //沉浸式
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 21) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE


            );

        }

    }

    //添加一条弹幕
    private void sendDanmu(String msg, boolean tool) {
        //创建 通过工厂mDanmakuFactory
        //指定弹幕显示的形式 BaseDanmaku.TYPE_SCROLL_LR
        BaseDanmaku danmaku =
                mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        //添加弹幕的文字
        danmaku.text = msg;
        //文字的属性
        //间距
        danmaku.padding = 10;
        //文字的颜色
        danmaku.textColor = Color.RED;

        //文字大小 dp sp === px
        danmaku.textSize = spToPx(20);
        //自己发一定显示
        danmaku.priority = 0;
        //需要某些属性的时候 tool
        if (tool) {
            //Shadow的颜色
            danmaku.textShadowColor = Color.GRAY;
        }

        mDanmakuView.addDanmaku(danmaku);

    }


    //SP 转 PX ----  PX转DP
    public int spToPx(float sp) {
        //找到change
        final float change = getResources().getDisplayMetrics().scaledDensity;
        //返回进行变换
        return (int) (sp * change + 0.5f);
    }

    //数据 XML 或者 JSON
    private void createDanmu() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (showHide) {
                    int num = new Random().nextInt(500);
                    String s = "" + num + num + "山楂树，不是小孩就不能吃糖了";
                    sendDanmu(s, false);

                    try {
                        Thread.sleep(num);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }


            }
        }).start();
    }
}