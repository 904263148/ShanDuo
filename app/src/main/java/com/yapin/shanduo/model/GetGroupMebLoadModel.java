package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.IMGroupUserInfo;
import com.yapin.shanduo.presenter.OnMultiLoadListener;

import java.util.List;

/**
 * 作者：L on 2018/6/19 0019 10:22
 */
public interface GetGroupMebLoadModel {

    void load(OnMultiLoadListener<List<IMGroupUserInfo.GroupMebInfo>> listener , String groupId , String page);

}
