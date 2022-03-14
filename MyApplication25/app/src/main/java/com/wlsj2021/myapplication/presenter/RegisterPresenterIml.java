package com.wlsj2021.myapplication.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.wlsj2021.myapplication.listener.OnRegisterListener;
import com.wlsj2021.myapplication.model.RegisterModel;
import com.wlsj2021.myapplication.model.RegisterModelIml;
import com.wlsj2021.myapplication.view.RegisterView;
//5
public class RegisterPresenterIml implements RegisterPresenter,BaseLifecycleObserver, OnRegisterListener {
private RegisterView mRegisterView;
private RegisterModel mRegisterModel;


    public RegisterPresenterIml(RegisterView registerView) {
        this.mRegisterView = registerView;
        this.mRegisterModel = new RegisterModelIml();

    }

    @Override
    public void registerMe(String usn, String psw, String rePsw) {

        mRegisterView.registerSuccess();
        mRegisterModel.rigister(usn,psw,rePsw,this);

    }

    @Override
    public void onDestroy() {
        mRegisterView  = null;
    }

    @Override
    public void onSuccess() {
        mRegisterView.registerSuccess();

    }

    @Override
    public void onFail() {
        mRegisterView.registerFail();
    }

    @Override
    public void onAny(LifecycleOwner owner) {

    }

    @Override
    public void onCreate(LifecycleOwner owner) {

    }

    @Override
    public void onStart(LifecycleOwner owner) {

    }

    @Override
    public void onStop(LifecycleOwner owner) {

    }

    @Override
    public void onResume(LifecycleOwner owner) {

    }

    @Override
    public void onPause(LifecycleOwner owner) {

    }

    @Override
    public void onDestory(LifecycleOwner owner) {

    }
}
