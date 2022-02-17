package com.wlsj2021.myapplication.contract.collect;

import com.wlsj2021.myapplication.base.interfaces.IBaseView;
import com.wlsj2021.myapplication.bean.db.Collect;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/01
 * Time: 22:37
 */
public class Contract {
    public interface ICollectModel {
        /**
         * 加载收藏文章
         *
         * @param pageNum
         * @return
         */
        Observable<List<Collect>> loadCollectData(int pageNum);

        /**
         * 刷新收藏文章
         *
         * @param pageNum
         * @return
         */
        Observable<List<Collect>> refreshCollectData(int pageNum);

        /**
         * 添加站外文章
         *
         * @param title
         * @param author
         * @param link
         * @return
         */
        Observable<Collect> addCollect(String title, String author, String link);



        Observable<com.wlsj2021.myapplication.bean.collect.Collect> unCollect(int articleId, int originId);

    }

    public interface ICollectView extends IBaseView {
        void onLoadCollectData(List<Collect> collectList);

        void onRefreshCollectData(List<Collect> collectList);

        void onAddCollect(Collect addCollect);



        void onUnCollect(com.wlsj2021.myapplication.bean.collect.Collect collect, int articleId);
    }

    public interface ICollectPresenter {
        void loadCollectData(int pageNum);

        void refreshCollectData(int pageNum);

        void addCollect(String title, String author, String link);


        void unCollect(int articleId, int originId);
    }
}
