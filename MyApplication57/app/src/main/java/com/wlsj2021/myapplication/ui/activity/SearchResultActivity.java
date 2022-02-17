package com.wlsj2021.myapplication.ui.activity;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wlsj2021.myapplication.Custom.loading.LoadingView;
import com.wlsj2021.myapplication.R;
import com.wlsj2021.myapplication.adapter.SearchResultAdapter;
import com.wlsj2021.myapplication.base.activity.BaseActivity;
import com.wlsj2021.myapplication.base.utils.Constant;
import com.wlsj2021.myapplication.base.utils.Utils;
import com.wlsj2021.myapplication.bean.base.Event;
import com.wlsj2021.myapplication.bean.collect.Collect;
import com.wlsj2021.myapplication.bean.db.Article;
import com.wlsj2021.myapplication.contract.searchresult.Contract;
import com.wlsj2021.myapplication.presenter.searchresult.SearchResultPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author wjxbless
 */
public class SearchResultActivity extends BaseActivity<Contract.ISearchResultView, SearchResultPresenter> implements Contract.ISearchResultView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener{

    private int mCurrentPage = 0;

    private Context mContext;

    private List<Article> mSearchResultList = new ArrayList<>();

    private SearchResultAdapter mSearchResultAdapter;

    private String mKeyWords;


    @BindView(R.id.article_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.search_result_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.loading_view)
    LoadingView mLoadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mContext = getApplicationContext();
        initKeyWords();
        mPresenter.loadSearchResult(mCurrentPage, mKeyWords);
        initAdapter();
        initToolbar();
        initStatusBar();
        // 滑动流畅
        mRecyclerView.setNestedScrollingEnabled(false);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
    }

    private void initToolbar() {
        mToolbar.setBackgroundColor(Utils.getColor(mContext));
        mToolbar.setTitle(R.string.search_result);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
        mSearchResultAdapter = new SearchResultAdapter(mContext, mSearchResultList);
        mRecyclerView.setAdapter(mSearchResultAdapter);
    }

    private void initKeyWords() {
        mKeyWords = getIntent().getStringExtra(Constant.KEY_KEYWORD);
        if (TextUtils.isEmpty(mKeyWords)) {
            return;
        }
    }

    @Override
    protected SearchResultPresenter createPresenter() {
        return new SearchResultPresenter();
    }


    @Override
    public void onLoadSearchResult(List<Article> searchWordData) {
        mLoadingView.setVisibility(View.GONE);
        mSearchResultList.addAll(searchWordData);
        mSearchResultAdapter.setSearchResultList(mSearchResultList);
    }

    @Override
    public void onRefreshSearchResult(List<Article> searchWordData) {
        mSearchResultList.clear();
        mSearchResultList.addAll(0, searchWordData);
        mSearchResultAdapter.setSearchResultList(mSearchResultList);
    }

    @Override
    public void onCollect(Collect collect, int articleId) {
        if (collect != null) {
            if (collect.getErrorCode() == Constant.SUCCESS) {
                Utils.showSnackMessage(this, "收藏成功");
            } else {
                ToastUtils.showShort("收藏失败");
            }
        }
    }

    @Override
    public void onUnCollect(Collect collect, int articleId) {
        if (collect != null) {
            if (collect.getErrorCode() == Constant.SUCCESS) {
                Utils.showSnackMessage(this, "取消收藏");
            } else {
                ToastUtils.showShort("取消收藏失败");
            }
        }
    }

    @Override
    public void onLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.startTranglesAnimation();
    }

    @Override
    public void onLoadSuccess() {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadFailed() {
        mLoadingView.setVisibility(View.GONE);
        ToastUtils.showShort("加载失败");
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        mPresenter.loadSearchResult(mCurrentPage, mKeyWords);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 0;
        mPresenter.refreshSearchResult(mCurrentPage, mKeyWords);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_SEARCH_RESULT) {
            if (event.type == Event.TYPE_COLLECT) {
                int articleId = Integer.valueOf(event.data);
                mSearchResultList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = true;
                mSearchResultAdapter.notifyDataSetChanged();
                mPresenter.collect(articleId);
            } else if (event.type == Event.TYPE_UNCOLLECT) {
                int articleId = Integer.valueOf(event.data);
                mSearchResultList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = false;
                mSearchResultAdapter.notifyDataSetChanged();
                mPresenter.unCollect(articleId);
            } else if (event.type == Event.TYPE_LOGIN) {
                mSearchResultList.clear();
                mPresenter.loadSearchResult(0, mKeyWords);
            } else if (event.type == Event.TYPE_LOGOUT) {
                mSearchResultList.clear();
                mPresenter.loadSearchResult(0, mKeyWords);
            } else if (event.type == Event.TYPE_REFRESH_COLOR) {
                mToolbar.setBackgroundColor(Utils.getColor(mContext));
            } else if (event.type == Event.TYPE_COLLECT_STATE_REFRESH) {
                int articleId = Integer.valueOf(event.data);
                // 刷新的收藏状态一定是和之前的相反
                mSearchResultList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect =
                        !mSearchResultList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect;
                mSearchResultAdapter.notifyDataSetChanged();
            }
        }
    }
}
