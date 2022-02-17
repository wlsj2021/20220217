package com.wlsj2021.myapplication.presenter.live;

import com.wlsj2021.myapplication.base.presenter.BasePresenter;
import com.wlsj2021.myapplication.bean.collect.Collect;
import com.wlsj2021.myapplication.bean.db.Article;
import com.wlsj2021.myapplication.contract.square.Contract;
import com.wlsj2021.myapplication.model.HomeSquareModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:直播Presenter
 *
 * @author: wlsj
 * @date: 2022/01/14
 * Time: 16:27
 */
public class LivePresenter extends BasePresenter<Contract.IHomeSquareView> implements Contract.IHomeSquarePresenter {
    Contract.IHomeSquareModel iHomeSquareModel;

    public LivePresenter() {
        iHomeSquareModel = new HomeSquareModel();
    }

    @Override
    public void loadHomeSquareData(int pageNum) {
        if (isViewAttached() && pageNum == 0) {
            getView().onLoading();
        }
        iHomeSquareModel.loadHomeSquareData(pageNum)
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
                            getView().loadHomeSquareData(articleList);
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
    public void refreshHomeSquareData(int pageNum) {
        iHomeSquareModel.refreshHomeSquareData(pageNum)
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
                            getView().refreshHomeSquareData(articleList);
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
        iHomeSquareModel.collect(articleId)
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
        iHomeSquareModel.unCollect(articleId)
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
