package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.ui.activity.LoginActivity;
import com.yapin.shanduo.ui.activity.SearchActActivity;
import com.yapin.shanduo.ui.activity.SiginActivity;
import com.yapin.shanduo.ui.adapter.HomeViewPagerAdapter;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment{

    @BindView(R.id.view_pager)
    AHBottomNavigationViewPager viewPager;
    @BindView(R.id.rg_title)
    RadioGroup radioGroup;
    @BindView(R.id.tv_act)
    TextView tvAct;
    @BindView(R.id.tv_trend)
    TextView tvTrend;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.iv_shanduo)
    ImageView ivShanduo;

    private Context context;
    private Activity activity;

    private final static int OPEN_SCAN = 1;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_activity_layout,container,false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();

//        tvAct.setText(Html.fromHtml("<u>活动</u>"));
//        tvTrend.setText(Html.fromHtml("<u>动态</u>"));

        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new HomeViewPagerAdapter(getChildFragmentManager()));

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_activity){
                    viewPager.setCurrentItem(0,false);
                }else{
                    viewPager.setCurrentItem(1,false);
                }
            }
        });

    }

    @OnClick({R.id.btn_scan ,R.id.tv_act , R.id.tv_trend ,R.id.iv_search , R.id.iv_sign})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_scan:
//                StartActivityUtil.start(activity ,this , PublishActivity.class , OPEN_SCAN);
                break;
            case R.id.tv_act:
                tvAct.setTextColor(getResources().getColor(R.color.home_title_select_color));
                tvAct.setTextSize(17);
                tvTrend.setTextColor(getResources().getColor(R.color.font_color_gray));
                tvTrend.setTextSize(15);
                viewPager.setCurrentItem(0,false);
                break;
            case R.id.tv_trend:
                tvTrend.setTextColor(getResources().getColor(R.color.home_title_select_color));
                tvTrend.setTextSize(17);
                tvAct.setTextColor(getResources().getColor(R.color.font_color_gray));
                tvAct.setTextSize(15);
                viewPager.setCurrentItem(1,false);
                break;
            case R.id.iv_search:
                StartActivityUtil.start(activity , this , SearchActActivity.class );
                break;
            case R.id.iv_sign:
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity , LoginActivity.class);
                }else {
                    StartActivityUtil.start(activity , this , SiginActivity.class );
                }
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setTitleHidden(float alpha){
        if(ivShanduo == null || llTitle == null) return;
        if(alpha == 1){
            ivShanduo.setVisibility(View.GONE);
            llTitle.setVisibility(View.VISIBLE);
        }else {
            ivShanduo.setVisibility(View.VISIBLE);
            llTitle.setVisibility(View.GONE);
        }
//        llTitle.setAlpha(alpha);
    }

}
