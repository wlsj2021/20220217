package com.wlsj2021.myapplication.contract.searchresult;

import com.wlsj2021.myapplication.base.interfaces.IBaseView;
import com.wlsj2021.myapplication.bean.collect.Collect;
import com.wlsj2021.myapplication.bean.db.Article;
import com.wlsj2021.myapplication.bean.searchresult.SearchResultData;
import com.wlsj2021.myapplication.bean.searchwords.SearchWordData;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/27
 * Time: 18:15
 */
public class Contract {
    public interface ISearchResultModel {
        Observable<List<Article>> loadSearchResult(int pageNum, String keyWord);

        Observable<List<Article>> refreshSearchResult(int pageNum, String keyWord);

        Observable<Collect> collect(int articleId);

        Observable<Collect> unCollect(int articleId);

    }

    public interface ISearchResultView extends IBaseView {
        void onLoadSearchResult(List<Article> searchWordData);

        void onRefreshSearchResult(List<Article> searchWordData);

        void onCollect(Collect collect, int articleId);

        void onUnCollect(Collect collect, int articleId);
    }

    public interface ISearchResultPresenter {
        void loadSearchResult(int pageNum, String keyWord);

        void refreshSearchResult(int pageNum, String keyWord);

        void collect(int articleId);

        void unCollect(int articleId);
    }
}
