package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.CommentInfo;
import com.yapin.shanduo.model.entity.SecondComment;
import com.yapin.shanduo.presenter.TrendInfoPresenter;
import com.yapin.shanduo.presenter.TrendReplayPresenter;
import com.yapin.shanduo.presenter.TrendSecondReplayPresenter;
import com.yapin.shanduo.ui.adapter.TrendCommentAdapter;
import com.yapin.shanduo.ui.adapter.TrendSecondReplayAdapter;
import com.yapin.shanduo.ui.contract.TrendInfoContract;
import com.yapin.shanduo.ui.contract.TrendReplayContract;
import com.yapin.shanduo.ui.contract.TrendSecondReplayContract;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.TimeUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.widget.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReplayInfoActivity extends BaseActivity implements TrendSecondReplayContract.View , LoadMoreRecyclerView.OnLoadMoreListener
        ,SwipeRefreshLayout.OnRefreshListener , TrendSecondReplayAdapter.OnItemClickListener , TrendReplayContract.View{

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.tv_tag)
    TextView tvTag;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_one_comment)
    TextView tvOneComment;
    @BindView(R.id.ll_two_replay)
    LinearLayout llTwoReplay;
    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.tv_tag2)
    TextView tvTag2;
    @BindView(R.id.et_comment)
    EditText etComment;
    @BindView(R.id.iv_expression)
    ImageView ivExpression;
    @BindView(R.id.tv_publish)
    TextView tvPublish;
    @BindView(R.id.rl_comment)
    RelativeLayout rlComment;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;

    private Context context;
    private Activity activity;

    TrendSecondReplayPresenter presenter;

    private CommentInfo.Comment comment;

    private LinearLayoutManager layoutManager;
    private int page = 1;
    private int pageSize = 10;
    private boolean isRefresh = false;
    private boolean isLoading = false;
    private List<SecondComment.Comments> list = new ArrayList<>();
    private SecondComment.Comments footerItem = new SecondComment.Comments();
    private TrendSecondReplayAdapter adapter;

    private final static String TYPE_ID = "2";

    private TrendReplayPresenter replayPresenter;

    private InputMethodManager imm;

    private int replay_position = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay_info);
        ButterKnife.bind(this);
        comment = getIntent().getParcelableExtra("comment");
        presenter = new TrendSecondReplayPresenter();
        presenter.init(this);
        replayPresenter = new TrendReplayPresenter();
        replayPresenter.init(this);
    }

    @Override
    public void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = this;

        setIsEvent(Constants.IS_EVENT);

        tvTitle.setText(comment.getCount()+"条回复");
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        GlideUtil.load(context ,activity , ApiUtil.IMG_URL+comment.getPortraitId() , ivHead);
        tvName.setText(comment.getName());
        Drawable drawable = null;
        if ("0".equals(comment.getGender())) {
            drawable = activity.getResources().getDrawable(R.drawable.icon_women);
            tvAge.setBackgroundResource(R.drawable.rounded_tv_sex_women);
        } else {
            drawable = activity.getResources().getDrawable(R.drawable.icon_men);
            tvAge.setBackgroundResource(R.drawable.rounded_tv_sex_men);
        }
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvAge.setCompoundDrawables(drawable, null, null, null);
        tvAge.setCompoundDrawablePadding(2);
        tvAge.setText(comment.getAge() +"");

        tvOneComment.setText(comment.getComment());

        tvDate.setText(TimeUtil.getDateToMMDD(comment.getCreateDate()));
        tvTime.setText(TimeUtil.getDateTohhmm(comment.getCreateDate()));

        layoutManager = new LinearLayoutManager(context);

        footerItem.setType(Constants.TYPE_FOOTER_LOAD);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new TrendSecondReplayAdapter(context, activity , list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        recyclerView.setOnLoadMoreListener(this);
        refreshLayout.setOnRefreshListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        presenter.getData(comment.getId() , TYPE_ID , page+"" ,pageSize+"");
    }


    @OnClick({R.id.iv_back , R.id.tv_publish , R.id.rl_tag})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_publish:
                if(TextUtils.isEmpty(etComment.getText())){
                    ToastUtil.showShortToast(context , "内容不能为空");
                    return;
                }
                if(replay_position == -1 ){
                    replayPresenter.getData(comment.getDynamicId() , etComment.getText().toString().trim() , TYPE_ID , comment.getId() ,comment.getUserId()+"");
                }else{
                    replayPresenter.getData(list.get(replay_position).getDynamicId() , etComment.getText().toString().trim() , TYPE_ID , list.get(replay_position).getId() ,list.get(replay_position).getUserId()+"");
                }
                etComment.setText("");
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.rl_tag:
                replay_position = -1;
                imm.toggleSoftInput(1 , InputMethodManager.RESULT_SHOWN);
                etComment.setHint("回复"+comment.getName());
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
    public void show(List<SecondComment.Comments> data, int totalPage) {
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
        ToastUtil.showShortToast(context , "发表成功");
        onRefresh();
    }

    @Override
    public void loading() {

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
        replay_position = position;
        imm.toggleSoftInput(1 , InputMethodManager.RESULT_SHOWN);
        etComment.setHint("回复"+list.get(position).getUserName());
    }

    @Override
    public void onLoadMore() {
        page++;
        setRefreshLoading(false, true);
        presenter.getData(comment.getId() , TYPE_ID ,page+"" , pageSize+"");
    }

    @Override
    public void onRefresh() {
        setRefreshLoading(true, false);
        page = 1;
        presenter.getData(comment.getId() , TYPE_ID ,page+"" , pageSize+"");
    }
}
