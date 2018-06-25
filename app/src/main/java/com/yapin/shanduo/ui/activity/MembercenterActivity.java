package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.GetVipLevelInfo;
import com.yapin.shanduo.presenter.GetVipLevelPresenter;
import com.yapin.shanduo.ui.adapter.ChargeTabAdapter;
import com.yapin.shanduo.ui.contract.GetVipLevelContract;
import com.yapin.shanduo.ui.fragment.ChargeVipDialogFragment;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.GrowthValueProgress;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/25.
 */

public class MembercenterActivity extends BaseActivity implements PopupWindow.OnDismissListener ,ChargeVipDialogFragment.DialogDismiss ,
        GetVipLevelContract.View{

    private Context context;
    private Activity activity;
    private GetVipLevelPresenter presenter;

    @BindView(R.id.ib_Headportrait)
    ImageView ib_Headportrait;
    @BindView(R.id.tv_nickname)
    TextView tv_nickname;
    @BindView(R.id.tv_svip)
    TextView tv_svip;
    @BindView(R.id.tv_sex)
    TextView tv_sex;

    @BindView(R.id.rl_Upperleft)
    RelativeLayout rl_Upperleft;
    @BindView(R.id.a)
    TextView Speedmultiplier;
    @BindView(R.id.f)
    TextView Thesum;

    @BindView(R.id.ll_a)
    LinearLayout ll_a;
    @BindView(R.id.iv_vip_Headportraita)
    ImageView iv_vip_Headportraita;
    @BindView(R.id.tv_Cylindera)
    TextView tv_Cylindera;

    @BindView(R.id.ll_b)
    LinearLayout ll_b;
    @BindView(R.id.iv_vip_Headportraitb)
    ImageView iv_vip_Headportraitb;
    @BindView(R.id.tv_Cylinderb)
    TextView tv_Cylinderb;

    @BindView(R.id.ll_c)
    LinearLayout ll_c;
    @BindView(R.id.iv_vip_Headportraitc)
    ImageView iv_vip_Headportraitc;
    @BindView(R.id.tv_Cylinderc)
    TextView tv_Cylinderc;

    @BindView(R.id.ll_d)
    LinearLayout ll_d;
    @BindView(R.id.iv_vip_Headportraitd)
    ImageView iv_vip_Headportraitd;
    @BindView(R.id.tv_Cylinderd)
    TextView tv_Cylinderd;

    @BindView(R.id.ll_e)
    LinearLayout ll_e;
    @BindView(R.id.iv_vip_Headportraite)
    ImageView iv_vip_Headportraite;
    @BindView(R.id.tv_Cylindere)
    TextView tv_Cylindere;

    @BindView(R.id.ll_f)
    LinearLayout ll_f;
    @BindView(R.id.iv_vip_Headportraitf)
    ImageView iv_vip_Headportraitf;
    @BindView(R.id.tv_Cylinderf)
    TextView tv_Cylinderf;

    @BindView(R.id.ll_g)
    LinearLayout ll_g;
    @BindView(R.id.iv_vip_Headportraitg)
    ImageView iv_vip_Headportraitg;
    @BindView(R.id.tv_Cylinderg)
    TextView tv_Cylinderg;

    @BindView(R.id.ll_h)
    LinearLayout ll_h;
    @BindView(R.id.iv_vip_Headportraith)
    ImageView iv_vip_Headportraith;
    @BindView(R.id.tv_Cylinderh)
    TextView tv_Cylinderh;

    @BindView(R.id.progress)
    GrowthValueProgress progress;

    private PopupWindow popupWindow;
    private ChargeTabAdapter adapter;

    private ChargeVipDialogFragment dialog;

    private GetVipLevelInfo getVipLevelInfo = new GetVipLevelInfo();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_center);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);

        presenter = new GetVipLevelPresenter();
        presenter.init(context , this);

        presenter.getvipLevel();
    }
    @Override
    public void initView(){

        Drawable drawable = null;
        if ("0".equals(PrefJsonUtil.getProfile(context).getGender())) {
            drawable = activity.getResources().getDrawable(R.drawable.icon_women);
            tv_sex.setBackgroundResource(R.drawable.rounded_tv_sex_women);
        } else {
            drawable = activity.getResources().getDrawable(R.drawable.icon_men);
            tv_sex.setBackgroundResource(R.drawable.rounded_tv_sex_men);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tv_sex.setCompoundDrawables(drawable, null, null, null);
        tv_sex.setCompoundDrawablePadding(2);
        tv_sex.setText(PrefJsonUtil.getProfile(context).getAge()+"");

        int level = PrefJsonUtil.getProfile(context).getVip();
        if(level == 0){
            tv_svip.setVisibility(View.GONE);
        }else if(level < 9){
            tv_svip.setVisibility(View.VISIBLE);
            tv_svip.setText("VIP"+level);
            tv_svip.setBackgroundResource(R.drawable.rounded_tv_vip);
        }else {
            tv_svip.setVisibility(View.VISIBLE);
            tv_svip.setText("SVIP"+(level-10));
            tv_svip.setBackgroundResource(R.drawable.rounded_tv_svip);
        }

            tv_nickname.setText(Utils.unicodeToString(PrefJsonUtil.getProfile(context).getName()));
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , ib_Headportrait);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
    }

    public void openPublishPopup(){

        dialog = new ChargeVipDialogFragment();
        dialog.setDismissListener(this);
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

    @Override
    public void dismiss() {
        dialog.dismiss();
    }

    public void getvipleve(){

        int values=0;

        progress.setCurrentValues(values);

        if (getVipLevelInfo.getExperience() == 0){
            rl_Upperleft.setVisibility(View.GONE);
        }else {
            Thesum.setText(getVipLevelInfo.getExperience() + "");
            progress.setCurrentValues(Integer.parseInt(getVipLevelInfo.getExperience()+""));
            progress.invalidate();
        }

        if (getVipLevelInfo.getLevel() == 0){
            iv_vip_Headportraita.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitb.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitc.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitd.setVisibility(View.INVISIBLE);
            iv_vip_Headportraite.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitf.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitg.setVisibility(View.INVISIBLE);
            iv_vip_Headportraith.setVisibility(View.INVISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.vip_cylinderb_shape);
        }else if (getVipLevelInfo.getLevel() == 1){
            Speedmultiplier.setText("1.1");
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportraita);
            iv_vip_Headportraita.setVisibility(View.VISIBLE);
            iv_vip_Headportraitb.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitc.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitd.setVisibility(View.INVISIBLE);
            iv_vip_Headportraite.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitf.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitg.setVisibility(View.INVISIBLE);
            iv_vip_Headportraith.setVisibility(View.INVISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.vip_cylinderb_shape);
        }else if (getVipLevelInfo.getLevel() == 2){
            Speedmultiplier.setText("1.2");
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportraitb);
            iv_vip_Headportraita.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitb.setVisibility(View.VISIBLE);
            iv_vip_Headportraitc.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitd.setVisibility(View.INVISIBLE);
            iv_vip_Headportraite.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitf.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitg.setVisibility(View.INVISIBLE);
            iv_vip_Headportraith.setVisibility(View.INVISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.vip_cylinderb_shape);
        }else if (getVipLevelInfo.getLevel() == 3){
            Speedmultiplier.setText("1.3");
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportraitc);
            iv_vip_Headportraita.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitb.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitc.setVisibility(View.VISIBLE);
            iv_vip_Headportraitd.setVisibility(View.INVISIBLE);
            iv_vip_Headportraite.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitf.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitg.setVisibility(View.INVISIBLE);
            iv_vip_Headportraith.setVisibility(View.INVISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.vip_cylinderb_shape);
        }else if (getVipLevelInfo.getLevel() == 4){
            Speedmultiplier.setText("1.4");
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportraitd);
            iv_vip_Headportraita.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitb.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitc.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitd.setVisibility(View.VISIBLE);
            iv_vip_Headportraite.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitf.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitg.setVisibility(View.INVISIBLE);
            iv_vip_Headportraith.setVisibility(View.INVISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.vip_cylinderb_shape);
        }else if (getVipLevelInfo.getLevel() == 5){
            Speedmultiplier.setText("1.5");
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportraite);
            iv_vip_Headportraita.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitb.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitc.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitd.setVisibility(View.INVISIBLE);
            iv_vip_Headportraite.setVisibility(View.VISIBLE);
            iv_vip_Headportraitf.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitg.setVisibility(View.INVISIBLE);
            iv_vip_Headportraith.setVisibility(View.INVISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.vip_cylinderb_shape);
        }else if (getVipLevelInfo.getLevel() == 6){
            Speedmultiplier.setText("1.6");
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportraitf);
            iv_vip_Headportraita.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitb.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitc.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitd.setVisibility(View.INVISIBLE);
            iv_vip_Headportraite.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitf.setVisibility(View.VISIBLE);
            iv_vip_Headportraitg.setVisibility(View.INVISIBLE);
            iv_vip_Headportraith.setVisibility(View.INVISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.vip_cylinderb_shape);
        }else if (getVipLevelInfo.getLevel() == 7){
            Speedmultiplier.setText("1.7");
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportraitg);
            iv_vip_Headportraita.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitb.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitc.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitd.setVisibility(View.INVISIBLE);
            iv_vip_Headportraite.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitf.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitg.setVisibility(View.VISIBLE);
            iv_vip_Headportraith.setVisibility(View.INVISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.vip_cylinderb_shape);
        }else if (getVipLevelInfo.getLevel() == 8){
            Speedmultiplier.setText("1.8");
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportraith);
            iv_vip_Headportraita.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitb.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitc.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitd.setVisibility(View.INVISIBLE);
            iv_vip_Headportraite.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitf.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitg.setVisibility(View.INVISIBLE);
            iv_vip_Headportraith.setVisibility(View.VISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.vip_cylindera_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.vip_cylindera_shape);
        }else if (getVipLevelInfo.getLevel() == 11){
            Speedmultiplier.setText("1.2");
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportraita);
            iv_vip_Headportraita.setVisibility(View.VISIBLE);
            iv_vip_Headportraitb.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitc.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitd.setVisibility(View.INVISIBLE);
            iv_vip_Headportraite.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitf.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitg.setVisibility(View.INVISIBLE);
            iv_vip_Headportraith.setVisibility(View.INVISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.vip_cylinderb_shape);
        }else if (getVipLevelInfo.getLevel() == 12){
            Speedmultiplier.setText("1.3");
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportraitb);
            iv_vip_Headportraita.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitb.setVisibility(View.VISIBLE);
            iv_vip_Headportraitc.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitd.setVisibility(View.INVISIBLE);
            iv_vip_Headportraite.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitf.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitg.setVisibility(View.INVISIBLE);
            iv_vip_Headportraith.setVisibility(View.INVISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.vip_cylinderb_shape);
        }else if (getVipLevelInfo.getLevel() == 13){
            Speedmultiplier.setText("1.4");
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportraitc);
            iv_vip_Headportraita.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitb.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitc.setVisibility(View.VISIBLE);
            iv_vip_Headportraitd.setVisibility(View.INVISIBLE);
            iv_vip_Headportraite.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitf.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitg.setVisibility(View.INVISIBLE);
            iv_vip_Headportraith.setVisibility(View.INVISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.vip_cylinderb_shape);
        }else if (getVipLevelInfo.getLevel() == 14){
            Speedmultiplier.setText("1.5");
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportraitd);
            iv_vip_Headportraita.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitb.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitc.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitd.setVisibility(View.VISIBLE);
            iv_vip_Headportraite.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitf.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitg.setVisibility(View.INVISIBLE);
            iv_vip_Headportraith.setVisibility(View.INVISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.vip_cylinderb_shape);
        }else if (getVipLevelInfo.getLevel() == 15){
            Speedmultiplier.setText("1.6");
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportraite);
            iv_vip_Headportraita.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitb.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitc.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitd.setVisibility(View.INVISIBLE);
            iv_vip_Headportraite.setVisibility(View.VISIBLE);
            iv_vip_Headportraitf.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitg.setVisibility(View.INVISIBLE);
            iv_vip_Headportraith.setVisibility(View.INVISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.vip_cylinderb_shape);
        }else if (getVipLevelInfo.getLevel() == 16){
            Speedmultiplier.setText("1.7");
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportraitf);
            iv_vip_Headportraita.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitb.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitc.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitd.setVisibility(View.INVISIBLE);
            iv_vip_Headportraite.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitf.setVisibility(View.VISIBLE);
            iv_vip_Headportraitg.setVisibility(View.INVISIBLE);
            iv_vip_Headportraith.setVisibility(View.INVISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.vip_cylinderb_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.vip_cylinderb_shape);
        }else if (getVipLevelInfo.getLevel() == 17){
            Speedmultiplier.setText("1.8");
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportraitg);
            iv_vip_Headportraita.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitb.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitc.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitd.setVisibility(View.INVISIBLE);
            iv_vip_Headportraite.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitf.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitg.setVisibility(View.VISIBLE);
            iv_vip_Headportraith.setVisibility(View.INVISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.vip_cylinderb_shape);
        }else if (getVipLevelInfo.getLevel() == 18){
            Speedmultiplier.setText("1.9");
            GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , iv_vip_Headportraith);
            iv_vip_Headportraita.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitb.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitc.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitd.setVisibility(View.INVISIBLE);
            iv_vip_Headportraite.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitf.setVisibility(View.INVISIBLE);
            iv_vip_Headportraitg.setVisibility(View.INVISIBLE);
            iv_vip_Headportraith.setVisibility(View.VISIBLE);
            tv_Cylindera.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderb.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderc.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderd.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylindere.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderf.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderg.setBackgroundResource(R.drawable.svip_cylinder_shape);
            tv_Cylinderh.setBackgroundResource(R.drawable.svip_cylinder_shape);
        }
    }

    @Override
    public void success(GetVipLevelInfo data) {
        getVipLevelInfo = data;
        getvipleve();
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {
        ToastUtil.showShortToast(context,"网络连接异常");
    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortToast(context ,msg);
    }

    @Override
    public void showFailed(String msg) {
        ToastUtil.showShortToast(context ,msg);
    }
}
