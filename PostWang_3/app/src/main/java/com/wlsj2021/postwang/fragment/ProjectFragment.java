package com.wlsj2021.postwang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wlsj2021.postwang.R;
import com.wlsj2021.postwang.base.BaseFragment;
import com.wlsj2021.postwang.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;

public class ProjectFragment extends BaseFragment {


    public static ProjectFragment getInstance() {
        ProjectFragment fragment = new ProjectFragment();
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment;
    }

    @Override
    protected void init() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
