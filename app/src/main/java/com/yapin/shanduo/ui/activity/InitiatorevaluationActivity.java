package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.presenter.InitiatorevaluationPresenter;
import com.yapin.shanduo.ui.contract.InitiatorevaluationContract;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.widget.RatingBarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/26.
 */

public class InitiatorevaluationActivity extends BaseActivity implements InitiatorevaluationContract.View {

    private InitiatorevaluationPresenter presenter;
    private Context context;
    private Activity activity;

    int level=0;
    String activityId;
    String score;
    String evaluationcontent;
    String name;
    String theme;
    String headportrait;

    private List<CheckBox> checkBoxList = new ArrayList<CheckBox>();


    @BindView(R.id.custom_ratingbar)
    RatingBarView custom_ratingbar;
    @BindView(R.id.tv_Number)
    TextView tv_Number;
    @BindView(R.id.checkBox1)
    CheckBox checkBox1;
    @BindView(R.id.checkBox2)
    CheckBox checkBox2;
    @BindView(R.id.checkBox3)
    CheckBox checkBox3;
    @BindView(R.id.checkBox4)
    CheckBox checkBox4;
    @BindView(R.id.et_Inputbox)
    EditText et_Inputbox;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_theme)
    TextView tv_theme;
    @BindView(R.id.iv_Headportrait)
    ImageView iv_Headportrait;

    public InitiatorevaluationActivity() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiator_evaluation);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);

        presenter = new InitiatorevaluationPresenter();
        presenter.init(context,this);

        custom_ratingbar.setOnRatingListener(new RatingBarView.OnRatingListener() {
            @Override
            public void onRating(Object bindObject, int RatingScore) {
                level = RatingScore;
//                Toast.makeText(context, String.valueOf(RatingScore)+"", Toast.LENGTH_SHORT).show();
                score = (String.valueOf(RatingScore));
                if ("0".equals(String.valueOf(RatingScore))){
                    tv_Number.setText("没有评价哦！");
                }else if ("1".equals(String.valueOf(RatingScore))){
                    tv_Number.setText("一星好评！");
                }else if ("2".equals(String.valueOf(RatingScore))){
                    tv_Number.setText("二星好评！");
                }else if ("3".equals(String.valueOf(RatingScore))){
                    tv_Number.setText("三星好评！");
                }else if ("4".equals(String.valueOf(RatingScore))){
                    tv_Number.setText("四星好评！");
                }else if ("5".equals(String.valueOf(RatingScore))){
                    tv_Number.setText("五星好评！");
                }
            }
        });

        Bundle bundle = this.getIntent().getExtras();
        activityId = bundle.getString("id");
        name = bundle.getString("userName");
        theme = bundle.getString("activityName");
        headportrait = bundle.getString("headPortraitId");
        GlideUtil.load(context ,activity , ApiUtil.IMG_URL + headportrait , iv_Headportrait);
        tv_theme.setText("主题："+theme);
        tv_name.setText(name);
//        Log.i("idaaaa", activityId+"");



    }

    @OnClick({R.id.iv_finish , R.id.but_evaluate})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_finish:
                finish();
                break;
            case R.id.but_evaluate:

                checkBoxList.add(checkBox1);
                checkBoxList.add(checkBox2);
                checkBoxList.add(checkBox3);
                checkBoxList.add(checkBox4);

                StringBuffer sb = new StringBuffer();

                for (CheckBox checkbox : checkBoxList) {
                    if (checkbox.isChecked()){
                        sb.append(checkbox.getText().toString() + " ");

                    }
                }
//                if (sb!=null && "".equals(sb.toString())){
//                    Toast.makeText(getApplicationContext(), "请至少选择一个", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(getApplicationContext(), sb.toString(), Toast.LENGTH_SHORT).show();
//                }
                evaluationcontent = et_Inputbox.getText().toString().trim()+sb.toString();

//                Log.i("dddadada", beEvaluated.toString()+"");

                presenter.initiat( score , evaluationcontent , activityId);

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
