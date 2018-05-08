package com.yapin.shanduo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.widget.progressbar.CircularProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FooterLoading extends RelativeLayout {

    @BindView(R.id.loading_progress_bar)
    CircularProgressBar loadingProgressBar;
    @BindView(R.id.load_full)
    TextView loadFull;

    public FooterLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FooterLoading(Context context) {
        super(context);
        initView(context);
    }

    public FooterLoading(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_loading_more, this, true);

        ButterKnife.bind(view);
    }

    public void onLoad(boolean isFull) {
        loadingProgressBar.setVisibility(isFull ? GONE : VISIBLE);
        loadFull.setVisibility(isFull ? VISIBLE : GONE);
    }
}
