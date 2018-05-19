package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.CommentInfo;
import com.yapin.shanduo.model.entity.SecondComment;
import com.yapin.shanduo.presenter.OnMultiLoadListener;

import java.util.List;

/**
 * 作者：L on 2018/5/19 0019 10:08
 */
public interface TrendSecondReplayLoadModel {
    void load(OnMultiLoadListener<List<SecondComment.Comments>> listener , String commentId , String typeId , String page , String pageSize);
}
