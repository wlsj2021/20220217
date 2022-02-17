package com.wlsj2021.myapplication.presenter.home;

import com.wlsj2021.myapplication.base.presenter.BasePresenter;
import com.wlsj2021.myapplication.bean.db.Article;
import com.wlsj2021.myapplication.bean.db.Banner;
import com.wlsj2021.myapplication.bean.collect.Collect;
import com.wlsj2021.myapplication.contract.home.Contract;
import com.wlsj2021.myapplication.model.HomeModel;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:对发送请求进行封装，利用RxJava处理封装的请求，并将数据和View进行绑定
 *
 * @author: wlsj
 * @date: 2019/12/19
 * Time: 16:00
 */
public class HomePresenter extends BasePresenter<Contract.IHomeView> implements Contract.IHomePresenter {

    Contract.IHomeModel iHomeModel;

    public HomePresenter() {
        iHomeModel = new HomeModel();
    }

    @Override
    public void loadBanner() {
        iHomeModel.loadBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Banner>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Banner> bannerList) {
                        if (isViewAttached()) {
                            getView().loadBanner(bannerList);
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
    public void refreshBanner() {
        iHomeModel.refreshBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Banner>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Banner> bannerList) {
                        if (isViewAttached()) {
                            getView().refreshBanner(bannerList);
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
    public void loadArticle(int pageNum) {
        if (isViewAttached() && pageNum == 0) {
            getView().onLoading();
        }
        iHomeModel.loadArticle(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Article>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Article> articleList) {
                        if (pageNum == 0) {
                            iHomeModel.loadTopArticle()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<List<Article>>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {
                                            mCompositeDisposable.add(d);
                                        }

                                        @Override
                                        public void onNext(List<Article> topArticleList) {
                                            if (isViewAttached()) {
                                                articleList.addAll(0, topArticleList);
                                                getView().loadArticle(articleList);
                                                getView().onLoadSuccess();
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        } else {
                            if (isViewAttached()) {
                                getView().loadArticle(articleList);
                                getView().onLoadSuccess();
                            }
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
    public void refreshArticle(int pageNum) {
        iHomeModel.refreshArticle(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Article>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Article> articleList) {
                        if (pageNum == 0) {
                            iHomeModel.refreshTopArticle()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Observer<List<Article>>() {
                                        @Override
                                        public void onSubscribe(Disposable d) {
                                            mCompositeDisposable.add(d);
                                        }

                                        @Override
                                        public void onNext(List<Article> topArticleList) {
                                            if (isViewAttached()) {
                                                articleList.addAll(0, topArticleList);
                                                getView().refreshArticle(articleList);
                                                getView().onLoadSuccess();
                                            }
                                        }

                                        @Override
                                        public void onError(Throwable e) {

                                        }

                                        @Override
                                        public void onComplete() {

                                        }
                                    });
                        } else {
                            if (isViewAttached()) {
                                getView().refreshArticle(articleList);
                                getView().onLoadSuccess();
                            }
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
        iHomeModel.collect(articleId)
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
        iHomeModel.unCollect(articleId)
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
