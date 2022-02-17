package com.wlsj2021.myapplication.model;

import com.wlsj2021.myapplication.base.utils.ApiServer;
import com.wlsj2021.myapplication.base.utils.Constant;
import com.wlsj2021.myapplication.bean.searchwords.SearchWordData;
import com.wlsj2021.myapplication.contract.searchwords.Contract;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2019/12/29
 * Time: 18:37
 */
public class SearchWordModel implements Contract.ISearchModel {
    @Override
    public Observable<SearchWordData> loadSearchWordData() {
        return getApiServer().loadSearchWordData();
    }

    /**
     * 获取请求对象
     * @return 当前的请求对象
     */
    private ApiServer getApiServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiServer apiServer = retrofit.create(ApiServer.class);
        return apiServer;
    }

}
