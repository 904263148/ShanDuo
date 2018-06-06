package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.ui.activity.AddactivityActivity;
import com.yapin.shanduo.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChargeFragment extends Fragment implements PayDialogFragment.DialogDismiss{

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

    private PayDialogFragment payDialogFragment;

    private String month = "3";

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
            rbOne.setTextColor(R.drawable.charge_text_color_selector);
            rbThree.setTextColor(R.drawable.charge_text_color_selector);
            rbSix.setTextColor(R.drawable.charge_text_color_selector);
            rbTwelve.setTextColor(R.drawable.charge_text_color_selector);
            tvGo.setBackgroundResource(R.drawable.rounded_tv_vip);
            tvMoney.setTextColor(getResources().getColor(R.color.home_vip_color));
            tvMoney.setText("26.4");
        }else {
            rbOne.setBackgroundResource(R.drawable.charge_svip_selector);
            rbThree.setBackgroundResource(R.drawable.charge_svip_selector);
            rbSix.setBackgroundResource(R.drawable.charge_svip_selector);
            rbTwelve.setBackgroundResource(R.drawable.charge_svip_selector);
            rbOne.setTextColor(R.drawable.charge_svip_text_color_selector);
            rbThree.setTextColor(R.drawable.charge_svip_text_color_selector);
            rbSix.setTextColor(R.drawable.charge_svip_text_color_selector);
            rbTwelve.setTextColor(R.drawable.charge_svip_text_color_selector);
            tvGo.setBackgroundResource(R.drawable.rounded_tv_svip);
            tvMoney.setTextColor(getResources().getColor(R.color.home_svip_color));
            tvMoney.setText("38.4");
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_one:
                        month = "1";
                        if(position == 0){
                            tvMoney.setText("8.8");
                        }else {
                            tvMoney.setText("12.8");
                        }
                        break;
                    case R.id.rb_three:
                        month = "3";
                        if(position == 0){
                            tvMoney.setText("26.4");
                        }else {
                            tvMoney.setText("38.4");
                        }
                        break;
                    case R.id.rb_six:
                        month = "6";
                        if(position == 0){
                            tvMoney.setText("52.8");
                        }else {
                            tvMoney.setText("76.8");
                        }
                        break;
                    case R.id.rb_twelve:
                        month = "12";
                        if(position == 0){
                            tvMoney.setText("105.6");
                        }else {
                            tvMoney.setText("153.6");
                        }
                        break;
                }
            }
        });


        tvGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payDialogFragment = PayDialogFragment.newInstance(Constants.OPEN_BY_VIP , position , month , "" ,tvMoney.getText().toString());
                payDialogFragment.setDismissListener(ChargeFragment.this);
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                payDialogFragment.show(ft, "tag");
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

    @Override
    public void dismiss() {
        payDialogFragment.dismiss();
    }
}
