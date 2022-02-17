package com.wlsj2021.myapplication.model;

import com.wlsj2021.myapplication.base.model.BaseModel;
import com.wlsj2021.myapplication.bean.collect.Collect;
import com.wlsj2021.myapplication.contract.webview.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/02/01
 * Time: 16:15
 */
public class WebViewModel extends BaseModel implements Contract.IWebViewModel {
    public WebViewModel(){
        setCookies(false);
    }
    @Override
    public Observable<Collect> collect(int articleId) {
        return mApiServer.onCollect(articleId);
    }

    @Override
    public Observable<Collect> unCollect(int articleId) {
        return mApiServer.unCollect(articleId);
    }
}
