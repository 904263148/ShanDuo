package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.FriendInfo;
import com.yapin.shanduo.presenter.OnLoadListener;

import java.util.List;

/**
 * 作者：L on 2018/5/19 0019 15:02
 */
public interface LinkManLoadModel {

    void load(OnLoadListener<List<FriendInfo.fInfo>> listener , String typeId);
}
