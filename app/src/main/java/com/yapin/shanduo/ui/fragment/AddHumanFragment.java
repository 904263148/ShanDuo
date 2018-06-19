package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.ui.SearchFriendActivity;
import com.yapin.shanduo.ui.activity.SearchAllActivity;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.StartActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddHumanFragment extends Fragment {

    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.ll_add_link)
    LinearLayout llAddLink;
    @BindView(R.id.ll_add_wechat)
    LinearLayout llAddWechat;
    @BindView(R.id.ll_add_qq)
    LinearLayout llAddQq;
    @BindView(R.id.listview)
    ListView listview;

    private View view;
    private Context context;
    private Activity activity;

    public AddHumanFragment() {
        // Required empty public constructor
    }

    public static AddHumanFragment newInstance() {
        AddHumanFragment fragment = new AddHumanFragment();
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
        view = inflater.inflate(R.layout.fragment_add_human, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();
    }

    @OnClick({R.id.ll_search})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_search:
                Bundle bundle = new Bundle();
                bundle.putString("type" , Constants.SEARCH_USER);
                StartActivityUtil.start(activity , this , SearchAllActivity.class , bundle);
                break;
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
}
