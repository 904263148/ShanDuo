package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.IMGroupInfo;
import com.yapin.shanduo.presenter.OnLoadListener;

import java.util.List;

/**
 * 作者：L on 2018/6/16 0016 17:20
 */
public interface SearchGroupLoadModel {

    void load(OnLoadListener<List<IMGroupInfo.GroupInfo>> listener , String name);
}
