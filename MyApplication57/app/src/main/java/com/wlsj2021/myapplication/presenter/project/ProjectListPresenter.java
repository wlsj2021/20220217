package com.wlsj2021.myapplication.presenter.project;

import com.wlsj2021.myapplication.base.presenter.BasePresenter;
import com.wlsj2021.myapplication.bean.collect.Collect;
import com.wlsj2021.myapplication.bean.db.Article;
import com.wlsj2021.myapplication.contract.project.Contract;
import com.wlsj2021.myapplication.model.ProjectListModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2019/12/27
 * Time: 17:42
 */
public class ProjectListPresenter extends BasePresenter<Contract.IProjectListView> implements Contract.IProjectListPresenter {
    Contract.IProjectListModel iProjectListModel;

    public ProjectListPresenter() {
        iProjectListModel = new ProjectListModel();
    }

    @Override
    public void loadProjectList(int pageNum, int cid) {
        if (isViewAttached() && pageNum == 1) {
            getView().onLoading();
        }
        iProjectListModel.loadProjectList(pageNum, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Article>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Article> articleList) {
                        if (isViewAttached()) {
                            getView().onLoadProjectList(articleList);
                            getView().onLoadSuccess();
                        }
                    }


                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (isViewAttached()) {
                            getView().onLoadFailed();
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void refreshProjectList(int pageNum, int cid) {
        iProjectListModel.refreshProjectList(pageNum, cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Article>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Article> articleList) {
                        if (isViewAttached()) {
                            getView().onRefreshProjectList(articleList);
                            getView().onLoadSuccess();
                        }
                    }


                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (isViewAttached()) {
                            getView().onLoadFailed();
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    @Override
    public void collect(int articleId) {
        iProjectListModel.collect(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Collect>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Collect collect) {
                        if (isViewAttached()) {
                            getView().onCollect(collect, articleId);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (isViewAttached()) {
                            getView().onLoadFailed();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void unCollect(int articleId) {
        iProjectListModel.unCollect(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Collect>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Collect collect) {
                        if (isViewAttached()) {
                            getView().onUnCollect(collect, articleId);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        if (isViewAttached()) {
                            getView().onLoadFailed();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}