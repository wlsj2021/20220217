package com.wlsj2021.postwang.base;

import com.wlsj2021.postwang.model.LoginData;
import com.wlsj2021.postwang.model.LogoutData;
import com.wlsj2021.postwang.model.RegisterData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiServer {
    @POST(Constant.REGISTER_URL)
    Observable<RegisterData> register(@Query("username") String userName, @Query("password") String password, @Query("repassword") String repassword);

    /**
     * 登出
     *
     * @return
     */
    @GET(Constant.LOGINOUT_URL)
    Observable<LogoutData> logout();
    /**
     * 登陆数据
     *
     * @param userName
     * @param passWord
     * @return
     */
    @POST(Constant.LOGIN_URL)
    Observable<LoginData> login(@Query("username") String userName, @Query("password") String passWord);




}
