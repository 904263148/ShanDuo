package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tencent.TIMConversationType;
import com.tencent.qcloud.sdk.Constant;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.ui.ChatActivity;
import com.yapin.shanduo.model.entity.FriendInfo;
import com.yapin.shanduo.model.entity.User;
import com.yapin.shanduo.presenter.LinkManPresenter;
import com.yapin.shanduo.ui.adapter.LinkFriendAdapter;
import com.yapin.shanduo.ui.contract.LinkManContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.widget.LoadingView;
import com.yapin.shanduo.widget.QuickIndexBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinkFriendFragment extends Fragment implements LinkManContract.View , AdapterView.OnItemClickListener{


    @BindView(R.id.quick_index_bar)
    QuickIndexBar quickIndexBar;
    @BindView(R.id.tv_hint)
    TextView tvHint;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.loading_view)
    LoadingView loadingView;

    private Context context;
    private Activity activity;

    private ArrayList<User> list;

    private List<FriendInfo.fInfo> fInfoList = new ArrayList<>();
    private LinkFriendAdapter adapter;
    private LinkManPresenter presenter;

    public LinkFriendFragment() {
        // Required empty public constructor
    }

    public static LinkFriendFragment newInstance() {
        LinkFriendFragment fragment = new LinkFriendFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_link_friend, container, false);
        ButterKnife.bind(this, view);
        presenter = new LinkManPresenter();
        presenter.init(this);
        return view;
    }

    @Override
    public void initView() {

        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();
        if(TextUtils.isEmpty(PrefUtil.getToken(context))){
            loadingView.noData(R.string.tips_no_login);
        }else {
            presenter.getData(Constants.ALL_FRIEND);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(PrefUtil.getToken(context))){
            loadingView.noData(R.string.tips_no_login);
        }else {
            presenter.getData(Constants.ALL_FRIEND);
        }
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0 ; i < fInfoList.size() ; i++) {
            list.add(new User(fInfoList.get(i)));
        }
        Collections.sort(list); // 对list进行排序，需要让User实现Comparable接口重写compareTo方法
        adapter = new LinkFriendAdapter(context,activity , list ,fInfoList);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);

        //给自定义view设置自定义监听，当点击到某个字母的时候弹出吐司显示这个字母；
        //当点击字母的时候遍历集合，找到首字母为点击字母的HaoHan的下标，让listview跳转到响应位置
        quickIndexBar.setOnLetterChangeListener(new QuickIndexBar.OnLetterChangeListen() {

            @Override
            public void onLetterChange(String letter) {

                quickIndexBar.setBackgroundColor(getResources().getColor(R.color.bg_color));

                tvHint.setVisibility(View.VISIBLE);
                tvHint.setText(letter);

                if(letter.equals("↑")){
                    listview.setSelection(0);
                    return;
                }

                for (int i = 0; i < list.size(); i++) {
                    if (letter.equalsIgnoreCase(list.get(i).getFirstLetter())) {
                        listview.setSelection(i); // 选择到首字母出现的位置
                        return;
                    }
                }
            }

            @Override
            //当手指弹起的时候此方法执行
            public void onResert() {
                tvHint.setVisibility(View.GONE);
                quickIndexBar.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
    }

    @Override
    public void success(List<FriendInfo.fInfo> data) {
        if(data == null || data.size() == 0){
            loadingView.noData(R.string.tips_no_friend);
        }else {
            loadingView.setGone();
        }
        fInfoList.clear();
        fInfoList = data;
        initData();
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {

    }

    @Override
    public void error(String msg) {

    }

    @Override
    public void showFailed(String msg) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ChatActivity.navToChat(activity , list.get(position).getUserId()+"" , TIMConversationType.C2C);
        Log.d("TIM_user_id",list.get(position).getUserId()+"");
    }
}
