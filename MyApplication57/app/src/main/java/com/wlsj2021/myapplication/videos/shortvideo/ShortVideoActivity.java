package com.wlsj2021.myapplication.videos.shortvideo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.wlsj2021.myapplication.R;
import com.wlsj2021.myapplication.videos.VideoPlayRecyclerView;

public class ShortVideoActivity extends Activity {
    private VideoPlayRecyclerView mRvVideo;
    private ShortMainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duan_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.ibBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRvVideo = findViewById(R.id.rvVideo);
        adapter = new ShortMainAdapter(this);
        mRvVideo.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.release();
    }
}