package com.wlsj2021.myapplication.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.wlsj2021.myapplication.R;
import com.wlsj2021.myapplication.adapter.ProjectListAdapter;
import com.wlsj2021.myapplication.base.fragment.BaseFragment;
import com.wlsj2021.myapplication.base.utils.Constant;
import com.wlsj2021.myapplication.base.utils.Utils;
import com.wlsj2021.myapplication.bean.base.Event;
import com.wlsj2021.myapplication.bean.collect.Collect;
import com.wlsj2021.myapplication.bean.db.Article;
import com.wlsj2021.myapplication.contract.project.Contract;
import com.wlsj2021.myapplication.presenter.project.ProjectListPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2019/12/27
 * Time: 16:17
 */
public class ProjectListFragment extends BaseFragment<Contract.IProjectListView, ProjectListPresenter> implements Contract.IProjectListView,
        com.scwang.smartrefresh.layout.listener.OnLoadMoreListener,
        com.scwang.smartrefresh.layout.listener.OnRefreshListener {

    @BindView(R.id.normal_view)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.project_list_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.layout_error)
    ViewGroup mLayoutError;

    private ProjectListAdapter mProjectListAdapter;
    private int mCurrentPage = 1;
    private int mCid;

    private Context mContext;

    private List<Article> mProjectArticleList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public ProjectListFragment(int cid) {
        mCid = cid;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.project_list_fragment;
    }

    @Override
    protected void init() {
        mContext = getContext().getApplicationContext();
        initAdapter();
        mPresenter.loadProjectList(mCurrentPage, mCid);
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mProjectListAdapter = new ProjectListAdapter(mContext, mProjectArticleList);
        mRecyclerView.setAdapter(mProjectListAdapter);
    }

    @Override
    protected ProjectListPresenter createPresenter() {
        return new ProjectListPresenter();
    }


    @Override
    public void onLoadProjectList(List<Article> projectList) {
        mProjectArticleList.addAll(projectList);
        mProjectListAdapter.setProjectList(mProjectArticleList);
    }

    @Override
    public void onRefreshProjectList(List<Article> projectList) {
        mProjectArticleList.clear();
        mProjectArticleList.addAll(0, projectList);
        mProjectListAdapter.setProjectList(mProjectArticleList);
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

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage++;
        mPresenter.loadProjectList(mCurrentPage, mCid);
    }

    /**
     * 刷新文章从首页开始
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurrentPage = 1;
        mPresenter.refreshProjectList(mCurrentPage, mCid);
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
        setNetWorkError(false);
        ToastUtils.showShort("网络未连接请重试");
        stopLoadingView();
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @Override
    public void onLoadSuccess() {
        stopLoadingView();
        setNetWorkError(true);
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
    }

    @OnClick(R.id.layout_error)
    public void onRetry() {
        setNetWorkError(true);
        mPresenter.loadProjectList(1, mCid);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event event) {
        if (event.target == Event.TARGET_PROJECT) {
            if (event.type == Event.TYPE_COLLECT) {
                String[] data = event.data.split(";");
                if (data.length > 1 && mCid == Integer.valueOf(data[1])) {
                    int articleId = Integer.valueOf(data[0]);
                    mProjectArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = true;
                    mProjectListAdapter.notifyDataSetChanged();
                    mPresenter.collect(articleId);
                }
            } else if (event.type == Event.TYPE_UNCOLLECT) {
                String[] data = event.data.split(";");
                if (data.length > 1 && mCid == Integer.valueOf(data[1])) {
                    int articleId = Integer.valueOf(data[0]);
                    mProjectArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect = false;
                    mProjectListAdapter.notifyDataSetChanged();
                    mPresenter.unCollect(articleId);
                }
            } else if (event.type == Event.TYPE_LOGIN) {
                mProjectArticleList.clear();
                mPresenter.refreshProjectList(0, mCid);
            } else if (event.type == Event.TYPE_LOGOUT) {
                mProjectArticleList.clear();
                mPresenter.refreshProjectList(0, mCid);
            } else if (event.type == Event.TYPE_COLLECT_STATE_REFRESH) {
                int articleId = Integer.valueOf(event.data);
                // 刷新的收藏状态一定是和之前的相反
                mProjectArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect =
                        !mProjectArticleList.stream().filter(a -> a.articleId == articleId).findFirst().get().collect;
                mProjectListAdapter.notifyDataSetChanged();
            }
        }
    }
}
