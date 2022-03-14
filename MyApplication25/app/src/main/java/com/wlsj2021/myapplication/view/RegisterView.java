package com.wlsj2021.myapplication.view;

import rx.Observable;

//1
public interface RegisterView {
    void registerSuccess();
    void registerFail();
    void showDialog();
    void hideDialog();
    void navigate();
    void updateData();
    //1RXJava内存优化
    <T> Observable.Transformer<T, T> bindToLifecycle();
}
