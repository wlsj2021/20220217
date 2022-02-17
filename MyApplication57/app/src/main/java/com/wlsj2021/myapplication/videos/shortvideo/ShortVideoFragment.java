package com.wlsj2021.myapplication.videos.shortvideo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wlsj2021.myapplication.R;
import com.wlsj2021.myapplication.videos.VideoPlayRecyclerView;

public class ShortVideoFragment extends Fragment {

    private VideoPlayRecyclerView mRvVideo;
    private ShortMainAdapter adapter;
    private View mView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.duan_main, container, false);
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
        adapter = new ShortMainAdapter(getActivity());
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
