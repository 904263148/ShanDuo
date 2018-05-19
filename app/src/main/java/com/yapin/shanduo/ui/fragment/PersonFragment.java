package com.yapin.shanduo.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.ui.activity.AddactivityActivity;
import com.yapin.shanduo.ui.activity.EditingformationAcivity;
import com.yapin.shanduo.ui.activity.MyDynamicsActivity;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonFragment extends Fragment {

    private Activity activity;
    private Context context;

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

    @OnClick({R.id.modify, R.id.exit_login,R.id.tv_MyDynamics})
    public void onClick(View view){
        switch (view.getId()){
//            case R.id.login :
//                StartActivityUtil.start(activity , LoginActivity.class);
//                break;
//            case R.id.register:
//                StartActivityUtil.start(activity , RegisterActivity.class);
//                break;
            case R.id.modify:
                StartActivityUtil.start(activity , EditingformationAcivity.class);
                break;
            case R.id.tv_MyDynamics:
                StartActivityUtil.start(activity , MyDynamicsActivity.class);
                break;
            case R.id.exit_login:
                PrefUtil.setToken(context ,"");
                ToastUtil.showShortToast(context , "logout success");
                break;
        }
    }

}
