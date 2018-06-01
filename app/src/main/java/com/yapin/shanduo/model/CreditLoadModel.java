package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.CreditItem;
import com.yapin.shanduo.presenter.OnMultiLoadListener;

import java.util.List;

/**
 * 作者：L on 2018/6/1 0001 09:34
 */
public interface CreditLoadModel {

    void load(OnMultiLoadListener<List<CreditItem>> listener , String userId , String page , String pageSize , String type);
}
