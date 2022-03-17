package com.wlsj2021.postwang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wlsj2021.postwang.R;
import com.wlsj2021.postwang.activity.LiveActivity;
import com.wlsj2021.postwang.adapter.LiveAdapter;
import com.wlsj2021.postwang.base.BaseFragment;
import com.wlsj2021.postwang.base.BasePresenter;
import com.wlsj2021.postwang.entity.LiveBean;

import java.util.ArrayList;
import java.util.List;

public class LiveFragment extends BaseFragment {

    private List<LiveBean> liveList = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_live;
    }

    @Override
    protected void init() {

        for (int i = 0; i < 50; i++) {
        LiveBean liveBean = new LiveBean(R.drawable.ic_launcher_background, "今天的直播真好看");
        liveList.add(liveBean);

        RecyclerView recyclerView = mRootView.findViewById(R.id.live_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        LiveAdapter adapter = new LiveAdapter(liveList);
        recyclerView.setAdapter(adapter);
        adapter.setItemClickListener(new LiveAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                    if (position == 0){
                        startActivity(new Intent(getActivity(),LiveActivity.class));
                    }
            }
        });



        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}