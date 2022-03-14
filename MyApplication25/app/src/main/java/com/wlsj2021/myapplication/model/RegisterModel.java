package com.wlsj2021.myapplication.model;
//2
import com.wlsj2021.myapplication.listener.OnRegisterListener;
//model接口
public interface RegisterModel {
    void rigister(String usn, String psw, String rePsw, OnRegisterListener mOnRegisterListener);
}
