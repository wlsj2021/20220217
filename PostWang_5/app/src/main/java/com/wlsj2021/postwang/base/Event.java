package com.wlsj2021.postwang.base;

/**
 * Created with Android Studio.
 * Description: EventBus
 *
 * @author: wlsj
 * @date: 2019/12/31
 * Time: 23:30
 */
public class Event {
    /**
     * 目标界面-闪屏页
     */
    public static final int TARGET_SPLASH = 0;
    /**
     * 目标界面-MainActivity
     */
    public static final int TARGET_MAIN = 1;
    /**
     * 更换主题颜色
     */
    public static final int TYPE_REFRESH_COLOR = 2;


    /**
     * 登录成功
     */
    public static final int TYPE_LOGIN = 3;
    /**
     * 切换夜间模式
     */
    public static final int TYPE_CHANGE_DAY_NIGHT_MODE = 4;
    /**
     * 开始动画
     */
    public static final int TYPE_START_ANIMATION = 5;

    /**
     * 停止动画
     */
    public static final int TYPE_STOP_ANIMATION = 6;



    /**
     * 目标界面-首页
     */
    public static final int TARGET_HOME = 7;

    /**
     * 收藏
     */
    public static final int TYPE_COLLECT = 8;
    /**
     * 取消收藏
     */
    public static final int TYPE_UNCOLLECT = 9;

    /**
     * 退出登录
     */
    public static final int TYPE_LOGOUT = 10;

    /**
     * 取消收藏刷新列表
     */
    public static final int TYPE_COLLECT_STATE_REFRESH = 11;
    public int target;

    public int type;

    public String data;
}
