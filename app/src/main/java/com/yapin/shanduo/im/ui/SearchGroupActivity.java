package com.yapin.shanduo.im.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.IMGroupInfo;
import com.yapin.shanduo.presenter.SearchGroupPresenter;
import com.yapin.shanduo.ui.activity.RightSlidingActivity;
import com.yapin.shanduo.ui.adapter.LinkGroupAdapter;
import com.yapin.shanduo.ui.contract.SearchGroupContract;
import com.yapin.shanduo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchGroupActivity extends RightSlidingActivity implements View.OnKeyListener, SearchGroupContract.View {

    private final String TAG = "SearchGroupActivity";
    @BindView(R.id.inputSearch)
    EditText searchInput;
    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.noResult)
    TextView noResult;

    private List<IMGroupInfo.GroupInfo> list = new ArrayList<>();
    private LinkGroupAdapter adapter;
    private Context context;
    private Activity activity;
    private SearchGroupPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_group);
        ButterKnife.bind(this);
        presenter = new SearchGroupPresenter();
        presenter.init(this);
    }

    @Override
    public void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        adapter = new LinkGroupAdapter(context, activity, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, GroupProfileActivity.class);
                intent.putExtra("identify", list.get(position).getGroupId());
                startActivity(intent);
            }
        });
        TextView tvCancel = (TextView) findViewById(R.id.cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchInput.setOnKeyListener(this);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP) {   // 忽略其它事件
            return false;
        }

        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                list.clear();
                adapter.notifyDataSetChanged();
                String key = searchInput.getText().toString();
                if (key.equals("")) return true;
                presenter.getData(key);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void show(List<IMGroupInfo.GroupInfo> data) {
            noResult.setVisibility(View.GONE);
            list.addAll(data);
            adapter.notifyDataSetChanged();
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {

    }

    @Override
    public void error(String msg) {
        noResult.setVisibility(View.VISIBLE);
        ToastUtil.showShortToast(context , msg);
    }

    @Override
    public void showFailed(String msg) {
        ToastUtil.showShortToast(context , msg);
    }

}
