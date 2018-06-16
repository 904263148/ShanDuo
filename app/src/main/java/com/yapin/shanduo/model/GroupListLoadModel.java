package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.IMGroupInfo;
import com.yapin.shanduo.presenter.OnLoadListener;

import java.util.List;

/**
 * 作者：L on 2018/6/16 0016 11:42
 */
public interface GroupListLoadModel {

    void load(OnLoadListener<List<IMGroupInfo.GroupInfo>> listener);
}
