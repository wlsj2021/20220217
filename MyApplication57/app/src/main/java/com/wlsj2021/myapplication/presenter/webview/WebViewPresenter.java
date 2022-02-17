package com.wlsj2021.myapplication.presenter.webview;

import com.wlsj2021.myapplication.base.presenter.BasePresenter;
import com.wlsj2021.myapplication.bean.collect.Collect;
import com.wlsj2021.myapplication.contract.webview.Contract;
import com.wlsj2021.myapplication.model.WebViewModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/02/01
 * Time: 16:17
 */
public class WebViewPresenter extends BasePresenter<Contract.IWebView> implements Contract.IWebViewPresenter {
    Contract.IWebViewModel iWebViewModel;
    public WebViewPresenter() {
        iWebViewModel = new WebViewModel();
    }
    @Override
    public void collect(int articleId) {
        iWebViewModel.collect(articleId)
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
                            getView().onCollect(collect);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void unCollect(int articleId) {
        iWebViewModel.unCollect(articleId)
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
                            getView().onUnCollect(collect);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
