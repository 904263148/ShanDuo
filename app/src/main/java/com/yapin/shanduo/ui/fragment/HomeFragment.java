package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.ui.activity.PublishActivity;
import com.yapin.shanduo.ui.activity.ScanActivity;
import com.yapin.shanduo.ui.activity.ScrollingActivity;
import com.yapin.shanduo.ui.activity.SearchActActivity;
import com.yapin.shanduo.ui.activity.SmartRefreshActivity;
import com.yapin.shanduo.ui.adapter.HomeViewPagerAdapter;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;

import javax.xml.transform.Result;

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

    @OnClick({R.id.btn_scan ,R.id.tv_act , R.id.tv_trend ,R.id.iv_search})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_scan:
                StartActivityUtil.start(activity ,this , SmartRefreshActivity.class , OPEN_SCAN);
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
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != activity.RESULT_OK){
            return;
        }
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == OPEN_SCAN) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ToastUtil.showShortToast(context, "解析结果:" + result);
                    Log.d("Scan_Result","解析结果:" + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.showShortToast(context, "解析二维码失败");
                    Log.d("Scan_Result","解析二维码失败");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
