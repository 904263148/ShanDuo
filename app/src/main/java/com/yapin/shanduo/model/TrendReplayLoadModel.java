package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * 作者：L on 2018/5/18 0018 15:05
 */
public interface TrendReplayLoadModel {

    void load(OnLoadListener<String> listener , String dynamicId , String comment , String typeId , String ommentId , String replyCommentId);
}
