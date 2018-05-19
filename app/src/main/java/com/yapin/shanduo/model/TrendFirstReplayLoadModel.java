package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.CommentInfo;
import com.yapin.shanduo.presenter.OnMultiLoadListener;

import java.util.List;

/**
 * 作者：L on 2018/5/17 0017 17:59
 */
public interface TrendFirstReplayLoadModel {

    void load(OnMultiLoadListener<List<CommentInfo.Comment>> listener , String dynamicId , String typeId , String page , String pageSize);

}
