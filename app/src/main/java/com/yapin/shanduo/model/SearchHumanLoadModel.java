package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.TokenInfo;
import com.yapin.shanduo.presenter.OnLoadListener;
import com.yapin.shanduo.presenter.OnMultiLoadListener;

import java.util.List;

/**
 * 作者：L on 2018/5/24 0024 16:27
 */
public interface SearchHumanLoadModel {

    void load(OnLoadListener<List<TokenInfo>> listener , String query , String typeId);
}
