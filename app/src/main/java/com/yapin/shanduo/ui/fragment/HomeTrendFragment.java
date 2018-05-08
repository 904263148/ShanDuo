package com.yapin.shanduo.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.ui.activity.MyCodeActivity;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class HomeTrendFragment extends Fragment {

    private Context context;
    private Activity activity;
    private View view;

    public HomeTrendFragment() {
        // Required empty public constructor
    }

    public static HomeTrendFragment newInstance() {
        HomeTrendFragment fragment = new HomeTrendFragment();
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
        view = inflater.inflate(R.layout.fragment_home_trend, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    public void initView(){
        activity = getActivity();
        context = ShanDuoPartyApplication.getContext();

    }

    @OnClick(R.id.bt)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt:
                Bundle bundle = new Bundle();
                bundle.putString("code","Hello World!!!");
                StartActivityUtil.start(activity , MyCodeActivity.class , bundle);
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

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void showAll() {
        ToastUtil.showShortToast(context ,"i wanna take u camera");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        HomeTrendFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void showWhy(final PermissionRequest request) {
        ToastUtil.showShortToast(context , "because i wanna take photo...");
        request.proceed();
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void denied() {
        return;
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void notAsk() {
    }
}
