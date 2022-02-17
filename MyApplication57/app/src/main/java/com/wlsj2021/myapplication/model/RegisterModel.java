package com.wlsj2021.myapplication.model;

import com.wlsj2021.myapplication.base.model.BaseModel;
import com.wlsj2021.myapplication.bean.me.RegisterData;
import com.wlsj2021.myapplication.contract.register.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/26
 * Time: 15:18
 */
public class RegisterModel extends BaseModel implements Contract.IRegisterModel {
    @Override
    public Observable<RegisterData> register(String userName, String passWord) {
        return mApiServer.register(userName, passWord, passWord);
    }
}
