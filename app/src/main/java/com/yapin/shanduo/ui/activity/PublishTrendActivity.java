package com.yapin.shanduo.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.ui.ImagePreviewActivity;
import com.yapin.shanduo.presenter.PublishTrendPresenter;
import com.yapin.shanduo.presenter.UploadPresenter;
import com.yapin.shanduo.ui.adapter.ShowPictureAdapter;
import com.yapin.shanduo.ui.contract.PublishTrendContract;
import com.yapin.shanduo.ui.contract.UploadContract;
import com.yapin.shanduo.ui.fragment.SelectPhotoDialogFragment;
import com.yapin.shanduo.utils.BitmapUtils;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.PictureUtil;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.ScrollGridLayoutManager;
import com.yapin.shanduo.widget.ShowAllRecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by dell on 2018/5/12.
 */

@RuntimePermissions
public class PublishTrendActivity extends RightSlidingActivity implements ShowPictureAdapter.OnItemClickListener , PublishTrendContract.View , View.OnClickListener, UploadContract.View , SelectPhotoDialogFragment.ImageCropListener{

    public static final int TYPE_SHOW = 0;

    private PublishTrendPresenter presenter;
    private Context context;
    private Activity activity;

    @BindView(R.id.tv_pd_cancel)    //取消
    TextView tv_pd_cancel;
    @BindView(R.id.tv_pd_text)  //动态内容
    EditText tv_pd_text;
    @BindView(R.id.ib_pd_Location)  //定位
    ImageButton ib_pd_Location;
    @BindView(R.id.ib_pd_Expression)    //表情
    ImageButton ib_pd_Expression;
    @BindView(R.id.tv_pd_Publish)   //发表
    TextView tv_pd_Publish;
    @BindView(R.id.rv_show)
    ShowAllRecyclerView rvShow;
    @BindView(R.id.tv_pd_address)       //当前位置显示
    TextView tv_pd_address;

    String content = "";
    String location = "";
    String textlonlat;
    String lat = null;
    String lon = null;

    private List<String> listShow = new ArrayList<>();
    private ShowPictureAdapter showAdapter;

    private UploadPresenter uploadPresenter;
    private SelectPhotoDialogFragment selectPhotoDialogFragment;//..拍照或者选择图片的fragment
    //调用照相机返回图片文件
    private File tempFile;

    private Dialog loadDialog;//...加载

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishingdynamics);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);
        presenter = new PublishTrendPresenter();
        presenter.init(context,this);
        uploadPresenter = new UploadPresenter();
        uploadPresenter.init(context ,this);

        tv_pd_address.setVisibility(View.GONE);

    }

    @Override
    public void initView(){

        loadDialog = ToastUtil.showLoading(activity);

        selectPhotoDialogFragment = new SelectPhotoDialogFragment();
        selectPhotoDialogFragment.setImagePathListener(this);

        tv_pd_Publish.setText(Html.fromHtml("<u>发表</u>"));
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
                ToastUtil.showShortToast(context, context.getString(R.string.toast_count_picture));
                return;
            }
            selectPhotoDialogFragment.show(getSupportFragmentManager() , "1");
            selectPhotoDialogFragment.setPicSize(Constants.COUNT_MAX_SHOW_PICTURE - listShow.size());
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("path", listShow.get(position));
        bundle.putInt("position", position);
        StartActivityUtil.start(activity, PictureReviewActivity.class, bundle, Constants.REQUEST_CODE_FOR_DELETE_PHOTO_SHOW);
    }

    @OnClick({R.id.tv_pd_Publish,R.id.tv_pd_cancel,R.id.ib_pd_Location ,R.id.ib_pick_photo , R.id.ib_take_photo })
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_pd_cancel:         //取消
                finish();
                break;
            case R.id.tv_pd_Publish:        //发布
                location = tv_pd_address.getText().toString().trim();
                if (lon==null){
                    lon = PrefUtil.getLon(context);
                }
                if (lat==null){
                    lat = PrefUtil.getLat(context);
                }
                if (listShow.size() >= 2) {
                    listShow.remove(0);
                    uploadPresenter.upload(listShow);
                }else{
                    loading();
                    publishTrend("");
                }

                break;
            case R.id.ib_pd_Location:       //定位
                Intent intent =new Intent(activity ,MapGaodeActivity.class);
                startActivityForResult(intent , 19);
                break;
        }
    }

    //绑定定位选择的数据
    public void setText(String data){
        tv_pd_address.setText(data);
        tv_pd_address.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK)
            return;
        List<String> paths = new ArrayList<>();
        switch (requestCode) {
            case Constants.REQUEST_CODE_FOR_SELECT_PHOTO_SHOW: {
                if (data == null) {
                    return;
                }
                paths.addAll(data.getStringArrayListExtra("path"));
//                presenter.upload(paths);
                break;
            }
            case Constants.REQUEST_CODE_FOR_TAKE_PHOTO_SHOW:{
//                paths.add(PictureUtil.compressPicture(PictureUtil.currentPhotoPath));
                paths.add(BitmapUtils.compressImage(tempFile.toString()));
                break;
            }
            case Constants.RELEASEDYNAMICPOSITIONING:{
            //获取定位信息
            setText(data.getStringExtra("Title"));
            textlonlat = data.getStringExtra("textlonlat");
            String []ary = textlonlat.split("\\,");

            lat=ary[0];
            lon=ary[1];
//            Log.i("test","地址是：--"+tv_pd_address+"--经纬度是："+textlonlat);
            break;
            }
            case Constants.REQUEST_CODE_FOR_DELETE_PHOTO_SHOW: {
                if (data == null) {
                    return;
                }
                int position = data.getIntExtra("position", 1);
                showAdapter.notifyItemRemoved(position);
                listShow.remove(position);
                return;
            }
        }
        show(paths);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void show(List<String> paths){
        int size = listShow.size();
        listShow.addAll(paths);
        showAdapter.notifyItemRangeInserted(size, listShow.size() - size);
    }

    @Override
    public void success(String data) {
        loadDialog.dismiss();
        ToastUtil.showShortToast(context,"发表成功");
        finish();
    }

    @Override
    public void uploadSuccess(String imgIds) {
        publishTrend(imgIds);
    }

    public void publishTrend(String imgIds){
        content=tv_pd_text.getText().toString().trim();
        presenter.publish(content , imgIds, lat, lon, location);
    }

    @Override
    public void loading() {
        loadDialog.show();
    }

    @Override
    public void networkError() {
        ToastUtil.showShortToast(context,"网络连接异常");
    }

    @Override
    public void error(String msg) {
        loadDialog.dismiss();
        ToastUtil.showShortToast(context,msg);
    }

    @Override
    public void showFailed(String msg) {
        loadDialog.dismiss();
        ToastUtil.showShortToast(context , msg);
    }


    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void showAll() {
        Utils.takePhoto(activity, Constants.REQUEST_CODE_FOR_TAKE_PHOTO_SHOW);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PublishTrendActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void onShowRationale(final PermissionRequest request) {
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void onDenied() {
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE})
    void onAnyOneNeverAskAgain() {
    }

    @Override
    public void setImagePath(File path) {
        this.tempFile = path;
    }
}
