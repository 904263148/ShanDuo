package com.yapin.shanduo.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinkManFragment extends Fragment {

    private Context context;
    private Activity activity;

    public static LinkManFragment newInstance() {
        LinkManFragment fragment = new LinkManFragment();
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
        View view = inflater.inflate(R.layout.fragment_linkman_layout,container,false);
        ButterKnife.bind(this,view);
        context = ShanDuoPartyApplication.getContext();
        activity = getActivity();
        return view;
    }

}
