package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.TokenInfo;
import com.yapin.shanduo.presenter.SearchHumanPresenter;
import com.yapin.shanduo.ui.adapter.UserProfAdapter;
import com.yapin.shanduo.ui.contract.SearchHumanContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAllActivity extends RightSlidingActivity implements SearchHumanContract.View , View.OnKeyListener , AdapterView.OnItemClickListener{

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
    @BindView(R.id.list)
    ListView listView;

    private Context context;
    private Activity activity;

    private SearchHumanPresenter presenter;
    private UserProfAdapter adapter;

    private List<TokenInfo> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_all);
        ButterKnife.bind(this);
        presenter = new SearchHumanPresenter();
        presenter.init(this);
    }

    @Override
    public void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = this;

        String type = getIntent().getStringExtra("type");

        adapter = new UserProfAdapter(context ,R.layout.link_friend_item ,list);
        listView.setAdapter(adapter);
        inputSearch.setOnKeyListener(this);
        listView.setOnItemClickListener(this);

        TextView tvCancel = (TextView) findViewById(R.id.cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
                presenter.getData(key , Constants.SEARCH_USER);
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    public void show(List<TokenInfo> data) {
        if(data == null) return;
        list.clear();
        list.addAll(data);
        adapter.notifyDataSetChanged();
        if (list.size() == 0){
            noResult.setVisibility(View.VISIBLE);
        }else{
            noResult.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString("userId" , list.get(position).getUserId());
        StartActivityUtil.start(activity , UserProfActivity.class , bundle);
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {

    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortToast(context , msg);
    }

    @Override
    public void showFailed(String msg) {
        ToastUtil.showShortToast(context , msg);
    }
}
