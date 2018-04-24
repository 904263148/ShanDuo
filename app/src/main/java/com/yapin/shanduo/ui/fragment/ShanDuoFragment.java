package com.yapin.shanduo.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapin.shanduo.R;

import butterknife.ButterKnife;

public class ShanDuoFragment extends Fragment {

    public static ShanDuoFragment newInstance() {
        ShanDuoFragment fragment = new ShanDuoFragment();
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
        View view = inflater.inflate(R.layout.fragment_shan_duo_layout,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

}
