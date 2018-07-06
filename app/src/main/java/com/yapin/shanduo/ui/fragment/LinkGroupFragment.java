package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.ui.GroupProfileActivity;
import com.yapin.shanduo.model.entity.IMGroupInfo;
import com.yapin.shanduo.presenter.GroupListPresenter;
import com.yapin.shanduo.ui.adapter.LinkGroupAdapter;
import com.yapin.shanduo.ui.contract.GroupListContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.widget.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LinkGroupFragment extends Fragment implements GroupListContract.View{

    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.loading_view)
    LoadingView loadingView;

    private View view;
    private Context context;
    private Activity activity;
    private GroupListPresenter presenter;
    private List<IMGroupInfo.GroupInfo> list = new ArrayList<>();
    private LinkGroupAdapter adapter;

    public LinkGroupFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LinkGroupFragment newInstance() {
        LinkGroupFragment fragment = new LinkGroupFragment();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_link_group, container, false);
        ButterKnife.bind(this, view);
        presenter = new GroupListPresenter();
        presenter.init(this);
        return view;
    }

    @Override
    public void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();

        adapter = new LinkGroupAdapter(context ,activity ,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, GroupProfileActivity.class);
                intent.putExtra("identify", list.get(position).getGroupId());
                startActivity(intent);
            }
        });

        if(TextUtils.isEmpty(PrefUtil.getToken(context))){
            loadingView.noData(R.string.tips_no_login);
        }else {
            presenter.getData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(PrefUtil.getToken(context))){
            loadingView.noData(R.string.tips_no_login);
        }else {
            presenter.getData();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void show(List<IMGroupInfo.GroupInfo> data) {
        if(data.size() == 0){
            loadingView.noData(R.string.tips_no_group);
        }else {
            loadingView.setGone();
        }
        list.clear();
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
        ToastUtil.showShortToast(context , msg);
    }

    @Override
    public void showFailed(String msg) {
        ToastUtil.showShortToast(context , msg);
    }
}
