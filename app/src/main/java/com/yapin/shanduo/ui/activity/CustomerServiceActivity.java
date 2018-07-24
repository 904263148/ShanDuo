package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.tencent.TIMConversationType;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.ui.ChatActivity;
import com.yapin.shanduo.model.entity.CustomerServiceInfo;
import com.yapin.shanduo.presenter.CustomerServicePresenter;
import com.yapin.shanduo.ui.adapter.CustomerAdapter;
import com.yapin.shanduo.ui.contract.CustomerServiceContract;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：L on 2018/7/23 0023 17:35
 */
public class CustomerServiceActivity extends RightSlidingActivity implements CustomerServiceContract.View{

    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.loading_view)
    LoadingView loadingView;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    private CustomerAdapter adapter;
    private Context context;
    private List<CustomerServiceInfo.Customer> list = new ArrayList<>();

    private CustomerServicePresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);
        ButterKnife.bind(this);

        presenter = new CustomerServicePresenter();
        presenter.init(this);

    }

    @Override
    public void initView() {
        context = ShanDuoPartyApplication.getContext();
        final Activity activity = this;

        adapter = new CustomerAdapter(context , activity , R.layout.link_friend_item , list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChatActivity.navToChat(activity , list.get(position).getUserId()+"" , TIMConversationType.C2C , 1);
                Log.d("TIM_user_id",list.get(position).getUserId()+"");
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        presenter.load();
    }

    @Override
    public void show(List<CustomerServiceInfo.Customer> data) {
        if(data == null || data.size() == 0){
            loadingView.noData(R.string.tips_no_customer);
        }else {
            loadingView.setGone();
            list.clear();
            list.addAll(data);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {
        loadingView.loadError();
    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortToast(context , msg);
    }

    @Override
    public void showFailed(String msg) {
        loadingView.loadError();
        ToastUtil.showShortToast(context , msg);
    }

}
