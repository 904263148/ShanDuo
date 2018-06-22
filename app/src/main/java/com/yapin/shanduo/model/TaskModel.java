package com.yapin.shanduo.model;

import com.yapin.shanduo.model.entity.TaskInfo;
import com.yapin.shanduo.presenter.OnLoadListener;

/**
 * Created by dell on 2018/6/21.
 */

public interface TaskModel {
    void load(OnLoadListener<TaskInfo> listener);
}
