package com.wlsj2021.myapplication.model;

import com.blankj.utilcode.util.NetworkUtils;
import com.wlsj2021.myapplication.base.model.BaseModel;
import com.wlsj2021.myapplication.base.utils.Constant;
import com.wlsj2021.myapplication.bean.db.Rank;
import com.wlsj2021.myapplication.contract.rank.Contract;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/13
 * Time: 10:35
 */
public class RankModel extends BaseModel implements Contract.IRankModel {
    public RankModel() {
        setCookies(false);
    }

    @Override
    public Observable<List<Rank>> loadRankData(int pageNum) {
        Observable<List<Rank>> loadFromLocal = Observable.create(emitter -> {
            List<Rank> rankList = LitePal
                    .offset((pageNum - 1) * Constant.PAGE_SIZE)
                    .limit(Constant.PAGE_SIZE)
                    .find(Rank.class);
            emitter.onNext(rankList);
            emitter.onComplete();
        });
        if (NetworkUtils.isConnected()) {
            Observable<List<Rank>> loadFromNet = loadRankDataFromNet(pageNum);
            return Observable.concat(loadFromLocal, loadFromNet);
        }
        return loadFromLocal;
    }


    private Observable<List<Rank>> loadRankDataFromNet(int pageNum) {
        return mApiServer.loadRankData(pageNum).filter(rankData -> rankData.getErrorCode() == Constant.SUCCESS)
                .map(rankData -> {
                    List<Rank> rankList = new ArrayList<>();
                    rankData.getData().getDatas().stream().forEach(datasBean -> {
                        Rank rank = new Rank();
                        rank.userId = datasBean.getUserId();
                        rank.coinCount = datasBean.getCoinCount();
                        rank.username = datasBean.getUsername();
                        rank.level = datasBean.getLevel();
                        rank.rank = datasBean.getRank();
                        rankList.add(rank);
                    });
                    return rankList;
                });
    }

    @Override
    public Observable<List<Rank>> refreshRankData(int pageNum) {
        return loadRankDataFromNet(pageNum);
    }
}
