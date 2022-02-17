package com.wlsj2021.myapplication.model;

import com.wlsj2021.myapplication.base.model.BaseModel;
import com.wlsj2021.myapplication.bean.me.LogoutData;
import com.wlsj2021.myapplication.contract.setting.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/16
 * Time: 13:56
 */
public class SettingModel extends BaseModel implements Contract.ILogoutModel {
    public SettingModel() {
        setCookies(true);
    }

    @Override
    public Observable<LogoutData> logout() {
        return mApiServer.logout();
    }
}
