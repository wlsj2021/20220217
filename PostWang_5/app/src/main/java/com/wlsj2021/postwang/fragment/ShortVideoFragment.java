package com.wlsj2021.postwang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wlsj2021.postwang.R;
import com.wlsj2021.postwang.adapter.ShortVideoAdapter;
import com.wlsj2022.wlsj.shortvideos.VideoPlayRecyclerView;

public class ShortVideoFragment extends Fragment {

    private VideoPlayRecyclerView mRvVideo;
    private ShortVideoAdapter adapter;
    private View mView;

    public static ShortVideoFragment getInstance() {
        ShortVideoFragment fragment = new ShortVideoFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.short_video, container, false);
        initView();
        return mView;
    }


    private void initView() {
       mView.findViewById(R.id.ibBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
            }
        });
        mRvVideo = mView.findViewById(R.id.rvVideo);
        adapter = new ShortVideoAdapter(getActivity());
        mRvVideo.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.release();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        adapter.release();

    }
}
