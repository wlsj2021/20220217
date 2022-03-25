package com.wlsj2021.postwang.contract.login;


import com.wlsj2021.postwang.base.IBaseView;
import com.wlsj2021.postwang.model.LoginData;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/11
 * Time: 22:15
 */
public class Contract {
    public interface ILoginModel {
        /**
         * 登录
         * @param userName
         * @param passWord
         * @return
         */
        Observable<LoginData> login(String userName, String passWord);

    }

    public interface ILoginView extends IBaseView {
        void onLogin (LoginData loginData);

    }

    public interface ILoginPresenter {
        void login (String userName, String passWord);
    }
}
