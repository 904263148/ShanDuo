package com.yapin.shanduo.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.ui.activity.CreditcenterActivity;
import com.yapin.shanduo.ui.activity.InitiatorevaluationActivity;
import com.yapin.shanduo.ui.activity.EditingformationAcivity;
import com.yapin.shanduo.ui.activity.LoginActivity;
import com.yapin.shanduo.ui.activity.MembercenterActivity;
import com.yapin.shanduo.ui.activity.MyDynamicsActivity;
import com.yapin.shanduo.ui.activity.MyactivitiesActivity;
import com.yapin.shanduo.ui.activity.MywalletActivity;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.ui.activity.SetupActivity;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends Fragment {

    private Activity activity;
    private Context context;
    private ShanDuoPartyApplication application;

    private final int PUBLISH_ACT_OPEN_LOGIN = 1;
    private final int PUBLISH_MYDYNAMICS_LOGIN = 2;
    private final int MYACTIVITIES =3;
    private final int MYACTIVITIESACTIVITY =4;
    private final int EDITING=5;
    private final int MYWALLET=6;
    private final int SETUP=7;
    private final int MEMBER_CENTER = 8;

      private TextView tv_nickname;
      private ImageView ib_Headportrait;
      private TextView tv_sex;
      private RelativeLayout ll_person_a;
      private LinearLayout ll_person_aa;
      private TextView tv_login_reg;
      private TextView tv_id;

    public static PersonFragment newInstance() {
        PersonFragment fragment = new PersonFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_layout,container,false);
        ButterKnife.bind(this,view);
        tv_nickname = view.findViewById(R.id.tv_nickname);
        ib_Headportrait = view.findViewById(R.id.ib_Headportrait);
        tv_sex = view.findViewById(R.id.tv_sex);
        ll_person_a = view.findViewById(R.id.ll_person_a);
        ll_person_aa = view.findViewById(R.id.ll_person_aa);
        tv_login_reg = view.findViewById(R.id.tv_login_reg);
        tv_id = view.findViewById(R.id.tv_id);


        initView();
        return view;
    }

    private void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();

        if(TextUtils.isEmpty(PrefUtil.getToken(context))){
            ll_person_a.setVisibility(View.GONE);
            ll_person_aa.setVisibility(View.VISIBLE);
        }else {
            ll_person_aa.setVisibility(View.GONE);
            ll_person_a.setVisibility(View.VISIBLE);
            tv_nickname.setText(PrefJsonUtil.getProfile(context).getName());
            tv_id.setText(PrefJsonUtil.getProfile(context).getUserId());
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , ib_Headportrait);
        }

    }
    @OnClick({R.id.tv_MyDynamics,R.id.tv_Myactivities ,R.id.ll_person_a , R.id.text_setup , R.id.text_mywallet ,R.id.tv_login_reg , R.id.tv_member_center ,R.id.tv_Creditcenter})
    public void onClick(View view){
        switch (view.getId()){

            case R.id.tv_member_center:     //会员中心
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity , LoginActivity.class , MEMBER_CENTER);
                }else {
                    StartActivityUtil.start(activity , MembercenterActivity.class , PUBLISH_MYDYNAMICS_LOGIN);
                }
                break;

            case R.id.tv_MyDynamics:    //我的动态
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity , LoginActivity.class , PUBLISH_ACT_OPEN_LOGIN);
                }else {
                    StartActivityUtil.start(activity , MyDynamicsActivity.class , PUBLISH_MYDYNAMICS_LOGIN);
                }
                break;

            case R.id.tv_Myactivities:      //我的活动
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity , LoginActivity.class , MYACTIVITIESACTIVITY);
                }else {
                    StartActivityUtil.start(activity , MyactivitiesActivity.class , PUBLISH_MYDYNAMICS_LOGIN);
                }
                break;
            case R.id.ll_person_a:      //个人信息
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity , LoginActivity.class , EDITING);
                }else {
                    StartActivityUtil.start(activity , EditingformationAcivity.class , PUBLISH_MYDYNAMICS_LOGIN);
                }
                break;
            case R.id.text_setup:       //设置
                    StartActivityUtil.start(activity , SetupActivity.class , PUBLISH_MYDYNAMICS_LOGIN);
                break;

            case R.id.text_mywallet:        //我的钱包
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity , LoginActivity.class , MYWALLET);
                }else {
                    StartActivityUtil.start(activity , MywalletActivity.class , PUBLISH_MYDYNAMICS_LOGIN);
                }
                break;
            case R.id.tv_login_reg:
                StartActivityUtil.start(activity , LoginActivity.class);
                break;
            case R.id.tv_Creditcenter:    //信用中心
                StartActivityUtil.start(activity , CreditcenterActivity.class);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case PUBLISH_ACT_OPEN_LOGIN:
                StartActivityUtil.start(activity , MyDynamicsActivity.class);
                break;

            case MYACTIVITIESACTIVITY:
                StartActivityUtil.start(activity , MyactivitiesActivity.class);
                break;
            case EDITING:
                StartActivityUtil.start(activity , EditingformationAcivity.class);
                break;
            case MYWALLET:
                StartActivityUtil.start(activity , MywalletActivity.class);
                break;
            case MEMBER_CENTER:
                StartActivityUtil.start(activity , MembercenterActivity.class);
                break;
        }
    }
}
