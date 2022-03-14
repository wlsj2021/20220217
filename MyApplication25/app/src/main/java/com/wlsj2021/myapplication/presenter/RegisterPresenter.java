package com.wlsj2021.myapplication.presenter;

//4
public interface RegisterPresenter  {
    //参数接口
    void registerMe(String usn,String psw,String rePsw);
    //释放
    void onDestroy();
}
