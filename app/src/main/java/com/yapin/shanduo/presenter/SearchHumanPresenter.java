package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.SearchHumanLoadModel;
import com.yapin.shanduo.model.entity.TokenInfo;
import com.yapin.shanduo.model.impl.SearchHumanModelImpl;
import com.yapin.shanduo.ui.contract.SearchHumanContract;

import java.util.List;

/**
 * 作者：L on 2018/5/24 0024 16:57
 */
public class SearchHumanPresenter implements SearchHumanContract.Presenter{

    private SearchHumanContract.View view;
    private SearchHumanLoadModel loadModel;

    public void init(SearchHumanContract.View view){
        this.view = view;
        loadModel = new SearchHumanModelImpl();
        view.initView();
    }

    @Override
    public void getData(String query, String typeId) {
        loadModel.load(new OnLoadListener<List<TokenInfo>>() {
            @Override
            public void onSuccess(List<TokenInfo> success) {
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
        } , query, typeId);
    }
}
