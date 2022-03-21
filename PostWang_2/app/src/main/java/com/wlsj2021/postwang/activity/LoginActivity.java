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

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ToastUtils;
import com.wlsj2021.postwang.R;
import com.wlsj2021.postwang.base.BaseActivity;
import com.wlsj2021.postwang.base.Constant;
import com.wlsj2021.postwang.base.Event;
import com.wlsj2021.postwang.base.Utils;
import com.wlsj2021.postwang.contract.login.Contract;
import com.wlsj2021.postwang.model.LoginData;
import com.wlsj2021.postwang.presenter.LoginPresenter;

import org.greenrobot.eventbus.EventBus;



public class LoginActivity extends BaseActivity<Contract.ILoginView, LoginPresenter> implements Contract.ILoginView {
    private String mUserNameText;
    private String mPassWordText;

    EditText mUsername;

    EditText mPassword;

    Button mLoginButton;

    Button mRegister;

    Toolbar mToolbar;

    private Context mContext;

    private String mRegisterName;

    private String mRegisterPassword;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mLoginButton = findViewById(R.id.login);
        mRegister = findViewById(R.id.go_register);



        mContext = getApplicationContext();
        try {
            mRegisterName = getIntent().getExtras().getString(Constant.EXTRA_KEY_USERNAME);
            mRegisterPassword = getIntent().getExtras().getString(Constant.EXTRA_VALUE_PASSWORD);
            mUsername.setText(mRegisterName);
            mPassword.setText(mRegisterPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        initToolbar();
        mLoginButton.getBackground().setColorFilter(
                Utils.getColor(mContext), PorterDuff.Mode.SRC_ATOP);
    }

    private void initToolbar() {
        getWindow().setStatusBarColor(Utils.getColor(mContext));
        mToolbar.setBackgroundColor(Utils.getColor(mContext));
        mToolbar.setTitle("登录");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void onLogin(LoginData loginData) {
        if (loginData != null) {
            if (loginData.getErrorCode() == 0) {
                Event event = new Event();
                event.target = Event.TARGET_MAIN;
                event.type = Event.TYPE_LOGIN;
                EventBus.getDefault().post(event);

                finish();
            } else {
                ToastUtils.showShort(loginData.getErrorMsg());
            }
        }
    }

    public void login() {
        if (TextUtils.isEmpty(mUsername.getText()) || TextUtils.isEmpty(mPassword.getText())) {
            ToastUtils.showShort(mContext.getString(R.string.complete_info));
            return;
        }
        mUserNameText = mUsername.getText().toString();
        mPassWordText = mPassword.getText().toString();
        mPresenter.login(mUserNameText, mPassWordText);
    }

    public void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
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

    public void onLogin(View view) {
        login();

    }
    public void goRegister(View view) {
        register();
    }
}
