package com.wlsj2021.postwang.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;


import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/05
 * Time: 12:32
 */
public class BaseModel {

    protected Retrofit mRetrofit;

    protected ApiServer mApiServer;

    public BaseModel()  {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiServer = mRetrofit.create(ApiServer.class);
    }
}
