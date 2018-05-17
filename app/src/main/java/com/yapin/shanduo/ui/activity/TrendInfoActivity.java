package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.ui.adapter.TrendGridViewAdapter;
import com.yapin.shanduo.ui.adapter.TrendInfoAdapter;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.LoadMoreRecyclerView;
import com.yapin.shanduo.widget.MyGridView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrendInfoActivity extends BaseActivity{

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_tag)
    TextView tvTag;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_mile)
    TextView tvMile;
    @BindView(R.id.rl_user_info)
    RelativeLayout rlUserInfo;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_img1)
    ImageView ivImg1;
    @BindView(R.id.iv_img2)
    ImageView ivImg2;
    @BindView(R.id.rl_img1)
    RelativeLayout rlImg1;
    @BindView(R.id.gridview)
    MyGridView gridview;
    @BindView(R.id.tv_publish_time)
    TextView tvPublishTime;
    @BindView(R.id.rl_trend_info)
    RelativeLayout rlTrendInfo;
    @BindView(R.id.tv_gray)
    TextView tvGray;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.rl_like)
    RelativeLayout rlLike;
    @BindView(R.id.tv_comment_count)
    TextView tvCommentCount;
    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.iv_expression)
    ImageView ivExpression;
    @BindView(R.id.rl_comment)
    RelativeLayout rlComment;
    @BindView(R.id.tv_replay_count)
    TextView tvReplayCount;
    @BindView(R.id.tv_like_count)
    TextView tvLikeCount;

    private Context context;
    private Activity activity;
    private TrendInfo.Trend trend;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_info);

        ButterKnife.bind(this);
        trend = getIntent().getParcelableExtra("trendInfo");
        initView();
    }

    public void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = this;

        setIsEvent(Constants.IS_EVENT);

        GlideUtil.load(context, activity, trend.getPortraitId(), ivHead);
        tvName.setText(trend.getName());

        Drawable drawable = null;
        if ("0".equals(trend.getGender())) {
            drawable = activity.getResources().getDrawable(R.drawable.icon_women);
            tvAge.setBackgroundResource(R.drawable.rounded_tv_sex_women);
        } else {
            drawable = activity.getResources().getDrawable(R.drawable.icon_men);
            tvAge.setBackgroundResource(R.drawable.rounded_tv_sex_men);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvAge.setCompoundDrawables(drawable, null, null, null);
        tvAge.setCompoundDrawablePadding(2);
        tvAge.setText(trend.getAge() + "");

        tvMile.setText(trend.getDistance() + "km");
        tvContent.setText(trend.getContent());

        tvReplayCount.setText(trend.getDynamicCount()+"");
        tvCommentCount.setText("("+trend.getDynamicCount()+")");
        tvLikeCount.setText(trend.getPraise()+"");

        if (TextUtils.isEmpty(Utils.vipLevel(trend.getVip()))) {
            tvVip.setVisibility(View.GONE);
        } else {
            tvVip.setText(Utils.vipLevel(trend.getVip()));
        }

        int size = trend.getPicture().size();
        switch (size){
            case 0:
                gridview.setVisibility(View.GONE);
                rlImg1.setVisibility(View.GONE);
                break;
            case 1:
                gridview.setVisibility(View.GONE);
                rlImg1.setVisibility(View.VISIBLE);
                GlideUtil.load(context ,activity , ApiUtil.IMG_URL+trend.getPicture().get(0) ,ivImg1 ,5 );
                ivImg2.setVisibility(View.GONE);
                break;
            case 2:
                gridview.setVisibility(View.GONE);
                rlImg1.setVisibility(View.VISIBLE);
                GlideUtil.load(context ,activity ,ApiUtil.IMG_URL+trend.getPicture().get(0) ,ivImg1 ,5 );
                GlideUtil.load(context ,activity ,ApiUtil.IMG_URL+trend.getPicture().get(1) ,ivImg2 ,5 );
                break;
            default:
                gridview.setVisibility(View.VISIBLE);
                rlImg1.setVisibility(View.GONE);
                TrendGridViewAdapter adapter = new TrendGridViewAdapter(context, trend.getPicture(), activity);
                gridview.setAdapter(adapter);
                break;
        }
    }

    @OnClick({R.id.iv_share , R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_share:
                break;
            case R.id.rl_back:
                onBackPressed();
                break;
        }
    }


}
