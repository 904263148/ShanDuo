package com.yapin.shanduo.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.model.entity.PhotoFolder;
import com.yapin.shanduo.ui.adapter.PictureFolderAdapter;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class PictureFolderActivity extends BaseActivity implements PictureFolderAdapter.OnItemClickListener{

    public static final int SOURCE_FOR_RADIO = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<PhotoFolder> list = new ArrayList<>(); //获取所有照片的路径
    private Context context;
    private Activity activity;
    private int source;
    private int left;
    private PictureFolderAdapter adapter;

    private PictureHandler pictureHandler;

    private Map<String, PhotoFolder> map;

    private ProgressDialog dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_folder);
        ButterKnife.bind(this);

        initView();

        PictureFolderActivityPermissionsDispatcher.showAllWithCheck(this);
    }

    private void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        source = getIntent().getIntExtra("source", SOURCE_FOR_RADIO);
        left = getIntent().getIntExtra("left", -1);
        setToolbar(toolbar, "");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new PictureFolderAdapter(context, list);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        pictureHandler = new PictureHandler(this);
        dialog = new ProgressDialog(this);
    }

    @Override
    public void onItemClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("source", source);
        bundle.putInt("left", left);
        bundle.putStringArrayList("list", (ArrayList<String>) list.get(position).getList());
        StartActivityUtil.start(activity, PictureActivity.class, bundle, Constants.REQUEST_CODE_FOR_SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        switch (requestCode) {
            case Constants.REQUEST_CODE_FOR_SELECT_PHOTO:
                setResult(RESULT_OK, data);
                finish();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void showAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                map = Utils.getPhotos(context);
                pictureHandler.sendEmptyMessage(map.isEmpty() ? 0 : 1);
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PictureFolderActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private static class PictureHandler extends Handler {
        private WeakReference<PictureFolderActivity> weakReference;

        PictureHandler(PictureFolderActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final PictureFolderActivity activity = weakReference.get();
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    activity.dialog.dismiss();

                    Set<String> keys = activity.map.keySet();
                    for (String key : keys) {
                        if (!Constants.ALL_PHOTO.equals(key)) {
                            activity.list.add(activity.map.get(key));
                        }
                    }
                    activity.adapter.notifyItemRangeInserted(0, activity.list.size());
                    break;
                default:
                    activity.dialog.dismiss();
            }
        }
    }

    @OnPermissionDenied(Constants.PERMISSIONS_STORAGE)
    void deniedAnyOne() {
        finish();
    }

    @OnShowRationale(Constants.PERMISSIONS_STORAGE)
    void showRationaleForAnyOne(PermissionRequest request) {
        request.proceed();
    }

    @OnNeverAskAgain(Constants.PERMISSIONS_STORAGE)
    void onAnyOneNeverAskAgain() {
    }

}
