package com.wlsj2021.myapplication.contract.squaresharearticle;

import com.wlsj2021.myapplication.base.interfaces.IBaseView;
import com.wlsj2021.myapplication.bean.db.Article;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/19
 * Time: 20:01
 */
public class Contract {

    public interface IShareModel {
        Observable<Article> addArticle(String title, String link);
    }

    public interface IShareView extends IBaseView {
        void onAddArticle(Article addArticle);
    }

    public interface ISharePresenter {
        void addArticle(String title, String link);
    }
}
