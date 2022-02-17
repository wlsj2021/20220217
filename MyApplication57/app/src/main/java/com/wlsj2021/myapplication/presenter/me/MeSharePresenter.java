package com.wlsj2021.myapplication.presenter.me;

import com.wlsj2021.myapplication.base.presenter.BasePresenter;
import com.wlsj2021.myapplication.bean.collect.Collect;
import com.wlsj2021.myapplication.bean.db.Share;
import com.wlsj2021.myapplication.bean.share.DeleteShare;
import com.wlsj2021.myapplication.contract.mesharearticle.Contract;
import com.wlsj2021.myapplication.model.MeShareModel;

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
 * @date: 2020/01/19
 * Time: 22:44
 */
public class MeSharePresenter extends BasePresenter<Contract.IMeShareView> implements Contract.IMeSharePresenter {
    Contract.IMeShareModel iMeShareModel;
    public MeSharePresenter(){
        iMeShareModel = new MeShareModel();
    }
    @Override
    public void loadShareArticle(int pageNum) {
        if (isViewAttached() && pageNum == 1) {
            getView().onLoading();
        }
        iMeShareModel.loadShareArticle(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Share>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Share> shares) {
                        if (isViewAttached()) {
                            getView().onLoadShareArticle(shares);
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
    public void refreshShareArticle(int pageNum) {
        iMeShareModel.refreshShareArticle(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Share>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Share> shares) {
                        if (isViewAttached()) {
                            getView().onRefreshShareArticle(shares);
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
    public void deleteShareArticle(int articleId) {
        iMeShareModel.deleteShareArticle(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DeleteShare>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DeleteShare deleteShare) {
                        if (isViewAttached()) {
                            getView().onDeleteShareArticle(deleteShare, articleId);
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
        iMeShareModel.collect(articleId)
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
    public void unCollect(int articleId) {
        iMeShareModel.unCollect(articleId)
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
}
