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
     * 更换主题颜色
     */
    public static final int TYPE_REFRESH_COLOR = 9;

    /**
     * 目标界面-闪屏页
     */
    public static final int TARGET_SPLASH = 13;


    public int target;

    public int type;

    public String data;
}
