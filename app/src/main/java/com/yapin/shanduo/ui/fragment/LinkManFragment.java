package com.yapin.shanduo.ui.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.auth.AlipaySDK;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.ui.SearchFriendActivity;
import com.yapin.shanduo.im.ui.SearchGroupActivity;
import com.yapin.shanduo.model.entity.PayResult;
import com.yapin.shanduo.ui.activity.AddHumanGroupActivity;
import com.yapin.shanduo.ui.activity.SearchAllActivity;
import com.yapin.shanduo.ui.adapter.LinkTabAdapter;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinkManFragment extends Fragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private Context context;
    private Activity activity;

    private LinkTabAdapter adapter;

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
        initView();
        return view;
    }

    public void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();

        List<String> tabList = new ArrayList<>();
        tabList.add("我的好友");
        tabList.add("我的群组");

        viewPager.setOffscreenPageLimit(2);
        adapter = new LinkTabAdapter(getChildFragmentManager(), tabList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                Utils.setIndicator(tabLayout , 55 , 55);
            }
        });
    }

    @OnClick({R.id.iv_add_friend , R.id.ll_search})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_add_friend:
                StartActivityUtil.start(activity , this , AddHumanGroupActivity.class);
                break;
            case R.id.ll_search:
//                if (viewPager.getCurrentItem() == 0) {
                Bundle bundle = new Bundle();
                bundle.putString("type" , Constants.SEARCH_FRIEND);
                StartActivityUtil.start(activity , this , SearchAllActivity.class , bundle);
//                }else {
//                    StartActivityUtil.start(activity ,this , SearchGroupActivity.class);
//                }
                break;
        }
    }

}
