package com.yapin.shanduo.presenter;

import com.yapin.shanduo.model.TrendReplayLoadModel;
import com.yapin.shanduo.model.impl.TrendReplayModelImpl;
import com.yapin.shanduo.ui.contract.TrendReplayContract;

/**
 * 作者：L on 2018/5/18 0018 15:18
 */
public class TrendReplayPresenter implements TrendReplayContract.Presenter{

    private TrendReplayContract.View view;
    private TrendReplayLoadModel loadModel;

    public void init(TrendReplayContract.View view){
        this.view = view;
        loadModel = new TrendReplayModelImpl();
    }

    @Override
    public void getData(String dynamicId, String comment, String typeId, String ommentId, String replyCommentId) {
        loadModel.load(new OnLoadListener<String>() {
            @Override
            public void onSuccess(String success) {
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
        } , dynamicId , comment , typeId , ommentId , replyCommentId);
    }
}
