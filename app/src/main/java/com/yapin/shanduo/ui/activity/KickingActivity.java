package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.CreditItem;
import com.yapin.shanduo.presenter.CreditDetailPresenter;
import com.yapin.shanduo.presenter.JoinActPresenter;
import com.yapin.shanduo.ui.adapter.KickingTabAdapter;
import com.yapin.shanduo.ui.contract.CreditDetailContract;
import com.yapin.shanduo.ui.contract.JoinActContract;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/6/7.
 */

public class KickingActivity extends AppCompatActivity implements CreditDetailContract.View ,JoinActContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.tv_score_des)
    TextView tvScoreDes;
    @BindView(R.id.tv_kicking)
    TextView tv_kicking;
    @BindView(R.id.textview)
    TextView textview;
    @BindView(R.id.ll_layoutkicking)
    LinearLayout ll_layoutkicking;

    private KickingTabAdapter adapter;
    private int page = 1;
    private int pageSize = 10;
    private List<CreditItem> list = new ArrayList<>();
    private CreditDetailPresenter presenter;
    private Context context;
    private Activity activity;
    private String activityId;
    private int positiona;
    private int typeid;

    private JoinActPresenter joinActPresenter;

    private String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        ButterKnife.bind(this);
        presenter = new CreditDetailPresenter();
        presenter.init(this);
        joinActPresenter = new JoinActPresenter();
        joinActPresenter.init(this);
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getExtras();
        activityId = bundle.getString("activityId");
        userId = getIntent().getStringExtra("userId");
        positiona = bundle.getInt("positiona");
        typeid = bundle.getInt("typeid");
        if (positiona == 0){
            textview.setVisibility(View.GONE);
            tv_kicking.setVisibility(View.GONE);
        }else if (positiona == 1){
            textview.setVisibility(View.GONE);
            tv_kicking.setVisibility(View.GONE);
        }
        if (3 == typeid ){
            ll_layoutkicking.setVisibility(View.GONE);
        }else if (2 == typeid ){
            ll_layoutkicking.setVisibility(View.GONE);
        }
//        else if (4 == typeid ){
//            tv_confirm.setText("取消活动");
//        }else if (5 == typeid ){
//            tv_confirm.setText("取消活动");
//        }
//        userId = getIntent().getStringExtra("userId") == null ? "" : getIntent().getStringExtra("userId");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        context = ShanDuoPartyApplication.getContext();
        activity = this;

        List<String> tabList = new ArrayList<>();
        tabList.add("发布");
        tabList.add("参加");

        adapter = new KickingTabAdapter(getSupportFragmentManager(), tabList , userId);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(tabLayout, 60, 60);
            }
        });

        toolbarLayout.setTitle("");

        presenter.getData(userId , page+"" , pageSize+""  , "1");
    }

    public void setData(){
        GlideUtil.load(context , activity , ApiUtil.IMG_URL + list.get(0).getFather_head() , ivHead);
        tvScore.setText(list.get(0).getFather_reputation()+"");

        tvScoreDes.setText(Utils.getCredit(list.get(0).getFather_reputation()));

    }

    @Override
    public void show(List<CreditItem> data, int totalPage) {
        list.clear();
        list = data;
        setData();
    }

    @Override
    public void loading() {

    }

    @OnClick({R.id.tv_kicking})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_kicking:
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(R.string.title_refuse)
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                return;
                            }
                        }).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        joinActPresenter.join(activityId , "3" ,userId);
                    }
                }).create().show();

                break;
        }
    }

    @Override
    public void success(String data) {
        ToastUtil.showShortToast(context,data.toString());
        finish();
    }



    @Override
    public void networkError() {
        ToastUtil.showShortToast(context,"网络连接异常");
    }

    @Override
    public void joinError(String msg) {
        ToastUtil.showShortToast(context,msg);
    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortToast(context,msg);
    }

    @Override
    public void showFailed(String msg) {
        ToastUtil.showShortToast(context,msg);
    }

}