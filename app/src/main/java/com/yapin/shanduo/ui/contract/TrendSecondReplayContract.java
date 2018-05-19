package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.CommentInfo;
import com.yapin.shanduo.model.entity.SecondComment;

import java.util.List;

/**
 * 作者：L on 2018/5/19 0019 10:13
 */
public interface TrendSecondReplayContract {

    interface View extends BaseView {

        void show(List<SecondComment.Comments> data , int totalPage);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void getData(String commentId  , String typeId , String page , String pageSize);
    }

}
