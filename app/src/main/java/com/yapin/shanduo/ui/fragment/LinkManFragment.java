package com.yapin.shanduo.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tencent.TIMConversationType;
import com.tencent.qcloud.presentation.viewfeatures.ChatView;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.ui.ChatActivity;
import com.yapin.shanduo.ui.activity.EditingformationAcivity;
import com.yapin.shanduo.ui.activity.LoginActivity;
import com.yapin.shanduo.ui.activity.MapTextingActivity;
import com.yapin.shanduo.ui.activity.RegisterActivity;
import com.yapin.shanduo.utils.StartActivityUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinkManFragment extends Fragment {

    private Context context;
    private Activity activity;

    public static LinkManFragment newInstance() {
        LinkManFragment fragment = new LinkManFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_linkman_layout,container,false);
        ButterKnife.bind(this,view);
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();
        return view;
    }

    @OnClick({R.id.btn_go_chat1 , R.id.btn_go_chat2,R.id.login , R.id.register,R.id.modify,R.id.maptext})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_go_chat1:
                ChatActivity.navToChat(activity , "456789" , TIMConversationType.C2C);
                break;
            case R.id.btn_go_chat2:
                ChatActivity.navToChat(activity , "123456789" , TIMConversationType.C2C);
                break;
            case R.id.login :
                StartActivityUtil.start(activity , LoginActivity.class);
                break;
            case R.id.register:
                StartActivityUtil.start(activity , RegisterActivity.class);
                break;
            case R.id.modify:
                StartActivityUtil.start(activity , EditingformationAcivity.class);
                break;
            case R.id.maptext:
                StartActivityUtil.start(activity , MapTextingActivity.class);
                break;
        }
    }

}
