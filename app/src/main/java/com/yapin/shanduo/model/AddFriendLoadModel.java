package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * 作者：L on 2018/5/29 0029 16:22
 */
public interface AddFriendLoadModel {

    void load(OnLoadListener<String> listener , String attention);
}
