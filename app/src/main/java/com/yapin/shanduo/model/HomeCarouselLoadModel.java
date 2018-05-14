package com.yapin.shanduo.model;

import com.yapin.shanduo.presenter.OnLoadListener;

import java.util.List;

/**
 * 作者：L on 2018/5/12 0012 17:25
 */
public interface HomeCarouselLoadModel {

    void load(OnLoadListener<List<String>> listener);
}
