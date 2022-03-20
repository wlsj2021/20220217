package com.wlsj2021.postwang.base;

import com.wlsj2021.postwang.model.RegisterData;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServer {
    @POST(Constant.REGISTER_URL)
    Observable<RegisterData> register(@Query("username") String userName, @Query("password") String password, @Query("repassword") String repassword);
}
