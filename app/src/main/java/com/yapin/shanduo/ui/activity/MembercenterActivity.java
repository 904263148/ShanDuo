package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.ui.adapter.ChargeTabAdapter;
import com.yapin.shanduo.ui.fragment.ChargeVipDialogFragment;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.PrefUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/25.
 */

public class MembercenterActivity extends BaseActivity implements PopupWindow.OnDismissListener{

    private Context context;
    private Activity activity;

    @BindView(R.id.ib_Headportrait)
    ImageView ib_Headportrait;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.iv_vip_Headportrait)
    ImageView iv_vip_Headportrait;

    private PopupWindow popupWindow;
    private ChargeTabAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_center);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){

            tv_nickname.setText(PrefJsonUtil.getProfile(context).getName());
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , ib_Headportrait);
        GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportrait);

        }


    @OnClick({R.id.iv_back , R.id.charge_vip})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.charge_vip:
                openPublishPopup();
                break;
        }
    }

    public void openPublishPopup(){

        ChargeVipDialogFragment dialog = new ChargeVipDialogFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        dialog.show(ft, "tag");

    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    @Override
    public void onDismiss() {
        setBackgroundAlpha(1);
    }

}
