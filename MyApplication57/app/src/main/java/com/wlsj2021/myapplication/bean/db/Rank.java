package com.wlsj2021.myapplication.bean.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/13
 * Time: 10:22
 */
public class Rank extends LitePalSupport {
    public int id;
    public int coinCount;
    public int level;
    public int rank;
    public int userId;
    public String username;
}
