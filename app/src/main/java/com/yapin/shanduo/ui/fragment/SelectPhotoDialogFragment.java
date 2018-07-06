package com.yapin.shanduo.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yapin.shanduo.BuildConfig;
import com.yapin.shanduo.R;
import com.yapin.shanduo.ui.activity.PictureFolderActivity;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.FileUtil;
import com.yapin.shanduo.utils.StartActivityUtil;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 作者：L on 2018/6/15 0015 11:07
 */
public class SelectPhotoDialogFragment extends BottomSheetDialogFragment {

    private View view;
    private Activity activity;

    //调用照相机返回图片文件
    private File tempFile;

    //请求相机
    private static final int REQUEST_CAPTURE = 105;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;

    private ImageCropListener listener;

    private int open_type;

    private int pic_size;//...用于记录发布动态页面已有图片数量

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        open_type = Integer.parseInt(getTag());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.select_photo_layout, null);
        ButterKnife.bind(this, view);
        activity = getActivity();
        return view;
    }

    public void setPicSize(int pic_size){
        this.pic_size = pic_size;
    }

    @OnClick({R.id.tv_select_album , R.id.tv_take_pic , R.id.tv_cancel})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_select_album:
                //权限判断
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                }else {
                    //跳转到相册
                    if(open_type == 1){
                        Bundle bundle = new Bundle();
                        bundle.putInt("left", pic_size);
                        bundle.putInt("source", 0);
                        StartActivityUtil.start(activity, PictureFolderActivity.class, bundle , Constants.REQUEST_CODE_FOR_SELECT_PHOTO_SHOW );
                    }else {
                        gotoPhoto();
                    }
                }
                dismiss();
                break;
            case R.id.tv_take_pic:
                //权限判断
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统相机
                    gotoCamera();
                }
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    /**
     * 外部存储权限申请返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCamera();
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoPhoto();
            }
        }
    }

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }


    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");

        listener.setImagePath(tempFile);

        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        activity.startActivityForResult(intent,open_type == 1 ? Constants.REQUEST_CODE_FOR_TAKE_PHOTO_SHOW : REQUEST_CAPTURE);
    }

    public interface ImageCropListener{
        void setImagePath(File path);
    }

    public void setImagePathListener(ImageCropListener listener){
        this.listener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
