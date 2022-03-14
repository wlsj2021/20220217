package com.wlsj2021.myapplication.net;

import com.wlsj2021.myapplication.model.Entity;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface RegisterService {
//    @Header() 查
@Headers({
        "Accept: application/vnd.github.v3.full+json",
        "User-Agent: Retrofit-Sample-App"
})
    @GET("/{user}/register")
Observable<Entity> myRegisterCall(@Path("user") String user);

    @GET("/{user}/register")
    Call<Entity>myRegisterCall6(@Path("user") String user,@Query("username")String username);

    @GET("group/{id}/users")
    Call<Entity> groupList(@Path("id") int groupId, @QueryMap Map<String, String> options);


    @GET("/user/register")
    Call<Entity>myRegisterCall1();
    @GET("/user/register?username=kkk123456789&password=1234567&repassword=1234567")
    Call<Entity>myRegisterCall5();
    @GET("")
    Call<Entity.DataDTO>myRegisterCall2();
    //删除
//    @DELETE
    @FormUrlEncoded
    @PUT("/user/register")
    Call<Entity>myRegisterCall3(@Field("username")String username,@Field("password")String password,@Field("repassword")String repassword);
    @Headers("")
    @POST("/user/register")
    Observable<Entity>myRegisterCall4(@Query("username") String username, @Query("password")String password, @Query("repassword")String repassword);

    @HTTP(method = "POST", path = "/user/register", hasBody = true)
    Call<Entity>myRegisterCall4();


//    @PUT


}
