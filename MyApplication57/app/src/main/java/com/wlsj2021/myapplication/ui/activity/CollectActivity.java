package com.wlsj2021.myapplication.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wlsj2021.myapplication.Custom.CustomDialog;
import com.wlsj2021.myapplication.Custom.loading.LoadingView;
import com.wlsj2021.myapplication.R;
import com.wlsj2021.myapplication.adapter.CollectAdaper;
import com.wlsj2021.myapplication.base.activity.BaseActivity;
import com.wlsj2021.myapplication.base.utils.Constant;
import com.wlsj2021.myapplication.base.utils.LoginUtils;
import com.wlsj2021.myapplication.base.utils.Utils;
import com.wlsj2021.myapplication.bean.base.Event;

import com.wlsj2021.myapplication.bean.db.Collect;
import com.wlsj2021.myapplication.contract.collect.Contract;
import com.wlsj2021.myapplication.presenter.collect.CollectPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

public class CollectActivity extends BaseActivity<Contract.ICollectView, CollectPresenter> implements Contract.ICollectView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener {

    private CollectAdaper mCollectAdapter;
    private int mCurrentPage = 0;

    private Context mContext;

    private List<Collect> mCollectList = new ArrayList<>();


    @BindView(R.id.article_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.collect_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.loading_view)
    LoadingView mLoadingView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        mPresenter.loadCollectData(mCurrentPage);
        initAdapter();
        initToolbar();
        initStatusBar();
        // 滑动流畅
        mRecyclerView.setNestedScrollingEnabled(false);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.collect_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_collect:
                CustomDialog customDialog = new CustomDialog(this);
                DisplayMetrics outMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
                int widthPixels = outMetrics.widthPixels;
                int heightPixels = outMetrics.heightPixels;
                customDialog.getWindow().setLayout(widthPixels * 3 / 4, heightPixels * 3 / 4);
                customDialog.setAuthor(LoginUtils.getLoginUser());
                customDialog.setOnPositiveListener(v -> {
                    if (TextUtils.isEmpty(customDialog.getTitle()) || TextUtils.isEmpty(customDialog.getLink())) {
                        ToastUtils.showShort(mContext.getString(R.string.author_link_empty));
                        return;
                    }
                    mPresenter.addCollect(customDialog.getTitle(), customDialog.getAuthor(), customDialog.getLink());
                    customDialog.dismiss();
                });
                customDialog.setOnNegativeListener(v -> {
                    customDialog.dismiss();
                });
                customDialog.show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void initToolbar() {
        mToolbar.setBackgroundColor(Utils.getColor(mContext));
        mToolbar.setTitle(R.string.collect_page);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setStatusBarColor(Utils.getColor(mContext));
        }
        if (ColorUtils.calculateLuminance(Utils.getColor(mContext)) >= 0.5) {
            // 设置状态栏中字体的颜色为黑色
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            // 跟随系统
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }


    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mCollectAdapter = new CollectAdaper(mContext, mCollectList);
        mRecyclerView.setAdapter(mCollectAdapter);
    }


    @Override
    protected CollectPresenter createPresenter() {
        return new CollectPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_COLLECT) {
            if (event.type == Event.TYPE_UNCOLLECT) {
                int articleId = Integer.valueOf(event.data.split(";")[0]);
                int originId = Integer.valueOf(event.data.split(";")[1]);
                List<Collect> tempList = mCollectList.stream().filter(a -> a.articleId != articleId).collect(Collectors.toList());
                changeCollectState(originId);
                mCollectList.clear();
                mCollectList.addAll(tempList);
                mCollectAdapter.setCollectList(mCollectList);
                mPresenter.unCollect(articleId, originId);
            } else if (event.type == Event.TYPE_COLLECT_STATE_REFRESH) {
                int originId = Integer.valueOf(event.data);
                List<Collect> tempList = mCollectList.stream().filter(a -> a.originId != originId).collect(Collectors.toList());
                mCollectList.clear();
                mCollectList.addAll(tempList);
                mCollectAdapter.setCollectList(mCollectList);
            }
        }
    }

    @Override
    public void onLoadCollectData(List<Collect> collectList) {
        mLoadingView.setVisibility(View.GONE);
        mCollectList.addAll(collectList);
        mCollectAdapter.setCollectList(mCollectList);
    }

    @Override
    public void onRefreshCollectData(List<Collect> collectList) {
        mCollectList.clear();
        mCollectList.addAll(0, collectList);
        mCollectAdapter.setCollectList(mCollectList);
    }

    @Override
    public void onAddCollect(Collect addCollect) {
        mCollectList.add(0, addCollect);
        mCollectAdapter.setCollectList(mCollectList);
    }

    @Override
    public void onUnCollect(com.wlsj2021.myapplication.bean.collect.Collect collect, int articleId) {
        if (collect != null) {
            if (collect.getErrorCode() == Constant.SUCCESS) {
                Utils.showSnackMessage(this, "取消收藏");
            } else {
                ToastUtils.showShort("取消收藏失败");
            }
        }
    }

    private void changeCollectState(int originId) {
        // 取消收藏传递给home
        Event homeEvent = new Event();
        homeEvent.target = Event.TARGET_HOME;
        homeEvent.type = Event.TYPE_COLLECT_STATE_REFRESH;
        homeEvent.data = originId + "";
        EventBus.getDefault().post(homeEvent);

        // 取消收藏传递给project
        Event projectEvent = new Event();
        projectEvent.target = Event.TARGET_PROJECT;
        projectEvent.type = Event.TYPE_COLLECT_STATE_REFRESH;
        projectEvent.data = originId + "";
        EventBus.getDefault().post(projectEvent);

        // 取消收藏传递给square
        Event squareEvent = new Event();
        squareEvent.target = Event.TARGET_SQUARE;
        squareEvent.type = Event.TYPE_COLLECT_STATE_REFRESH;
        squareEvent.data = originId + "";
        EventBus.getDefault().post(squareEvent);

        // 取消收藏传递给tree
        Event treeEvent = new Event();
        treeEvent.target = Event.TARGET_TREE;
        treeEvent.type = Event.TYPE_COLLECT_STATE_REFRESH;
        treeEvent.data = originId + "";
        EventBus.getDefault().post(treeEvent);

        // 取消收藏传递给wechat
        Event wechatEvent = new Event();
        wechatEvent.target = Event.TARGET_WX;
        wechatEvent.type = Event.TYPE_COLLECT_STATE_REFRESH;
        wechatEvent.data = originId + "";
        EventBus.getDefault().post(wechatEvent);
    }

    @Override
    public void onLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.startTranglesAnimation();
    }

    @Override
    public void onLoadFailed() {
        mLoadingView.setVisibility(View.GONE);
        ToastUtils.showShort("加载失败");
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadSuccess() {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        mPresenter.loadCollectData(mCurrentPage);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 0;
        mPresenter.refreshCollectData(mCurrentPage);
    }
}
