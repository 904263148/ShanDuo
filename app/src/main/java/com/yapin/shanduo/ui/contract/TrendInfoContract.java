package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.CommentInfo;

import java.util.List;

/**
 * 作者：L on 2018/5/18 0018 09:50
 */
public interface TrendInfoContract {

    interface View extends BaseView {

        void show(List<CommentInfo.Comment> data , int totalPage);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void getData(String dynamicId  , String typeId , String page , String pageSize);
    }
}
