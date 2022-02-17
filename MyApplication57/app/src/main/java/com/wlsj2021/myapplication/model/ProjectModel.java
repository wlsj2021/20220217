package com.wlsj2021.myapplication.model;

import com.blankj.utilcode.util.NetworkUtils;
import com.wlsj2021.myapplication.base.model.BaseModel;
import com.wlsj2021.myapplication.base.utils.Constant;
import com.wlsj2021.myapplication.bean.db.ProjectClassify;
import com.wlsj2021.myapplication.contract.project.Contract;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2019/12/27
 * Time: 15:20
 */
public class ProjectModel extends BaseModel implements Contract.IProjectModel {

    public ProjectModel() {
        setCookies(false);
    }
    @Override
    public Observable<List<ProjectClassify>> loadProjectClassify() {
        Observable<List<ProjectClassify>> loadFromLocal = Observable.create(emitter -> {
                List<ProjectClassify> projectClassifyList = LitePal.findAll(ProjectClassify.class);
                emitter.onNext(projectClassifyList);
                emitter.onComplete();
        });
        if (NetworkUtils.isConnected()) {
            Observable<List<ProjectClassify>> loadFromNet = loadProjectClassifyFromNet();
            return Observable.concat(loadFromLocal, loadFromNet);
        } else {
            return loadFromLocal;
        }
    }

    private Observable<List<ProjectClassify>> loadProjectClassifyFromNet() {
        return mApiServer.loadProjectClassify().filter(p -> p.getErrorCode() == Constant.SUCCESS)
                .map(p -> {
                    List<ProjectClassify> projectClassifyList = new ArrayList<>();
                    p.getData().stream().forEach(dataBean -> {
                        ProjectClassify projectClassifyDB = new ProjectClassify();
                        projectClassifyDB.categoryId = dataBean.getId();
                        projectClassifyDB.name = dataBean.getName();
                        projectClassifyList.add(projectClassifyDB);
                    });
                    if (projectClassifyList.size() > 0) {
                        LitePal.deleteAll(ProjectClassify.class);
                    }
                    LitePal.saveAll(projectClassifyList);
                    return projectClassifyList;
        });
    }

    @Override
    public Observable<List<ProjectClassify>> refreshProjectClassify() {
        return loadProjectClassifyFromNet();
    }
}
