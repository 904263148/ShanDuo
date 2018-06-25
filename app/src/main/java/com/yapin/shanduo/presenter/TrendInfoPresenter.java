package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.TrendFirstReplayLoadModel;
import com.yapin.shanduo.model.entity.CommentInfo;
import com.yapin.shanduo.model.impl.TrendFirstFirstReplayModelImpl;
import com.yapin.shanduo.ui.contract.TrendInfoContract;

import java.util.List;

/**
 * 作者：L on 2018/5/18 0018 09:52
 */
public class TrendInfoPresenter implements TrendInfoContract.Presenter{

    private TrendInfoContract.View view;
    private TrendFirstReplayLoadModel loadModel;

    public void init(TrendInfoContract.View view){
        this.view = view;
        loadModel = new TrendFirstFirstReplayModelImpl();
        view.initView();
    }

    @Override
    public void getData(String dynamicId  , String typeId , String page, String pageSize) {
        view.loading();
        loadModel.load(new OnMultiLoadListener<List<CommentInfo.Comment>>() {
            @Override
            public void onSuccess(List<CommentInfo.Comment> success, int totalPage) {
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
        } , dynamicId  ,typeId , page , pageSize);

    }
}
