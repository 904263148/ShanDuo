package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * Created by dell on 2018/5/29.
 */

public interface InitiatorevaluationModel {
    void load(OnLoadListener<String> listener, String score, String evaluationcontent ,String activityId);
}
