package com.yapin.shanduo.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.yapin.shanduo.presenter.PublishTrendPresenter;
import com.yapin.shanduo.presenter.UploadPresenter;
import com.yapin.shanduo.ui.adapter.ShowPictureAdapter;
import com.yapin.shanduo.ui.contract.PublishTrendContract;
import com.yapin.shanduo.ui.contract.UploadContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.PictureUtil;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.ScrollGridLayoutManager;
import com.yapin.shanduo.widget.ShowAllRecyclerView;

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
public class PublishTrendActivity extends BaseActivity implements ShowPictureAdapter.OnItemClickListener , PublishTrendContract.View , View.OnClickListener, UploadContract.View{

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
            showPopwindow();
            return;
        }
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
                if (listShow!=null) {
                    listShow.remove(0);
                    uploadPresenter.upload(listShow);
                }else{
                    publishTrend("");
                }

                break;
            case R.id.ib_pd_Location:       //定位
                Intent intent =new Intent(activity ,MapGaodeActivity.class);
                startActivityForResult(intent , 19);
                break;

            case R.id.ib_pick_photo:        //拍摄
                PublishTrendActivityPermissionsDispatcher.showAllWithCheck(PublishTrendActivity.this);
                break;

            case R.id.ib_take_photo:    //相册
                Bundle bundle = new Bundle();
                bundle.putInt("left", Constants.COUNT_MAX_SHOW_PICTURE - listShow.size());
                bundle.putInt("source", 0);
                StartActivityUtil.start(activity, PictureFolderActivity.class, bundle , Constants.REQUEST_CODE_FOR_SELECT_PHOTO_SHOW );
                break;

        }
    }

    //绑定定位选择的数据
    public void setText(String data){
        tv_pd_address.setText(data);
        tv_pd_address.setVisibility(View.VISIBLE);
    }


    private void showPopwindow() {
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(this, R.layout.cameraselection, null);

        Button btnCamera = (Button) popView.findViewById(R.id.btn_take_photo);
        Button btnAlbum = (Button) popView.findViewById(R.id.btn_pick_photo);
        Button btnCancel = (Button) popView.findViewById(R.id.btn_cancel);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        final PopupWindow popWindow = new PopupWindow(popView,width,height);
        popWindow.setAnimationStyle(R.style.take_photo_anim);
        popWindow.setFocusable(true);
//        popWindow.setBackgroundDrawable(newBitmapDrawable());// 响应返回键必须的语句。
        //设置点击弹窗外隐藏自身
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_take_photo:
                        Bundle bundle = new Bundle();
                        bundle.putInt("left", Constants.COUNT_MAX_SHOW_PICTURE - listShow.size());
                        bundle.putInt("source", 0);
                        StartActivityUtil.start(activity, PictureFolderActivity.class, bundle , Constants.REQUEST_CODE_FOR_SELECT_PHOTO_SHOW );
                        popWindow.dismiss();
                        lighton();
                        break;
                    case R.id.btn_pick_photo:
                        PublishTrendActivityPermissionsDispatcher.showAllWithCheck(PublishTrendActivity.this);
                        popWindow.dismiss();
                        lighton();
                        break;
                    case R.id.btn_cancel:
                        popWindow.dismiss();
                        lighton();
                        break;
                }
                popWindow.dismiss();
            }
        };

        btnCamera.setOnClickListener(listener);
        btnAlbum.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);

        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        setBackgroundAlpha(0.5f);
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    /**
     * 设置手机屏幕亮度显示正常
     */
    private void lighton() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
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
                paths.add(PictureUtil.compressPicture(PictureUtil.currentPhotoPath));
                break;
            }
            case Constants.RELEASEDYNAMICPOSITIONING:{
            //获取定位信息
            setText(data.getStringExtra("Title"));
            textlonlat = data.getStringExtra("textlonlat");
            String []ary = textlonlat.split("\\,");

            lon=ary[0];
            lat=ary[1];
//            Log.i("test","地址是：--"+tv_pd_address+"--经纬度是："+textlonlat);
            break;
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
        ToastUtil.showShortToast(context,"发表成功");
        finish();
    }

    @Override
    public void uploadSuccess(String imgIds) {
        publishTrend(imgIds);
    }

    public void publishTrend(String imgIds){
        content=tv_pd_text.getText().toString().trim();
        presenter.publish(content , imgIds, lon, lat, location);
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {
        ToastUtil.showShortToast(context,"网络连接异常");
    }

    @Override
    public void error(String msg) {
//        ToastUtil.showShortToast(context,msg);
    }

    @Override
    public void showFailed(String msg) {

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

}
