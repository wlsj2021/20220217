package com.wlsj2021.postwang.fragment;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.wlsj2021.postwang.R;
import com.wlsj2021.postwang.adapter.LiveAdapter;
import com.wlsj2021.postwang.base.BaseFragment;
import com.wlsj2021.postwang.base.BasePresenter;
import com.wlsj2021.postwang.entity.LiveBean;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends BaseFragment {
    private List<LiveBean> liveList = new ArrayList<>();


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void init() {
        for (int i = 0; i < 50; i++) {
            LiveBean liveBean = new LiveBean(R.drawable.ic_launcher_background, "今天的直播真好看");
            liveList.add(liveBean);

            RecyclerView recyclerView = mRootView.findViewById(R.id.video_recyclerview);
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            LiveAdapter adapter = new LiveAdapter(liveList);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view =  inflater.inflate(R.layout.fragment_fullscreen_live,container,false);
//
//        return view;
//    }
}