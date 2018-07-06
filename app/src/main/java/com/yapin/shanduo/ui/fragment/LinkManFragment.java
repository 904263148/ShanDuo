package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.yapin.shanduo.R;
import com.yapin.shanduo.ui.activity.AddHumanGroupActivity;
import com.yapin.shanduo.ui.activity.SearchAllActivity;
import com.yapin.shanduo.ui.adapter.LinkTabAdapter;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.StartActivityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinkManFragment extends Fragment {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.sliding_tab)
    SlidingTabLayout slidingTabLayout;

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
        activity = getActivity();

        List<String> tabList = new ArrayList<>();
        tabList.add("我的好友");
        tabList.add("我的群组");

        viewPager.setOffscreenPageLimit(2);
        adapter = new LinkTabAdapter(getChildFragmentManager(), tabList);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager);
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
