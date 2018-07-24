package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.utils.StartActivityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstUseActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tv_skip)
    TextView tvSkip;

    private int[] imgs = {R.drawable.icon_first_img1 , R.drawable.icon_first_img2};

    private Context context;
    private Activity activity;

    private ImmersionBar mImmersionBar; //沉浸式

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_use);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        context = ShanDuoPartyApplication.getContext();
        activity = this;

        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();

        ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartActivityUtil.start(activity , MainActivity.class);
                finish();
            }
        });

    }

    private class ViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return imgs.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(imgs[position]);
            container.addView(imageView);
            return imageView;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();
    }

}
