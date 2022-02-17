package com.wlsj2021.myapplication.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.wlsj2021.myapplication.R;
import com.wlsj2021.myapplication.adapter.TreeAdapter;
import com.wlsj2021.myapplication.base.fragment.BaseFragment;
import com.wlsj2021.myapplication.base.utils.Constant;
import com.wlsj2021.myapplication.base.utils.Utils;
import com.wlsj2021.myapplication.bean.base.Event;
import com.wlsj2021.myapplication.bean.square.TreeData;
import com.wlsj2021.myapplication.contract.square.Contract;
import com.wlsj2021.myapplication.presenter.square.TreePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * Created with Android Studio.
 * Description: 知识体系
 *
 * @author: wlsj
 * @date: 2/01/07
 * Time: 20:26
 */
public class TreeFragment extends BaseFragment<Contract.ITreeView, TreePresenter> implements Contract.ITreeView {
    @BindView(R.id.navigation_tab_layout)
    VerticalTabLayout mVerticalTabLayout;

    @BindView(R.id.normal_view)
    LinearLayout mViewGroup;

    @BindView(R.id.navigation_divider)
    View mDivider;

    @BindView(R.id.navigation_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

    private TreeAdapter mTreeAdapter;

    private LinearLayoutManager mLinearLayoutManager;

    private Context mContext;

    @Override
    protected int getContentViewId() {
        return R.layout.square_navigation;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void init() {
        mContext = getContext().getApplicationContext();
        initAdapter();
        mRecyclerView.setNestedScrollingEnabled(false);
        mPresenter.loadTree();
        setChildViewVisibility(View.VISIBLE);
        mVerticalTabLayout.setIndicatorColor(Utils.getColor(mContext));
    }

    private void initAdapter() {
        mLinearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mTreeAdapter = new TreeAdapter(mContext);
        mRecyclerView.setAdapter(mTreeAdapter);
    }

    @Override
    protected TreePresenter createPresenter() {
        return new TreePresenter();
    }

    @Override
    public void loadTreeData(TreeData treeData) {
        mTreeAdapter.setBeans(treeData);
        if (treeData.getErrorCode() == Constant.SUCCESS) {
            List<String> tabNames = treeData.getData()
                    .stream()
                    .map(TreeData.DataBean::getName)
                    .collect(Collectors.toList());
            mVerticalTabLayout.setTabAdapter(new TabAdapter() {
                @Override
                public int getCount() {
                    return tabNames == null ? 0 : tabNames.size();
                }

                @Override
                public ITabView.TabBadge getBadge(int position) {
                    return null;
                }

                @Override
                public ITabView.TabIcon getIcon(int position) {
                    return null;
                }

                @Override
                public ITabView.TabTitle getTitle(int position) {
                    return new TabView.TabTitle.Builder()
                            .setContent(tabNames.get(position))
                            .setTextColor(ContextCompat.getColor(mContext, R.color.always_white_text),
                                    Utils.randomColor())
                            .build();
                }

                @Override
                public int getBackground(int position) {
                    return 0;
                }
            });
            leftRightLinkTogether();
        }
    }

    /**
     * 实现VerticalTabLayout 和 RecyclerView 二级联动
     */
    private void leftRightLinkTogether() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 将VerticalTabLayout 的选中位置与RecyclerView的第一条的标题相同
                mVerticalTabLayout.setTabSelected(mLinearLayoutManager.findFirstVisibleItemPosition());
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        mVerticalTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                // 将RecyclerView的第一条的位置和VerticalTabLayout的 选中的index的position设置相同
                mLinearLayoutManager.scrollToPositionWithOffset(position, 0);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onLoadFailed() {
        setNetWorkError(false);
        ToastUtils.showShort("网络未连接请重试");
    }

    @Override
    public void onLoadSuccess() {
        setNetWorkError(true);
    }

    @OnClick(R.id.layout_error)
    public void onReTry() {
        setNetWorkError(true);
        mPresenter.loadTree();
    }

    private void setNetWorkError(boolean isSuccess) {
        if (isSuccess) {
            mViewGroup.setVisibility(View.VISIBLE);
            mLayoutError.setVisibility(View.GONE);
        } else {
            mViewGroup.setVisibility(View.GONE);
            mLayoutError.setVisibility(View.VISIBLE);
        }
    }

    private void setChildViewVisibility(int visibility) {
        mViewGroup.setVisibility(visibility);
        mVerticalTabLayout.setVisibility(visibility);
        mDivider.setVisibility(visibility);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_TREE) {
            if (event.type == Event.TYPE_REFRESH_COLOR) {
                mVerticalTabLayout.setIndicatorColor(Utils.getColor(mContext));
            }
        }
    }
}
