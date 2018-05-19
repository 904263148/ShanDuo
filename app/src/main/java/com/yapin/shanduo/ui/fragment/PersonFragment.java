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

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.ui.activity.AddactivityActivity;
import com.yapin.shanduo.ui.activity.EditingformationAcivity;
import com.yapin.shanduo.ui.activity.LoginActivity;
import com.yapin.shanduo.ui.activity.MyDynamicsActivity;
import com.yapin.shanduo.ui.activity.MyactivitiesActivity;
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

    private final int PUBLISH_ACT_OPEN_LOGIN = 1;
    private final int PUBLISH_MYDYNAMICS_LOGIN = 2;
    private final int MYACTIVITIES =3;
    private final int MYACTIVITIESACTIVITY =4;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_layout,container,false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();
    }

    @OnClick({R.id.tv_MyDynamics,R.id.tv_Myactivities})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_MyDynamics:
//                StartActivityUtil.start(activity , MyDynamicsActivity.class);
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity , LoginActivity.class , PUBLISH_ACT_OPEN_LOGIN);
                }else {
                    StartActivityUtil.start(activity , MyDynamicsActivity.class , PUBLISH_MYDYNAMICS_LOGIN);
                }
                break;

            case R.id.tv_Myactivities:
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity , LoginActivity.class , MYACTIVITIESACTIVITY);
                }else {
                    StartActivityUtil.start(activity , MyactivitiesActivity.class , PUBLISH_MYDYNAMICS_LOGIN);
                }

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
        }
    }
}
