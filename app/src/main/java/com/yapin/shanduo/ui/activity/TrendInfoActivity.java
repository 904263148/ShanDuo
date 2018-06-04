package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.CommentInfo;
import com.yapin.shanduo.model.entity.TrendInfo;
import com.yapin.shanduo.presenter.LikePresenter;
import com.yapin.shanduo.presenter.TrendInfoPresenter;
import com.yapin.shanduo.presenter.TrendReplayPresenter;
import com.yapin.shanduo.ui.adapter.TrendCommentAdapter;
import com.yapin.shanduo.ui.adapter.TrendGridViewAdapter;
import com.yapin.shanduo.ui.contract.LikeContract;
import com.yapin.shanduo.ui.contract.TrendInfoContract;
import com.yapin.shanduo.ui.contract.TrendReplayContract;
import com.yapin.shanduo.ui.fragment.CustomBottomSheetDialogFragment;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.TimeUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.widget.LoadMoreRecyclerView;
import com.yapin.shanduo.widget.MyGridView;
import com.yich.layout.picwatcherlib.PicWatcher;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TrendInfoActivity extends RightSlidingActivity implements TrendInfoContract.View , TrendCommentAdapter.OnItemClickListener
        , LoadMoreRecyclerView.OnLoadMoreListener ,SwipeRefreshLayout.OnRefreshListener , TrendReplayContract.View  , LikeContract.View{

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
    @BindView(R.id.tv_home_age)
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
    @BindView(R.id.tv_date)
    TextView tvDate;
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
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private Context context;
    private Activity activity;
    private TrendInfo.Trend trend;

    private TrendInfoPresenter presenter;

    private LinearLayoutManager layoutManager;
    private int page = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private boolean isLoading = false;
    private List<CommentInfo.Comment> list = new ArrayList<>();
    private CommentInfo.Comment footerItem = new CommentInfo.Comment();
    private TrendCommentAdapter adapter;

    private final static String TYPEID = "1";

    private TrendReplayPresenter replayPresenter;
    private LikePresenter likePresenter;

    private InputMethodManager imm;

    private CustomBottomSheetDialogFragment fragment;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_info);

        ButterKnife.bind(this);
        trend = getIntent().getParcelableExtra("trendInfo");
        presenter = new TrendInfoPresenter();
        presenter.init(this);
        replayPresenter = new TrendReplayPresenter();
        replayPresenter.init(this);
        likePresenter  = new LikePresenter();
        likePresenter.init(this);
    }

    @Override
    public void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = this;

        fragment = new CustomBottomSheetDialogFragment();

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        GlideUtil.load(context, activity,ApiUtil.IMG_URL+ trend.getPortraitId(), ivHead);
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

        String diff = TimeUtil.getTimeDiff(TimeUtil.getDateToString(trend.getCreateDate()), TimeUtil.getNowTime());
        if(TextUtils.isEmpty(diff)){
            tvDate.setText(TimeUtil.getDateToMMDD(trend.getCreateDate()));
            tvPublishTime.setText(TimeUtil.getDateTohhmm(trend.getCreateDate()));
        }else {
            tvDate.setText(diff);
            tvPublishTime.setText("");
        }

        int level = trend.getVip();
        if(level == 0){
            tvVip.setVisibility(View.GONE);
        }else if(level > 0 && level < 9){
            tvVip.setVisibility(View.VISIBLE);
            tvVip.setText("VIP"+level);
            tvVip.setBackgroundResource(R.drawable.rounded_tv_vip);
        }else {
            tvVip.setVisibility(View.VISIBLE);
            tvVip.setText("SVIP"+(level-10));
            tvVip.setBackgroundResource(R.drawable.rounded_tv_svip);
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
                GlideUtil.load(activity , ApiUtil.IMG_URL+trend.getPicture().get(0) ,ivImg1 );
                ivImg2.setVisibility(View.GONE);

                final List<ImageView> thumUrlsImageView = new ArrayList<>();
                thumUrlsImageView.add(ivImg1);
                ivImg1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicWatcher.showImages(activity , ivImg1 , 0 , thumUrlsImageView , trend.getPicture());
                    }
                });
                
                break;
            case 2:
                gridview.setVisibility(View.GONE);
                rlImg1.setVisibility(View.VISIBLE);
                GlideUtil.load(activity ,ApiUtil.IMG_URL+trend.getPicture().get(0) ,ivImg1 );
                GlideUtil.load(activity ,ApiUtil.IMG_URL+trend.getPicture().get(1) ,ivImg2  );

                final List<ImageView> thumUrlsImageView2 = new ArrayList<>();
                thumUrlsImageView2.add(ivImg1);
                thumUrlsImageView2.add(ivImg2);
                ivImg1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicWatcher.showImages(activity , ivImg1 , 0 , thumUrlsImageView2 , trend.getPicture());
                    }
                });
                ivImg2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PicWatcher.showImages(activity , ivImg2 , 1 , thumUrlsImageView2 , trend.getPicture());
                    }
                });
                
                break;
            default:
                gridview.setVisibility(View.VISIBLE);
                rlImg1.setVisibility(View.GONE);
                TrendGridViewAdapter adapter = new TrendGridViewAdapter(context, trend.getPicture(), activity);
                gridview.setAdapter(adapter);
                break;
        }

        layoutManager = new LinearLayoutManager(context);

        footerItem.setType(Constants.TYPE_FOOTER_LOAD);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new TrendCommentAdapter(context, activity , list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        recyclerView.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);

        recyclerView.setNestedScrollingEnabled(false);

        presenter.getData(trend.getId() , TYPEID ,page+"" , pageSize+"");
    }

    @OnClick({R.id.iv_share , R.id.rl_back , R.id.tv_publish  ,R.id.tv_mile , R.id.tv_like_count , R.id.iv_head})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_share:
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity , LoginActivity.class , Constants.OPEN_LOGIN);
                    return;
                }
                fragment.show(getSupportFragmentManager() , "share");
                break;
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.tv_publish:
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity , LoginActivity.class , Constants.OPEN_LOGIN);
                    return;
                }
                if(TextUtils.isEmpty(etComment.getText())){
                    ToastUtil.showShortToast(context , "内容不能为空");
                    return;
                }
                replayPresenter.getData(trend.getId() , etComment.getText().toString().trim() , TYPEID , "" ,"");
                etComment.setText("");
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.tv_mile:
                Bundle bundle = new Bundle();
                bundle.putDouble("lat" , trend.getLat());
                bundle.putDouble("lon" , trend.getLon());
                bundle.putString("place" , trend.getLocation());
                StartActivityUtil.start(activity , PlaceActivity.class , bundle);
                break;
            case R.id.tv_like_count:
                if(TextUtils.isEmpty(PrefUtil.getToken(context))){
                    StartActivityUtil.start(activity , LoginActivity.class , Constants.OPEN_LOGIN);
                    return;
                }
                likePresenter.onLike(trend.getId());
                break;
            case R.id.iv_back:
                Bundle bundle1 = new Bundle();
                bundle1.putString("userId" , trend.getUserId()+"");
                StartActivityUtil.start(activity , UserProfActivity.class , bundle1);
                break;
        }
    }

    /**
     * 设置刷新和加载更多的状态
     *
     * @param isRefresh 刷新状态
     * @param isLoading 加载更多状态
     */
    public void setRefreshLoading(boolean isRefresh, boolean isLoading) {
        this.isRefresh = isRefresh;
        this.isLoading = isLoading;

        if (!isRefresh && !isLoading) {
            recyclerView.setLoading(false);
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void show(List<CommentInfo.Comment> data, int totalPage) {
        if (!isLoading) {
//            if (totalPage == 0) {
//                loadingView.noData(R.string.tips_no_act);
//            } else {
//                loadingView.setGone();
//            }
            list.clear();
            list.add(footerItem);
        }
        recyclerView.setPage(page, totalPage);
        footerItem.setType(page < totalPage ? Constants.TYPE_FOOTER_LOAD : Constants.TYPE_FOOTER_FULL);
        list.addAll(list.size() - 1, data);
        adapter.notifyDataSetChanged();
        setRefreshLoading(false, false);
    }

    @Override
    public void show(String data) {
        ToastUtil.showShortToast(context , data);
        onRefresh();
    }

    @Override
    public void success(String data) {
        if(Constants.IS_LIKE.equals(data)){
            ToastUtil.showShortToast(context , R.string.tips_like);
            tvLikeCount.setText( (Integer.parseInt(tvLikeCount.getText().toString())+1) +"");
        }else {
            ToastUtil.showShortToast(context , R.string.tips_unlike);
            tvLikeCount.setText( (Integer.parseInt(tvLikeCount.getText().toString())-1) +"");
        }
    }

    @Override
    public void loading() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void networkError() {
        setRefreshLoading(false, false);
    }

    @Override
    public void error(String msg) {
        setRefreshLoading(false, false);
        ToastUtil.showShortToast(context , msg);
    }

    @Override
    public void showFailed(String msg) {
        setRefreshLoading(false, false);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("comment" , list.get(position));
        StartActivityUtil.start(activity , ReplayInfoActivity.class , bundle);
    }

    @Override
    public void onHeadClick(int id) {
        Bundle bundle = new Bundle();
        bundle.putString("userId" , id+"");
        StartActivityUtil.start(activity , UserProfActivity.class , bundle);
    }

    @Override
    public void onLoadMore() {
        page++;
        setRefreshLoading(false, true);
        presenter.getData(trend.getId() , TYPEID ,page+"" , pageSize+"");
    }

    @Override
    public void onRefresh() {
        setRefreshLoading(true, false);
        page = 1;
        presenter.getData(trend.getId() , TYPEID ,page+"" , pageSize+"");
    }
}
