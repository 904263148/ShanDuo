package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.ui.CreateGroupActivity;
import com.yapin.shanduo.utils.StartActivityUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddGroupFragment extends Fragment {

    private View view;
    private Context context;
    private Activity activity;

    public AddGroupFragment() {
        // Required empty public constructor
    }

    public static AddGroupFragment newInstance() {
        AddGroupFragment fragment = new AddGroupFragment();
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
        view = inflater.inflate(R.layout.fragment_add_group, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    public void initView(){
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();
    }

    @OnClick({R.id.ll_create_group})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.ll_create_group:
                Bundle bundle = new Bundle();
                bundle.putString("type","Public");
                StartActivityUtil.start(activity , this , CreateGroupActivity.class , bundle);
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

}
