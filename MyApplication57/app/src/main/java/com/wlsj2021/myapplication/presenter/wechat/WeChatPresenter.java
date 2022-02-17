package com.wlsj2021.myapplication.presenter.wechat;

import com.wlsj2021.myapplication.base.presenter.BasePresenter;
import com.wlsj2021.myapplication.base.utils.Constant;
import com.wlsj2021.myapplication.bean.db.Author;
import com.wlsj2021.myapplication.bean.wechat.WeChatClassifyData;
import com.wlsj2021.myapplication.contract.wechat.Contract;
import com.wlsj2021.myapplication.model.WeChatModel;

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
 * @date: 2019/12/29
 * Time: 12:07
 */
public class WeChatPresenter extends BasePresenter<Contract.IWeChatView> implements Contract.IWeChatPresenter {
    Contract.IWeChatModel iWeChatModel;

    public WeChatPresenter() {
        iWeChatModel = new WeChatModel();
    }

    @Override
    public void loadWeChatClassify() {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        iWeChatModel.loadWeChatClassify()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Author>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Author> authorList) {
                        if (isViewAttached()) {
                            getView().onLoadWeChatClassify(authorList);
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
    public void refreshWeChatClassify() {
        if (isViewAttached()) {
            getView().onLoading();
        } else {
            return;
        }
        iWeChatModel.refreshWeChatClassify()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Author>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(List<Author> authorList) {
                        if (isViewAttached()) {
                            getView().onRefreshWeChatClassify(authorList);
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
