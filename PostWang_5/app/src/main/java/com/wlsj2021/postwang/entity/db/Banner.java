package com.wlsj2021.postwang.entity.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created with Android Studio.
 * Description: Banner持久化数据
 *
 * @author: wlsj
 * @date: 2020/01/05
 * Time: 21:50
 */
public class Banner extends LitePalSupport {

    public int id;

    public String title;

    public String imagePath;

    public String url;

}
