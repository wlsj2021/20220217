package com.wlsj2021.myapplication.model;

import com.wlsj2021.myapplication.base.model.BaseModel;
import com.wlsj2021.myapplication.bean.square.NavigationData;
import com.wlsj2021.myapplication.contract.square.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2019/12/29
 * Time: 14:56
 */
public class SquareModel extends BaseModel implements Contract.ISquareModel {

    public SquareModel() {
        setCookies(false);
    }
    @Override
    public Observable<NavigationData> loadNavigation() {
        return mApiServer.loadNavigationData();
    }
}
