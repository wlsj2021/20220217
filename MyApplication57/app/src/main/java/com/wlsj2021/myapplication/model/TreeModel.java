package com.wlsj2021.myapplication.model;

import com.wlsj2021.myapplication.base.model.BaseModel;
import com.wlsj2021.myapplication.bean.square.TreeData;
import com.wlsj2021.myapplication.contract.square.Contract;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/01/08
 * Time: 11:11
 */
public class TreeModel extends BaseModel implements Contract.ITreeModel {
    public TreeModel() {
        setCookies(false);
    }
    @Override
    public Observable<TreeData> loadTreeData() {
        return mApiServer.loadTreeData();
    }
}
