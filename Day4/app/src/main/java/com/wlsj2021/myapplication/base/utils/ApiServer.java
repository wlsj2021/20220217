package com.wlsj2021.myapplication.base.utils;



import com.wlsj2021.myapplication.bean.me.LoginData;
import com.wlsj2021.myapplication.bean.me.LogoutData;
import com.wlsj2021.myapplication.bean.me.RegisterData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created with Android Studio.
 * Description: 请求APi
 *
 * @author: wlsj
 * @date: 2019/12/19
 * Time: 15:56
 */
public interface ApiServer {
    /**
     * 登陆数据
     *
     * @param userName
     * @param passWord
     * @return
     */
    @POST(Constant.LOGIN_URL)
    Observable<LoginData> login(@Query("username") String userName, @Query("password") String passWord);

    /**
     * 注册数据
     *
     * @param userName
     * @param password
     * @param repassword
     * @return
     */
    @POST(Constant.REGISTER_URL)
    Observable<RegisterData> register(@Query("username") String userName, @Query("password") String password, @Query("repassword") String repassword);

    /**
     * 登出
     *
     * @return
     */
    @GET(Constant.LOGINOUT_URL)
    Observable<LogoutData> logout();



}
