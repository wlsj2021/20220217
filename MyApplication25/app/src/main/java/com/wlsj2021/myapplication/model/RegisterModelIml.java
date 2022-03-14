package com.wlsj2021.myapplication.model;
//3
import android.util.Log;

import com.trello.rxlifecycle.ActivityEvent;
import com.wlsj2021.myapplication.common.MyActivity;
import com.wlsj2021.myapplication.listener.OnRegisterListener;
import com.wlsj2021.myapplication.net.RegisterService;
import com.wlsj2021.myapplication.view.RegisterView;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterModelIml implements RegisterModel{
    private RegisterView mRegisterView;


    //数据的请求和数据的处理 判断参数的相关操作
    @Override
    public void rigister(String usn, String psw, String rePsw, OnRegisterListener mOnRegisterListener) {


        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

//RXBUS RXORM
//Retrofit turns your HTTP API into a Java interface.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
//The Retrofit class generates an implementation of the GitHubService interface.
        RegisterService registerService = retrofit.create(RegisterService.class);






        registerService.myRegisterCall4("SAD123456","123456789","123456789")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                //3RXJava内存优化
                .compose(mRegisterView.<Entity>bindToLifecycle())
                .subscribe(new Subscriber<Entity>() {
                    @Override
                    public void onCompleted() {
                        Log.e("TAG", "onCompleted: ",null );

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Entity entity) {
                        Log.e("TAG", "onNext: "+entity.getData().getToken(),null );
                    }
                });


    }






        //带着参数
    }

