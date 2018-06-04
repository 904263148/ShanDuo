package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.SearchActLoadModel;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.impl.SearchActModelImpl;
import com.yapin.shanduo.ui.contract.SearchActContract;

import java.util.List;

/**
 * 作者：L on 2018/6/4 0004 14:19
 */
public class SearchActPresenter implements SearchActContract.Presenter {

    private SearchActContract.View view;
    private SearchActLoadModel loadModel;

    public void init(SearchActContract.View view){
        this.view = view;
        loadModel = new SearchActModelImpl();
        view.initView();
    }

    @Override
    public void getData(String query, String page , String pageSize) {
        loadModel.load(new OnMultiLoadListener<List<ActivityInfo.Act>>() {
            @Override
            public void onSuccess(List<ActivityInfo.Act> success , int totalPage) {
                view.show(success , totalPage);
            }

            @Override
            public void onError(String msg) {
                view.error(msg);
            }

            @Override
            public void networkError() {
                view.networkError();
            }
        } , query , page , pageSize);
    }
}
