package com.yapin.shanduo.ui.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureReviewActivity extends BaseActivity  {

    @BindView(R.id.iv_picture)
    ImageView ivPicture;

    private Context context;
    private String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_review);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        context = ShanDuoPartyApplication.getContext();
        id = getIntent().getStringExtra("path");

        Glide.with(this).load(id).asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        ivPicture.setImageBitmap(resource);
                    }
                });

        findViewById(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, getIntent());
                onBackPressed();
            }
        });

        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
