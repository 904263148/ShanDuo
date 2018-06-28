package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.MyMessageInfo;
import com.yapin.shanduo.presenter.OnMultiLoadListener;

import java.util.List;

/**
 * 作者：L on 2018/6/27 0027 17:42
 */
public interface MyMessageLoadModel {

    void load(OnMultiLoadListener<List<MyMessageInfo.Message>> listener , String page , String pageSize);

}
