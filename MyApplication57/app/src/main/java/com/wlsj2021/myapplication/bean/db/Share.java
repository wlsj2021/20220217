package com.wlsj2021.myapplication.bean.db;

import org.litepal.crud.LitePalSupport;

/**
 * Created with Android Studio.
 * Description: 分享的文章数据
 *
 * @author: wlsj
 * @date: 2020/01/19
 * Time: 22:18
 */
public class Share extends LitePalSupport {

    public int id;

    public int articleId;

    public int originId;

    public String envelopePic;

    public String author;

    public String title;

    public String category;

    public long time;

    public String link;

    public String chapterName;

    public boolean isFresh;

    public boolean isCollect;

    public String niceDate;
}
