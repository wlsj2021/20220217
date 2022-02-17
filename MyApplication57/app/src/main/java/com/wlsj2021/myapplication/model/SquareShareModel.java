package com.wlsj2021.myapplication.model;

import com.wlsj2021.myapplication.base.model.BaseModel;
import com.wlsj2021.myapplication.base.utils.Constant;
import com.wlsj2021.myapplication.bean.db.Article;
import com.wlsj2021.myapplication.contract.squaresharearticle.Contract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/19
 * Time: 20:03
 */
public class SquareShareModel extends BaseModel implements Contract.IShareModel {

    public SquareShareModel() {
        setCookies(false);
    }

    @Override
    public Observable<Article> addArticle(String title, String link) {
        return mApiServer.addArticle(title, link)
                .filter(a -> a.getErrorCode() == Constant.SUCCESS)
                .map(a -> {
                    List<Article> shareArticleList = new ArrayList<>();
                    a.getData().getDatas().stream().forEach(datasBean -> {
                        Article article = new Article();
                        article.type = Article.TYPE_SQUARE;
                        article.articleId = datasBean.getId();
                        article.author = datasBean.getAuthor();
                        article.link = datasBean.getAuthor();
                        article.time = datasBean.getPublishTime();
                        article.title = datasBean.getTitle();
                        article.chapterName = datasBean.getChapterName();
                        article.superChapterName = datasBean.getSuperChapterName();
                        shareArticleList.add(article);
                    });
                    return shareArticleList.get(0);
                });
    }
}
