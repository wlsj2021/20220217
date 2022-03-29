package com.wlsj2021.postwang.base;

import com.wlsj2021.postwang.entity.collect.Collect;
import com.wlsj2021.postwang.entity.home.ArticleBean;
import com.wlsj2021.postwang.entity.home.Banner;
import com.wlsj2021.postwang.entity.home.TopArticleBean;
import com.wlsj2021.postwang.model.LoginData;
import com.wlsj2021.postwang.model.LogoutData;
import com.wlsj2021.postwang.model.RegisterData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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

    /**
     * 获取首页Banner
     *
     * @return
     */
    @GET(Constant.BANNER_URL)
    Observable<Banner> loadBanner();

    /**
     * 获取首页文章
     *
     * @return
     */
    @GET(Constant.ARTICLE_URL)
    Observable<ArticleBean> loadArticle(@Path("pageNum") int number);

    /**
     * 获取置顶文章
     *
     * @return
     */
    @GET(Constant.TOP_ARTICLE_URL)
    Observable<TopArticleBean> loadTopArticle();




    /**
     * 点击收藏文章
     *
     * @param id
     * @return
     */
    @POST(Constant.COLLECT_URL)
    Observable<Collect> onCollect(@Path("id") int id);

    /**
     * 点击取消收藏文章
     *
     * @param id
     * @return
     */
    @POST(Constant.UNCOLLECT_URL)
    Observable<Collect> unCollect(@Path("id") int id);



}
