package com.wlsj2021.myapplication.dagger2;

import com.wlsj2021.myapplication.MainActivity;

import dagger.Component;

@Component(modules = RegisterModule.class)
public interface RegisterComponent {
    void inject(MainActivity mainActivity);
}
