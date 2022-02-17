package com.wlsj2021.myapplication.presenter.searchwords;

import com.wlsj2021.myapplication.base.presenter.BasePresenter;
import com.wlsj2021.myapplication.bean.searchwords.SearchWordData;
import com.wlsj2021.myapplication.contract.searchwords.Contract;
import com.wlsj2021.myapplication.model.SearchWordModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2019/12/29
 * Time: 18:38
 */
public class SearchWordPresenter extends BasePresenter<Contract.ISearchView> implements Contract.ISearchPresenter {
    Contract.ISearchModel iSearchModel;

    public SearchWordPresenter(){
        iSearchModel = new SearchWordModel();
    }
    @Override
    public void loadSearchWordData() {
        iSearchModel.loadSearchWordData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchWordData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(SearchWordData searchWordData) {
                        if (isViewAttached()) {
                            getView().loadSearchWordData(searchWordData);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
