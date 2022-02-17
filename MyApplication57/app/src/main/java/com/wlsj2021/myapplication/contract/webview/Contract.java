package com.wlsj2021.myapplication.contract.webview;

import com.wlsj2021.myapplication.base.interfaces.IBaseView;
import com.wlsj2021.myapplication.bean.collect.Collect;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/02/01
 * Time: 16:11
 */
public class Contract {

    public interface IWebViewModel {
        Observable<Collect> collect(int articleId);

        Observable<Collect> unCollect(int articleId);
    }

    public interface IWebView extends IBaseView {
        void onCollect(Collect collect);

        void onUnCollect(Collect collect);
    }

    public interface IWebViewPresenter {
        void collect(int articleId);

        void unCollect(int articleId);
    }
}
