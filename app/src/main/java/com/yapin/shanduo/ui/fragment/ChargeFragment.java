package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChargeFragment extends Fragment {

    @BindView(R.id.iv_time)
    ImageView ivTime;
    @BindView(R.id.rb_three)
    RadioButton rbThree;
    @BindView(R.id.rb_six)
    RadioButton rbSix;
    @BindView(R.id.rb_twelve)
    RadioButton rbTwelve;
    @BindView(R.id.rb_one)
    RadioButton rbOne;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_go)
    TextView tvGo;
    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;

    private int position;

    private Context context;
    private Activity activity;

    public ChargeFragment() {
        // Required empty public constructor
    }

    public static ChargeFragment newInstance(int position) {
        ChargeFragment fragment = new ChargeFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_charge, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();

        if(position == 0){
            rbOne.setBackgroundResource(R.drawable.charge_selector);
            rbThree.setBackgroundResource(R.drawable.charge_selector);
            rbSix.setBackgroundResource(R.drawable.charge_selector);
            rbTwelve.setBackgroundResource(R.drawable.charge_selector);
        }else {

        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
