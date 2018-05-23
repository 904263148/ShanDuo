package com.yapin.shanduo.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.ui.activity.AddactivityActivity;
import com.yapin.shanduo.ui.activity.EditingformationAcivity;
import com.yapin.shanduo.ui.activity.LoginActivity;
import com.yapin.shanduo.ui.activity.MyDynamicsActivity;
import com.yapin.shanduo.ui.activity.MyactivitiesActivity;
import com.yapin.shanduo.ui.activity.MywalletActivity;
import com.yapin.shanduo.ui.activity.SetupActivity;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;

import butterknife.BindView;
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

//    @BindView(R.id.ib_Headportrait)
//    ImageButton ib_Headportrait;
//    @BindView(R.id.tv_nickname)
      private TextView tv_nickname;
      private ImageView ib_Headportrait;
      private TextView tv_sex;
      private LinearLayout ll_person_a;


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

        initView();
        return view;
    }

    private void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();

        if(TextUtils.isEmpty(PrefUtil.getToken(context))){

        }else {
            tv_nickname.setText(PrefJsonUtil.getProfile(context).getName());
//            ib_Headportrait.setImageDrawable(Drawable.createFromPath(PrefJsonUtil.getProfile(context).getPicture()));
//            tv_sex.setText(PrefJsonUtil.getProfile(context).getAgeId());
        }

    }

    @OnClick({R.id.tv_MyDynamics,R.id.tv_Myactivities ,R.id.ll_person_a , R.id.text_setup , R.id.text_mywallet})
    public void onClick(View view){
        switch (view.getId()){
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
                StartActivityUtil.start(activity , SetupActivity.class);
                break;

            case R.id.text_mywallet:        //我的钱包
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity , LoginActivity.class , MYWALLET);
                }else {
                    StartActivityUtil.start(activity , MywalletActivity.class , PUBLISH_MYDYNAMICS_LOGIN);
                }
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
        }
    }
}
