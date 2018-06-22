package com.yapin.shanduo.presenter;

import android.content.Context;

import com.yapin.shanduo.model.TaskModel;
import com.yapin.shanduo.model.entity.SigninInfo;
import com.yapin.shanduo.model.entity.TaskInfo;
import com.yapin.shanduo.model.impl.TaskModelImpl;
import com.yapin.shanduo.ui.contract.TaskContract;

/**
 * Created by dell on 2018/6/21.
 */

public class TaskPresenter implements TaskContract.Presenter {

    private  TaskContract.View view;
    private TaskModel taskModel;
    private Context context;

    public void init(Context context, TaskContract.View view) {
        this.view = view;
        view.initView();
        taskModel = new TaskModelImpl();
    }

    @Override
    public void start() {

    }

    @Override
    public void gettask() {
        taskModel.load(new OnLoadListener<TaskInfo>() {
            @Override
            public void onSuccess(TaskInfo success) {
                view.success(success);
            }
            @Override
            public void onError(String msg) {
                view.error(msg);
            }
            @Override
            public void networkError() {
                view.networkError();
            }
        });
    }
}
