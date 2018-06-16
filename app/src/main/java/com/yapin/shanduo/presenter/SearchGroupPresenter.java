package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.GroupListLoadModel;
import com.yapin.shanduo.model.SearchGroupLoadModel;
import com.yapin.shanduo.model.entity.IMGroupInfo;
import com.yapin.shanduo.model.impl.GroupListModelImpl;
import com.yapin.shanduo.model.impl.SearchGroupModelImpl;
import com.yapin.shanduo.ui.contract.GroupListContract;
import com.yapin.shanduo.ui.contract.SearchGroupContract;

import java.util.List;

/**
 * 作者：L on 2018/6/16 0016 11:57
 */
public class SearchGroupPresenter implements SearchGroupContract.Presenter{

    private SearchGroupContract.View view;
    private SearchGroupLoadModel loadModel;

    public void init(SearchGroupContract.View view){
        this.view = view;
        loadModel = new SearchGroupModelImpl();
        view.initView();
    }

    @Override
    public void getData(String name) {
        loadModel.load(new OnLoadListener<List<IMGroupInfo.GroupInfo>>() {
            @Override
            public void onSuccess(List<IMGroupInfo.GroupInfo> success) {
                view.show(success);
            }

            @Override
            public void onError(String msg) {
                view.error(msg);
            }

            @Override
            public void networkError() {
                view.networkError();
            }
        } , name);
    }
}
