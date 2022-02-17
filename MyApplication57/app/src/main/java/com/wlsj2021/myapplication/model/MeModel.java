package com.wlsj2021.myapplication.model;

import com.wlsj2021.myapplication.base.model.BaseModel;
import com.wlsj2021.myapplication.bean.me.IntegralData;
import com.wlsj2021.myapplication.contract.me.Contract;

import io.reactivex.Observable;


/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2019/12/31
 * Time: 14:19
 */
public class MeModel extends BaseModel implements Contract.IMeModel {
    public MeModel() {
        setCookies(false);
    }

    @Override
    public Observable<IntegralData> loadIntegralData() {
        return mApiServer.loadIntegralData();
    }

    @Override
    public Observable<IntegralData> refreshIntegralData() {
        return mApiServer.loadIntegralData();
    }


}
