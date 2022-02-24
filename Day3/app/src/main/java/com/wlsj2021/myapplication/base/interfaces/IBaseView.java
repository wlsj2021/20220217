package com.wlsj2021.myapplication.base.interfaces;

/**
 * Created with Android Studio.
 * Description: 用来指示错误和完成的接口
 *
 * @author: wlsj
 * @date: 2019/12/27
 * Time: 10:52
 */
public interface IBaseView {

    /**
     * 加载中
     */
    void onLoading();

    /**
     * 加载错误回调
     */
    void onLoadFailed();

    /**
     * 加载完成
     */
    void onLoadSuccess();
}
