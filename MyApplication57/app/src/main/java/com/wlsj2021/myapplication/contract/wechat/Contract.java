package com.wlsj2021.myapplication.contract.wechat;

import com.wlsj2021.myapplication.base.interfaces.IBaseView;
import com.wlsj2021.myapplication.bean.collect.Collect;
import com.wlsj2021.myapplication.bean.db.Article;
import com.wlsj2021.myapplication.bean.db.Author;


import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description: 公众号契约类
 *
 * @author: wlsj
 * @date: 2019/12/29
 * Time: 12:01
 */
public class Contract {
    /**
     * WeChatTab
     */
    public interface IWeChatModel{
        /**
         * 获取公众号Tab数据
         * @return banner数据
         */
        Observable<List<Author>> loadWeChatClassify();

        /**
         * 刷新公众号Tab数据
         * @return
         */
        Observable<List<Author>> refreshWeChatClassify();
    }

    public interface IWeChatView extends IBaseView {
        /**
         * 获取公众号Tab据进行显示
         * @param weChatClassifyData
         */
        void onLoadWeChatClassify(List<Author> weChatClassifyData);

        /**
         * 刷新微信公众号Tab数据的显示
         * @param weChatClassifyData
         */
        void onRefreshWeChatClassify(List<Author> weChatClassifyData);
    }

    public interface IWeChatPresenter{
        /**
         * 公众号Tab
         */
        void loadWeChatClassify();

        void refreshWeChatClassify();

    }

    /**
     * ProjectList
     */

    public interface IWeChatListModel{
        /**
         * 获取项目数据
         * @return 文章数据
         */
        Observable<List<Article>> loadWeChatList(int cid, int pageNum);

        /**
         * 刷新项目列表
         * @return
         */
        Observable<List<Article>> refreshWeChatList(int cid, int pageNum);

        /**
         * 收藏
         * @param articleId
         * @return
         */
        Observable<Collect> collect(int articleId);

        /**
         * 取消收藏
         * @param articleId
         * @return
         */
        Observable<Collect> unCollect(int articleId);
    }

    public interface IWeChatListView extends IBaseView {

        /**
         * 获取项目数据进行显示
         * @param weChatList
         */
        void onLoadWeChatList(List<Article> weChatList);

        /**
         * 刷新项目列表
         * @param weChatList
         */
        void onRefreshWeChatList(List<Article> weChatList);

        void onCollect(Collect collect, int articleId);

        void onUnCollect(Collect collect, int articleId);

    }

    public interface IWeChatListPresenter{

        /**
         * 项目列表
         */
        void loadWeChatList(int cid, int pageNum);

        /**
         * 刷新项目列表
         */
        void refreshWeChatList(int cid, int pageNum);

        void collect(int articleId);

        void unCollect(int articleId);
    }
}
