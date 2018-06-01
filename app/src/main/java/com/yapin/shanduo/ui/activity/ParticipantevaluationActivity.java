package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.presenter.InitiatorevaluationPresenter;
import com.yapin.shanduo.presenter.ParticipantevaluationPresenter;
import com.yapin.shanduo.ui.contract.ParticipantevaluationContract;
import com.yapin.shanduo.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dell on 2018/5/28.
 */

public class ParticipantevaluationActivity extends BaseActivity implements ParticipantevaluationContract.View{

    private ParticipantevaluationPresenter presenter;
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

    @BindView(R.id.tv_theme)
    TextView tv_theme;
    @BindView(R.id.tv_Numberofpeople)
    TextView tv_Numberofpeople;
    @BindView(R.id.tv_mode)
    TextView tv_mode;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_evaluation);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);

        presenter = new ParticipantevaluationPresenter();
        presenter.init(context ,this);

        Bundle bundle = this.getIntent().getExtras();
        activityId = bundle.getString("id");
        name = bundle.getString("userName");
        theme = bundle.getString("activityName");
        headportrait = bundle.getString("headPortraitId");
        Numberofpeople = bundle.getString("Numberofpeople");
        mode = bundle.getString("mode");
        tv_theme.setText("主题："+theme);
        tv_Numberofpeople.setText("参与者"+Numberofpeople+"人");
        tv_mode.setText(mode);
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
