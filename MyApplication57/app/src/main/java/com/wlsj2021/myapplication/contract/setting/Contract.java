package com.wlsj2021.myapplication.contract.setting;

import com.wlsj2021.myapplication.base.interfaces.IBaseView;
import com.wlsj2021.myapplication.bean.me.LogoutData;

import io.reactivex.Observable;


/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/16
 * Time: 13:54
 */
public class Contract {
    public interface ILogoutModel {
        /**
         * 退出登录
         * @return
         */
        Observable<LogoutData> logout();
    }

    public interface ILogoutView extends IBaseView {
        void onLogout(LogoutData logoutData);
    }

    public interface ILogoutPresenter {
        void logout();
    }
}
