package com.wlsj2021.myapplication.dagger2;

import com.wlsj2021.myapplication.presenter.Presenter;

import dagger.Module;
import dagger.Provides;


//module注解
@Module
public class RegisterModule {
    //实例化对象的注解
@Provides
    Presenter mPresenter(){
      return  new Presenter();
    }

}
