package com.wlsj2021.myapplication.presenter.register;

import com.wlsj2021.myapplication.base.presenter.BasePresenter;
import com.wlsj2021.myapplication.bean.me.RegisterData;
import com.wlsj2021.myapplication.contract.register.Contract;
import com.wlsj2021.myapplication.model.RegisterModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/26
 * Time: 15:20
 */
public class RegisterPresenter extends BasePresenter<Contract.IRegisterView> implements Contract.IRegisterPresenter {
    Contract.IRegisterModel iRegisterModel;

    public RegisterPresenter() {
        iRegisterModel = new RegisterModel();
    }
    @Override
    public void register(String userName, String passWord) {
        iRegisterModel.register(userName, passWord)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(RegisterData registerData) {
                        if (isViewAttached()) {
                            getView().onRegister(registerData);
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
