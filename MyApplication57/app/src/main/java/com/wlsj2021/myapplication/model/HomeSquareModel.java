package com.wlsj2021.myapplication.model;

import com.wlsj2021.myapplication.base.model.BaseModel;
import com.wlsj2021.myapplication.base.utils.Constant;
import com.wlsj2021.myapplication.bean.collect.Collect;
import com.wlsj2021.myapplication.bean.db.Article;
import com.wlsj2021.myapplication.contract.square.Contract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/09
 * Time: 16:15
 */
public class HomeSquareModel extends BaseModel implements Contract.IHomeSquareModel {
    public HomeSquareModel() {
        setCookies(false);
    }

    @Override
    public Observable<List<Article>> loadHomeSquareData(int pageNum) {
//        Observable<List<Article>> loadFromLocal = Observable.create(emitter -> {
//            List<Article> articleList = LitePal.where("type=?"
//                    , Article.TYPE_SQUARE + "")
//                    .order("time desc")
//                    .offset(pageNum * Constant.PAGE_SIZE)
//                    .limit(Constant.PAGE_SIZE)
//                    .find(Article.class);
//            emitter.onNext(articleList);
//            emitter.onComplete();
//        });
//        if (NetworkUtils.isConnected()) {
        Observable<List<Article>> loadFromNet = loadHomeSquareDataFromNet(pageNum);
//            return Observable.concat(loadFromLocal, loadFromNet);
//        } else {
//            return loadFromLocal;
//        }
        return loadFromNet;
    }

    private Observable<List<Article>> loadHomeSquareDataFromNet(int pageNum) {
        return mApiServer.loadHomeSquareData(pageNum).filter(squareData ->
                squareData.getErrorCode() == Constant.SUCCESS)
                .map(squareData -> {
//                    List<Article> allHomeSquareData = LitePal.where("type=?", Article.TYPE_SQUARE + "")
//                            .find(Article.class);
                    List<Article> squareArticleList = new ArrayList<>();
                    squareData.getData().getDatas().stream().forEach(datasBean -> {
//                        long count = allHomeSquareData.stream().filter(m -> m.articleId == datasBean.getId()).count();
//                        if (count <= 0) {
                        Article article = new Article();
                        article.type = Article.TYPE_SQUARE;
                        article.articleId = datasBean.getId();
                        article.title = datasBean.getTitle();
                        article.author = datasBean.getAuthor();
                        article.chapterName = datasBean.getChapterName();
                        article.superChapterName = datasBean.getSuperChapterName();
                        article.time = datasBean.getPublishTime();
                        article.link = datasBean.getLink();
                        article.collect = datasBean.isCollect();
                        article.niceDate = datasBean.getNiceDate();
                        article.shareUser = datasBean.getShareUser();
                        article.isFresh = datasBean.isFresh();
                        squareArticleList.add(article);
//                        } else {
//                            allHomeSquareData.stream().filter(m -> m.articleId == datasBean.getId()).forEach(m -> {
//                                if (m.niceDate != datasBean.getNiceDate() ||
//                                        m.collect != datasBean.isCollect() || m.isFresh != datasBean.isFresh()) {
//                                    m.articleId = datasBean.getId();
//                                    m.title = datasBean.getTitle();
//                                    m.author = datasBean.getAuthor();
//                                    m.link = datasBean.getLink();
//                                    m.chapterName = datasBean.getChapterName();
//                                    m.superChapterName = datasBean.getSuperChapterName();
//                                    m.collect = datasBean.isCollect();
//                                    m.niceDate = datasBean.getNiceDate();
//                                    m.shareUser = datasBean.getShareUser();
//                                    m.time = datasBean.getPublishTime();
//                                    m.isFresh = datasBean.isFresh();
//                                    if (!m.collect) {
//                                        m.setToDefault("collect");
//                                    }
//                                    m.update(m.id);
//                                }
//                            });
//                        }
                    });
//                    LitePal.saveAll(squareArticleList);
//                    List<Article> articleResult = LitePal.where("type=?", Article.TYPE_SQUARE + "")
//                            .order("time desc")
//                            .offset(pageNum * Constant.PAGE_SIZE)
//                            .limit(Constant.PAGE_SIZE)
//                            .find(Article.class);
                    return squareArticleList;
                });
    }

    @Override
    public Observable<List<Article>> refreshHomeSquareData(int pageNum) {
        return loadHomeSquareDataFromNet(pageNum);
    }

    @Override
    public Observable<Collect> collect(int articleId) {
        return mApiServer.onCollect(articleId);
    }

    @Override
    public Observable<Collect> unCollect(int articleId) {
        return mApiServer.unCollect(articleId);
    }

}
