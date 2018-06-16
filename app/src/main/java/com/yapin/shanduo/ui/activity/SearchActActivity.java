package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.ActivityInfo;
import com.yapin.shanduo.presenter.SearchActPresenter;
import com.yapin.shanduo.ui.adapter.ActivityInfoAdapter;
import com.yapin.shanduo.ui.contract.SearchActContract;
import com.yapin.shanduo.ui.inter.OpenPopupWindow;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActActivity extends RightSlidingActivity implements  View.OnKeyListener , SearchActContract.View , LoadMoreRecyclerView.OnLoadMoreListener , ActivityInfoAdapter.OnItemClickListener , OpenPopupWindow {


    @BindView(R.id.inputSearch)
    EditText inputSearch;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.title)
    LinearLayout title;
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.noResult)
    TextView noResult;
    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;

    private Context context;
    private Activity activity;

    private int page = 1;
    private int pageSize = 10;
    private boolean isLoading = false;

    private SearchActPresenter presenter;
    private List<ActivityInfo.Act> list = new ArrayList<>();
    private ActivityInfoAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ActivityInfo.Act footerItem = new ActivityInfo.Act();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_act);
        ButterKnife.bind(this);
        presenter = new SearchActPresenter();
        presenter.init(this);
    }

    @Override
    public void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        TextView tvCancel = (TextView) findViewById(R.id.cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        inputSearch.setOnKeyListener(this);

        footerItem.setType(Constants.TYPE_FOOTER_LOAD);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new ActivityInfoAdapter(context ,activity , list ,10);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP){   // 忽略其它事件
            return false;
        }

        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                list.clear();
                adapter.notifyDataSetChanged();
                String key = inputSearch.getText().toString();
                if (key.equals("")) return true;
                presenter.getData(key , page+"" , pageSize+"");
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    /**
     * 设置刷新和加载更多的状态
     *
     * @param isLoading 加载更多状态
     */
    public void setRefreshLoading(boolean isLoading) {
        this.isLoading = isLoading;
        if (!isLoading) {
            recyclerView.setLoading(false);
        }
    }

    @Override
    public void show(List<ActivityInfo.Act> data, int totalPage) {
        if (!isLoading) {
            if (totalPage == 0) {
                noResult.setVisibility(View.VISIBLE);
                return;
            } else {
                noResult.setVisibility(View.GONE);
            }
            list.clear();
            list.add(footerItem);
        }
        recyclerView.setPage(page, totalPage);
        footerItem.setType(page < totalPage ? Constants.TYPE_FOOTER_LOAD : Constants.TYPE_FOOTER_FULL);
        list.addAll(list.size() - 1, data);
        setRefreshLoading(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {
        setRefreshLoading(false);
    }

    @Override
    public void error(String msg) {
        setRefreshLoading(false);
    }

    @Override
    public void showFailed(String msg) {
        setRefreshLoading(false);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onTextClick(int position, ActivityInfo.Act act, int type) {

    }

    @Override
    public void onLoadMore() {
        page++;
        setRefreshLoading(true);
        presenter.getData(inputSearch.getText().toString() , page+"" , pageSize+"");
    }

    @Override
    public void openPopupWindow(Object object, int type) {

    }

    @Override
    public void onTitleHidden(float alpha) {

    }
}
