package com.yapin.shanduo.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.ui.adapter.PictureAdapter;
import com.yapin.shanduo.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class PictureActivity extends BaseActivity implements PictureAdapter.OnItemClickListener {
    public static final int SOURCE_FOR_RADIO = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private List<String> list = new ArrayList<>(); //获取所有照片的路径
    private Context context;
    private int source;
    private int left;
    private PictureAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_folder);
        ButterKnife.bind(this);

        initView();

        PictureActivityPermissionsDispatcher.showAllWithCheck(this);
    }

    private void initView() {
        context = ShanDuoPartyApplication.getContext();
        source = getIntent().getIntExtra("source", SOURCE_FOR_RADIO);
        left = getIntent().getIntExtra("left", -1);

//        setToolbar(toolbar, source == SOURCE_FOR_RADIO ? context.getString(R.string.title_select_picture) : context.getString(R.string.picture_count, 0, left));

        setToolbar(toolbar , "选择图片");

        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new PictureAdapter(this, context, list);
        adapter.setOnClickListener(this);
        adapter.setSource(source);
        adapter.setLeft(left);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        if (source == SOURCE_FOR_RADIO) {
            Intent intent = new Intent();
            intent.putExtra("path", list.get(position));
            setResult(RESULT_OK, intent);
            onBackPressed();
            return;
        }
//        toolbar.setTitle(context.getString(R.string.picture_count, adapter.getSelectList().size(), left));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done_action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return source != SOURCE_FOR_RADIO;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_done:
                Intent intent = new Intent();
                intent.putExtra("path", adapter.getSelectList());
                setResult(RESULT_OK, intent);
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void showAll() {
        list.addAll(getIntent().getStringArrayListExtra("list"));
        adapter.notifyItemRangeInserted(0, list.size());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PictureActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
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
