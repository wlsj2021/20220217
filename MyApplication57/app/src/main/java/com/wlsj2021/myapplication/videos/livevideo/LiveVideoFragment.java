package com.wlsj2021.myapplication.videos.livevideo;

import android.os.Bundle;
import android.os.Trace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.wlsj2021.myapplication.R;
import com.wlsj2021.myapplication.videos.VideoPlayRecyclerView;
import com.wlsj2021.myapplication.videos.shortvideo.ShortMainAdapter;


public class LiveVideoFragment extends Fragment {

    private StandardGSYVideoPlayer videoPlayer;

    private View mView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.live_main, container, false);
        initView();
        return mView;
    }


    private void initView() {
        videoPlayer = mView.findViewById(R.id.detail_player);

        videoPlayer.onVideoResume();

        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL);

        videoPlayer.setUp("https://ws-adaptive.pull.yximgs.com/gifshow/i0e03zw8TzY.flv?wsTime=61f7ecd4&wsSecret=29ed3f1ab6a675e51e5858491f19d882&stat=oG1oivCfnqQ+qIcPj" +
                "YKWFI9YM5EDCGeEsm+G4vAAgCqG4TyjjHC29i2AT5WpI" +
                "0Au&highTraffic=1&oidc=txhb&upLine=3C_BGP&fd=1&ss=s20",false,"九天狐，最后一天巅峰赛能否国服");
        videoPlayer.startPlayLogic();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
