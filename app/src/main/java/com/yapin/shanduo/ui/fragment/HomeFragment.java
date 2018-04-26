package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.ui.adapter.HomeViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.view_pager)
    AHBottomNavigationViewPager viewPager;
    @BindView(R.id.rg_title)
    RadioGroup radioGroup;

    private Context context;
    private Activity activity;

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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
