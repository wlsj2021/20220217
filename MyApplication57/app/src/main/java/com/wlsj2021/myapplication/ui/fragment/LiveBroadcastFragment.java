package com.wlsj2021.myapplication.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wlsj2021.myapplication.R;
import com.wlsj2021.myapplication.adapter.HomeSquareAdapter;
import com.wlsj2021.myapplication.base.fragment.BaseFragment;
import com.wlsj2021.myapplication.base.utils.Constant;
import com.wlsj2021.myapplication.base.utils.LoginUtils;
import com.wlsj2021.myapplication.base.utils.Utils;
import com.wlsj2021.myapplication.bean.base.Event;
import com.wlsj2021.myapplication.bean.collect.Collect;
import com.wlsj2021.myapplication.bean.db.Article;
import com.wlsj2021.myapplication.contract.square.Contract;
import com.wlsj2021.myapplication.presenter.square.HomeSquarePresenter;
import com.wlsj2021.myapplication.ui.activity.LoginActivity;
import com.wlsj2021.myapplication.ui.activity.ShareArticleActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * Description: 直播
 *
 * @author: wlsj
 * @date: 2022/01/13
 * Time: 22:30
 */
public class LiveBroadcastFragment extends BaseFragment<Contract.IHomeSquareView, HomeSquarePresenter> implements Contract.IHomeSquareView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener {

    private Context mContext;

    private HomeSquareAdapter mHomeSquareAdapter;

    private int mCurrentPage = 0;

    private List<Article> mHomeSquareList = new ArrayList<>();

    @BindView(R.id.article_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.float_add)
    FloatingActionButton mFloatAdd;

    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected int getContentViewId() {
        return R.layout.homesquare_fragment;
    }

    @Override
    protected void init() {
        mContext = getContext().getApplicationContext();
        initAdapter();
        mPresenter.loadHomeSquareData(mCurrentPage);
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        initFloatBtnColor();
        initFloatListener();
    }

    private void initFloatListener() {
        mFloatAdd.setOnClickListener(v -> {
            if (LoginUtils.isLogin()) {
                Intent intent = new Intent(mContext, ShareArticleActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void initFloatBtnColor() {
        mFloatAdd.setBackgroundTintList(Utils.getOneColorStateList(mContext));
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mHomeSquareAdapter = new HomeSquareAdapter(mContext, mHomeSquareList);
        mRecyclerView.setAdapter(mHomeSquareAdapter);
    }

    @Override
    protected HomeSquarePresenter createPresenter() {
        return new HomeSquarePresenter();
    }

    @Override
    public void loadHomeSquareData(List<Article> homeSquareData) {
        if (mCurrentPage == 0) {
            mHomeSquareList.clear();
        }
        mHomeSquareList.addAll(homeSquareData);
        mHomeSquareAdapter.setHomeSquareList(mHomeSquareList);
    }

    @Override
    public void refreshHomeSquareData(List<Article> homeSquareData) {
        mHomeSquareList.clear();
        mHomeSquareList.addAll(0, homeSquareData);
        mHomeSquareAdapter.setHomeSquareList(mHomeSquareList);
    }

    @Override
    public void onCollect(Collect collect, int articleId) {
        if (collect != null) {
            if (collect.getErrorCode() == Constant.SUCCESS) {
                Utils.showSnackMessage(getActivity(), "收藏成功");
            } else {
                ToastUtils.showShort("收藏失败");
            }
        }
    }

    @Override
    public void onUnCollect(Collect collect, int articleId) {
        if (collect != null) {
            if (collect.getErrorCode() == Constant.SUCCESS) {
                Utils.showSnackMessage(getActivity(), "取消收藏");
            } else {
                ToastUtils.showShort("取消收藏失败");
            }
        }
    }

    public void startLoadingView() {
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_START_ANIMATION;
        EventBus.getDefault().post(e);
    }

    public void stopLoadingView() {
        Event e = new Event();
        e.target = Event.TARGET_MAIN;
        e.type = Event.TYPE_STOP_ANIMATION;
        EventBus.getDefault().post(e);
    }

    @Override
    public void onLoading() {
        startLoadingView();
    }

    @Override
    public void onLoadFailed() {
        stopLoadingView();
        setNetWorkError(false);
        ToastUtils.showShort("网络未连接请重试");
        mSmartRefreshLayout.finishRefresh(false);
        mSmartRefreshLayout.finishLoadMore(false);
    }

    @Override
    public void onLoadSuccess() {
        stopLoadingView();
        setNetWorkError(true);
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @OnClick(R.id.layout_error)
    public void onReTry() {
        setNetWorkError(true);
        mPresenter.loadHomeSquareData(0);
    }

    private void setNetWorkError(boolean isSuccess) {
        if (isSuccess) {
            mSmartRefreshLayout.setVisibility(View.VISIBLE);
            mLayoutError.setVisibility(View.GONE);
        } else {
            mSmartRefreshLayout.setVisibility(View.GONE);
            mLayoutError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        mPresenter.loadHomeSquareData(mCurrentPage);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 0;
        mPresenter.refreshHomeSquareData(mCurrentPage);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_SQUARE) {
            if (event.type == Event.TYPE_COLLECT) {
                int articleId = Integer.valueOf(event.data);
                mHomeSquareList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = true;
                mHomeSquareAdapter.notifyDataSetChanged();
                mPresenter.collect(articleId);
            } else if (event.type == Event.TYPE_UNCOLLECT) {
                int articleId = Integer.valueOf(event.data);
                mHomeSquareList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = false;
                mHomeSquareAdapter.setHomeSquareList(mHomeSquareList);
                mPresenter.unCollect(articleId);
            } else if (event.type == Event.TYPE_LOGIN) {
                mHomeSquareList.clear();
                mPresenter.refreshHomeSquareData(0);
            } else if (event.type == Event.TYPE_LOGOUT) {
                mHomeSquareList.clear();
                mPresenter.refreshHomeSquareData(0);
            } else if (event.type == Event.TYPE_COLLECT_STATE_REFRESH) {
                int articleId = Integer.valueOf(event.data);
                // 刷新的收藏状态一定是和之前的相反
                mHomeSquareList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect =
                        !mHomeSquareList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect;
                mHomeSquareAdapter.notifyDataSetChanged();
            } else if (event.type == Event.TYPE_REFRESH_COLOR) {
                initFloatBtnColor();
            } else if (event.type == Event.TYPE_DELETE_SHARE) {
                int articleId = Integer.valueOf(event.data);
                LitePal.deleteAll(Article.class, "type=? and articleId=?", Article.TYPE_SQUARE + "", articleId + "");
                List<Article> tempList = mHomeSquareList.stream().filter(a -> a.articleId != articleId).collect(Collectors.toList());
                mHomeSquareList.clear();
                mHomeSquareList.addAll(tempList);
                mHomeSquareAdapter.setHomeSquareList(mHomeSquareList);
            }
        }
    }
}
