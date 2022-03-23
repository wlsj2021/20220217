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

    public int target;

    public int type;

    public String data;
}
