package com.wlsj2021.postwang.model;



import com.wlsj2021.postwang.base.BaseModel;
import com.wlsj2021.postwang.contract.register.Contract;

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
