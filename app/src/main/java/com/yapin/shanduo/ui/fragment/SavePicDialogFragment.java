package com.yapin.shanduo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.GlideUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：L on 2018/6/22 0022 11:50
 */
public class SavePicDialogFragment extends BottomSheetDialogFragment {

    private View view;
    private Context context;
    private String imgUrl;

    public static SavePicDialogFragment newInstance(String url) {
        Bundle args = new Bundle();
        SavePicDialogFragment fragment = new SavePicDialogFragment();
        args.putString("imgUrl" , url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgUrl = getArguments().getString("imgUrl");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_save_pic, null);
        ButterKnife.bind(this, view);
        context = ShanDuoPartyApplication.getContext();
        return view;
    }

    @OnClick({R.id.tv_save_pic })
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_save_pic:
                GlideUtil.getBitmap(context , ApiUtil.IMG_URL + imgUrl , imgUrl);
                dismiss();
                break;
        }
    }

}
