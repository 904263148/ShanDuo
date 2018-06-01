package com.yapin.shanduo.ui.contract;

import com.yapin.shanduo.base.BaseView;
import com.yapin.shanduo.model.entity.CommentInfo;
import com.yapin.shanduo.model.entity.CreditItem;

import java.util.List;

/**
 * 作者：L on 2018/6/1 0001 11:36
 */
public interface CreditDetailContract {

    interface View extends BaseView {

        void show(List<CreditItem> data , int totalPage);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);

    }

    interface Presenter{
        void getData( String userId , String page , String pageSize , String type);
    }
}
