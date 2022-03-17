package com.wlsj2021.postwang.fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.wlsj2021.postwang.R;
import com.wlsj2021.postwang.adapter.LiveAdapter;
import com.wlsj2021.postwang.adapter.SortAdapter;
import com.wlsj2021.postwang.base.BaseFragment;
import com.wlsj2021.postwang.base.BasePresenter;
import com.wlsj2021.postwang.entity.LiveBean;
import com.wlsj2021.postwang.entity.SortBean;

import java.util.ArrayList;
import java.util.List;

public class SortFragment extends BaseFragment {
    private List<SortBean> sortList = new ArrayList<>();


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_sort;
    }

    @Override
    protected void init() {
        for (int i = 0; i < 50; i++) {
            SortBean sortBean = new SortBean("排名"+i,R.drawable.ic_launcher_foreground, "今天的直播真好看","关注");
            sortList.add(sortBean);

            RecyclerView recyclerView = mRootView.findViewById(R.id.sort_recyclerview);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            SortAdapter adapter = new SortAdapter(sortList);
            recyclerView.setAdapter(adapter);
        }
    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


}