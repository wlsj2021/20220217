package com.wlsj2021.postwang.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created with Android Studio.
 * Description: Base Fragment
 *
 * @author: wlsj
 * @date: 2019/12/18
 * Time: 21:26
 */
public abstract class BaseFragment<V, P extends BasePresenter<V>> extends Fragment{

    protected P mPresenter;

    protected abstract int getContentViewId();

    protected View mRootView;



    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 创建Presenter
     *
     * @return p
     */
    protected abstract P createPresenter();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (mRootView == null) {
            mRootView = inflater.inflate(getContentViewId(), container, false);
        }
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
        init();
        return mRootView;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
