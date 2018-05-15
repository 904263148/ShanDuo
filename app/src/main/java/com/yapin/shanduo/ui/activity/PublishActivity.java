package com.yapin.shanduo.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.Toolbar;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.okhttp.JavaOkCallback;
import com.yapin.shanduo.okhttp.OkHttp;
import com.yapin.shanduo.presenter.UploadPresenter;
import com.yapin.shanduo.ui.adapter.ShowPictureAdapter;
import com.yapin.shanduo.ui.contract.UploadContract;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.ScrollGridLayoutManager;
import com.yapin.shanduo.widget.ShowAllRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class PublishActivity extends BaseActivity implements ShowPictureAdapter.OnItemClickListener , UploadContract.View{

    public static final int TYPE_SHOW = 0;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_show)
    ShowAllRecyclerView rvShow;

    private Context context;
    private Activity activity;

    private List<String> listShow = new ArrayList<>();

    private ShowPictureAdapter showAdapter;

    private UploadPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        context = ShanDuoPartyApplication.getContext();
        activity = this;

        presenter = new UploadPresenter();
        presenter.init(context , this);
    }

    @Override
    public void initView(){

        setToolbar(toolbar , "发布活动");

        listShow.add("");
        ScrollGridLayoutManager layoutManager1 = new ScrollGridLayoutManager(this, 4);
        layoutManager1.setScrollEnabled(false);
        rvShow.setLayoutManager(layoutManager1);
        rvShow.setItemAnimator(new DefaultItemAnimator());
        showAdapter = new ShowPictureAdapter(context, activity, listShow);
        showAdapter.setSource(TYPE_SHOW);
        showAdapter.setOnItemClickListener(this);
        rvShow.setAdapter(showAdapter);
    }

    @Override
    public void onItemClick(int position, int source) {
        if (position == 0) {
            if (listShow.size() == Constants.COUNT_MAX_SHOW_PICTURE) {
                ToastUtil.showShortToast(context, "");
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("left", Constants.COUNT_MAX_SHOW_PICTURE - listShow.size());
            bundle.putInt("source", 0);
            StartActivityUtil.start(activity, PictureFolderActivity.class, bundle , Constants.REQUEST_CODE_FOR_SELECT_PHOTO_SHOW );
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case Constants.REQUEST_CODE_FOR_SELECT_PHOTO_SHOW: {
                if (data == null) {
                    return;
                }
                List<String> paths = new ArrayList<>();
                paths.addAll(data.getStringArrayListExtra("path"));
                show(paths);
                presenter.upload(paths);
                break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void show(List<String> paths){
        int size = listShow.size();
        listShow.addAll(paths);
        showAdapter.notifyItemRangeInserted(size, listShow.size() - size);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void showAll() {
        Utils.takePhoto(activity, Constants.REQUEST_CODE_FOR_TAKE_PHOTO_SHOW);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PublishActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void showRationaleForAnyOne(final PermissionRequest request) {
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void onAnyOneNeverAskAgain() {
    }

    @Override
    public void uploadSuccess(String imgIds) {
        ToastUtil.showShortToast(context , imgIds);
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
}
