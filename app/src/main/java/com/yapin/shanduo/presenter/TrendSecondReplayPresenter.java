package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.TrendSecondReplayLoadModel;
import com.yapin.shanduo.model.entity.SecondComment;
import com.yapin.shanduo.model.impl.TrendSecondReplayModelImpl;
import com.yapin.shanduo.ui.contract.TrendSecondReplayContract;

import java.util.List;

/**
 * 作者：L on 2018/5/19 0019 10:15
 */
public class TrendSecondReplayPresenter implements TrendSecondReplayContract.Presenter{

    private TrendSecondReplayContract.View view;
    private TrendSecondReplayLoadModel loadModel;

    public void init(TrendSecondReplayContract.View view){
        this.view = view;
        loadModel = new TrendSecondReplayModelImpl();
        view.initView();
    }

    @Override
    public void getData(String commentId  , String typeId , String page, String pageSize) {

        loadModel.load(new OnMultiLoadListener<List<SecondComment.Comments>>() {
            @Override
            public void onSuccess(List<SecondComment.Comments> success, int totalPage) {
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
        } , commentId  ,typeId , page , pageSize);

    }
}
