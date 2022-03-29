package com.wlsj2021.postwang.base;

public class Constant {
    /**
     * 域名
     */
    public static final String BASE_URL = "https://www.wanandroid.com/";

    public static final int SUCCESS = 0;

    public static final String REGISTER_URL = "/user/register";

    /**
     * 登录URL
     */
    public static final String LOGIN_URL = "/user/login";

    /**
     * 登出URL
     */
    public static final String LOGINOUT_URL = "user/logout/json";


    /**
     * user-name
     */
    public static final String EXTRA_KEY_USERNAME = "referrer";

    /**
     * user-password
     */
    public static final String EXTRA_VALUE_PASSWORD = "collect";
    public static final long EXIT_TIME = 2000;

    /**
     * 首页Banner的url
     */
    public static final String BANNER_URL = "/banner/json";

    /**
     * 首页文章URL
     */
    public static final String ARTICLE_URL = "/article/list/{pageNum}/json";

    /**
     * 置顶文章数据
     */
    public static final String TOP_ARTICLE_URL = "/article/top/json";



    /**
     * 收藏文章的url
     */
    public static final String COLLECT_URL = "/lg/collect/{id}/json";

    /**
     * 取消收藏文章的url
     */
    public static final String UNCOLLECT_URL = "/lg/uncollect_originId/{id}/json";


    /**
     * 设置文件的保存名称
     */
    public static final String CONFIG_SETTINGS = "settings";

    /**
     * key-night-mode
     */
    public static final String KEY_NIGHT_MODE = "night_mode";

}
