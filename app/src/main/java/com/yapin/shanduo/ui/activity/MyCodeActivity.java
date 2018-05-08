package com.yapin.shanduo.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.utils.BitmapUtils;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.FileUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCodeActivity extends BaseActivity {

    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.save_img)
    Button saveImg;

    public Bitmap mBitmap = null;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_code);
        ButterKnife.bind(this);

        context = ShanDuoPartyApplication.getContext();
        mBitmap = CodeUtils.createImage(getIntent().getStringExtra("code"), 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        ivCode.setImageBitmap(mBitmap);

        saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    BitmapUtils.saveImageToSD(context, FileUtil.getSDCardBasePath() + Constants.PICTURE_PATH, "code.jpg", mBitmap, 100);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }



}
