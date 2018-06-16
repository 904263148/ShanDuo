package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * 作者：L on 2018/6/16 0016 11:28
 */
public interface EditGroupNameLoadModel {

    void load(OnLoadListener<String> listener , String name , String groupId);
}
