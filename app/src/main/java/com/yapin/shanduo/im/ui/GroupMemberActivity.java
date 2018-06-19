package com.yapin.shanduo.im.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.tencent.qcloud.ui.TemplateTitle;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.model.GroupMemberProfile;
import com.yapin.shanduo.model.entity.IMGroupUserInfo;
import com.yapin.shanduo.presenter.GroupMebPresenter;
import com.yapin.shanduo.ui.adapter.GroupMebAdapter;
import com.yapin.shanduo.ui.contract.GroupMebContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.LoadMoreRecyclerView;
import com.yapin.shanduo.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupMemberActivity extends Activity implements GroupMebContract.View ,LoadMoreRecyclerView.OnLoadMoreListener , GroupMebAdapter.OnItemClickListener{

    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;
    @BindView(R.id.loading_view)
    LoadingView loadingView;

    GroupMebAdapter adapter;
    List<IMGroupUserInfo.GroupMebInfo> list = new ArrayList<>();
    ListView listView;
    TemplateTitle title;
    String groupId, type;
    private final int MEM_REQ = 100;
    private final int CHOOSE_MEM_CODE = 200;
    private int memIndex;

    private GroupMebPresenter presenter;
    private Context context;
    private Activity activity;
    private int page = 1;
    private boolean isLoading = false;
    private IMGroupUserInfo.GroupMebInfo footerItem = new IMGroupUserInfo.GroupMebInfo();
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member);
        ButterKnife.bind(this);
        presenter = new GroupMebPresenter();
        presenter.init(this);
    }

    @Override
    public void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = this;

        title = (TemplateTitle) findViewById(R.id.group_mem_title);
        groupId = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");

        layoutManager = new LinearLayoutManager(context);
        footerItem.setType(Constants.TYPE_FOOTER_LOAD);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new GroupMebAdapter(context , activity , list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                memIndex = position;
//                Intent intent = new Intent(GroupMemberActivity.this, GroupMemberProfileActivity.class);
//                GroupMemberProfile profile = (GroupMemberProfile) list.get(position);
//                intent.putExtra("data", profile);
//                intent.putExtra("groupId", groupId);
//                intent.putExtra("type", type);
//                startActivityForResult(intent, MEM_REQ);
//            }
//        });

        presenter.getData(groupId , page+"");

    }

    @Override
    public void show(List<IMGroupUserInfo.GroupMebInfo> data, int totalPage) {
        if (!isLoading) {
            if (totalPage == 0) {
                loadingView.noData(R.string.tips_no_credit);
            } else {
                loadingView.setGone();
            }
            list.clear();
//            list.add(footerItem);
        }
//        recyclerView.setPage(page, totalPage);
//        footerItem.setType(page < totalPage ? Constants.TYPE_FOOTER_LOAD : Constants.TYPE_FOOTER_FULL);
        list.addAll(data);
        adapter.notifyDataSetChanged();
        setRefreshLoading( false);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(GroupMemberActivity.this, GroupMemberProfileActivity.class);
        IMGroupUserInfo.GroupMebInfo info = list.get(position);
        info.setRoleType(Utils.getRoleType(info.getRole()));
        intent.putExtra("data", info);
        intent.putExtra("groupId", groupId);
        intent.putExtra("type", type);
        startActivityForResult(intent, MEM_REQ);
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
    public void loading() {

    }

    @Override
    public void networkError() {
        setRefreshLoading( false);
    }

    @Override
    public void error(String msg) {
        setRefreshLoading( false);
        ToastUtil.showShortToast(context, msg);
    }

    @Override
    public void showFailed(String msg) {
        setRefreshLoading( false);
        ToastUtil.showShortToast(context, msg);
    }

    @Override
    public void onLoadMore() {
        page++;
        setRefreshLoading(true);
        presenter.getData(groupId , page+"");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (MEM_REQ == requestCode) {
            if (resultCode == RESULT_OK) {
                boolean isKick = data.getBooleanExtra("isKick", false);
                if (isKick) {
                    list.remove(memIndex);
                    adapter.notifyDataSetChanged();
                } else {
                    page = 1;
                    presenter.getData(groupId , page+"");
                }
            }
        }
    }
}
