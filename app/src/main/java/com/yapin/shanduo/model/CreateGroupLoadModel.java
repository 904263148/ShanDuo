package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * 作者：L on 2018/6/1 0001 19:20
 */
public interface CreateGroupLoadModel {

    void load(OnLoadListener<String> listener , String typeId , String groupId , String groupType);
}
