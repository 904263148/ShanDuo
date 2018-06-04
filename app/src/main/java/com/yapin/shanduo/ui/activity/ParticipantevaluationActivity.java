package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.model.entity.ActivityevaluationInfo;
import com.yapin.shanduo.model.entity.JoinActUser;
import com.yapin.shanduo.presenter.InitiatorevaluationPresenter;
import com.yapin.shanduo.presenter.JoinActUserPresenter;
import com.yapin.shanduo.presenter.ParticipantevaluationPresenter;
import com.yapin.shanduo.ui.adapter.ParticipantevaluationAdapter;
import com.yapin.shanduo.ui.contract.JoinActUserContract;
import com.yapin.shanduo.ui.contract.ParticipantevaluationContract;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.widget.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/28.
 */

public class ParticipantevaluationActivity extends BaseActivity implements ParticipantevaluationContract.View ,JoinActUserContract.View {

    private ParticipantevaluationPresenter presenter;
    private JoinActUserPresenter joinActUserPresenter;
    private Context context;
    private Activity activity;

    String activityId;
    String score;
    String evaluationcontent;
    String name;
    String theme;
    String headportrait;
    String Numberofpeople;
    String mode;

    private int isJoin = 0 ;

    private ParticipantevaluationAdapter adapter;

    @BindView(R.id.tv_theme)
    TextView tv_theme;
    @BindView(R.id.tv_Numberofpeople)
    TextView tv_Numberofpeople;
    @BindView(R.id.tv_mode)
    TextView tv_mode;
    @BindView(R.id.list_my_view)
    ListView list_my_view;
    private List<JoinActUser.ActUser> joinlist = new ArrayList<>();
    private ActivityInfo.Act act = new ActivityInfo.Act();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_evaluation);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);

        presenter = new ParticipantevaluationPresenter();
        presenter.init(context ,this);
        joinActUserPresenter = new JoinActUserPresenter();
        joinActUserPresenter.init(this);

        Bundle bundle = this.getIntent().getExtras();
        activityId = bundle.getString("id");
        name = bundle.getString("userName");
        theme = bundle.getString("activityName");
        headportrait = bundle.getString("headPortraitId");
//        Numberofpeople = bundle.getString("Numberofpeople");
        mode = bundle.getString("mode");
        tv_theme.setText("主题："+theme);

//        tv_Numberofpeople.setText(joinlist.size());
        tv_mode.setText(mode);


        joinActUserPresenter.getData(activityId , "1" , "10");
    }

    @OnClick({R.id.bt_Submission ,R.id.tv_finish})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_Submission:

//                Map<String, Object> data = new HashMap<>();

//                data.put("data", adapter.getData());

                String dataResult = JSON.toJSONString(adapter.getData());

                Log.e(ParticipantevaluationActivity.class.getName(), "onClick: " + dataResult);

//                Log.i("dateadapter", adapter.getData().toString());

                presenter.initiat(activityId , dataResult);
                break;
            case R.id.tv_finish:
                finish();
                break;
        }
    }



    @Override
    public void initView() {

    }

    @Override
    public void success(String data) {
        ToastUtil.showShortToast(context,"评价成功");
        finish();
    }

    @Override
    public void show(List<JoinActUser.ActUser> data, int totalPage) {
        if(totalPage == 0){

            return;
        }else {
//            loadingView.setGone();
        }
        joinlist.clear();
        joinlist.addAll(data);
//        adapter.notifyDataSetChanged();
        refreshData();
        isJoin = joinlist.get(0).getJoinActivity();
        if (data.size() > 0){
            tv_Numberofpeople.setText("参与者" + (data.size()) + "人");
        }else {
            tv_Numberofpeople.setText("还没有人参加哦！");
        }

    }

    public void refreshData(){
        adapter = new ParticipantevaluationAdapter(context ,activity , joinlist);
        list_my_view.setAdapter(adapter);
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {
        ToastUtil.showShortToast(context,"网络连接异常");
    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortToast(context,msg);
    }

    @Override
    public void showFailed(String msg) {

    }


}
