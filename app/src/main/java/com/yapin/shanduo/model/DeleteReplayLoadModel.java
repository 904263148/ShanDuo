package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * 作者：L on 2018/6/14 0014 11:49
 */
public interface DeleteReplayLoadModel {

    void load(OnLoadListener<String> listener , String commentId);
}
