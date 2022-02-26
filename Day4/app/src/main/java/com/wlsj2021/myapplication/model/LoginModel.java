package com.wlsj2021.myapplication.model;

import com.wlsj2021.myapplication.base.model.BaseModel;
import com.wlsj2021.myapplication.bean.me.LoginData;
import com.wlsj2021.myapplication.contract.login.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/11
 * Time: 22:16
 */
public class LoginModel extends BaseModel implements Contract.ILoginModel {
    public LoginModel() {
        setCookies(true);
    }

    @Override
    public Observable<LoginData> login(String userName, String passWord) {
        return mApiServer.login(userName, passWord);
    }
}
