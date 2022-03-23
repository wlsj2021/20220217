package com.wlsj2021.postwang.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.wlsj2021.postwang.R;
import com.wlsj2021.postwang.base.BaseActivity;
import com.wlsj2021.postwang.base.Constant;
import com.wlsj2021.postwang.contract.register.Contract;
import com.wlsj2021.postwang.model.RegisterData;
import com.wlsj2021.postwang.presenter.RegisterPresenter;


/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/26
 * Time: 15:26
 */
public class RegisterActivity extends BaseActivity<Contract.IRegisterView, RegisterPresenter> implements Contract.IRegisterView {


    private Context mContext;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mContext = getApplicationContext();

    }


    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public void onRegister(RegisterData registerData) {

        if (registerData != null) {
            if (registerData.getErrorCode() == Constant.SUCCESS) {
                Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, ""+registerData.getErrorMsg(), Toast.LENGTH_SHORT).show();

              }
        }
    }
    public void request(View view) {

        mPresenter.register("wang5555", "666666");

    }


    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadFailed() {
    }

    @Override
    public void onLoadSuccess() {

    }

    @Override
    public void callBack(Message msg) {

    }
}
