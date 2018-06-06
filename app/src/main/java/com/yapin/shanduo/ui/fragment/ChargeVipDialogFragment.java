package com.yapin.shanduo.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.ui.adapter.ChargeTabAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：L on 2018/6/5 0005 19:07
 */
public class ChargeVipDialogFragment extends DialogFragment {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_vip)
    ImageView ivVip;
    @BindView(R.id.iv_svip)
    ImageView ivSvip;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private ChargeTabAdapter adapter;

    private DialogDismiss dialogDismiss;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//无标题栏
        View view = inflater.inflate(R.layout.charge_vip_layout, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        adapter = new ChargeTabAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0 ){
                    ivVip.setBackgroundResource(R.drawable.icon_charge_vip);
                    ivSvip.setBackgroundResource(R.drawable.icon_charge_svip_uncheck);
                }else {
                    ivVip.setBackgroundResource(R.drawable.icon_vip_checked);
                    ivSvip.setBackgroundResource(R.drawable.icon_svip_checked);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDismiss.dismiss();
            }
        });

    }

    public void setDismissListener(DialogDismiss dismissListener){
        this.dialogDismiss = dismissListener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface DialogDismiss{
        void dismiss();
    }

}
