package com.yapin.shanduo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yapin.shanduo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingView extends FrameLayout {
    @BindView(R.id.tv_tips)
    TextView tvTips;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private Context context;

    public LoadingView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        if (isInEditMode())
            return;
        View view = LayoutInflater.from(context).inflate(R.layout.widge_loading, this, true);
        ButterKnife.bind(view);

    }

    public void loadError() {
        setVisibility(VISIBLE);
        tvTips.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        tvTips.setText("网络异常");
    }

    public void noData(int tipsId) {
        setVisibility(VISIBLE);
        tvTips.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        tvTips.setText(context.getString(tipsId));
    }

    public void setGone() {
        setVisibility(GONE);
    }

    public void loading() {
        setVisibility(VISIBLE);
        progressBar.setVisibility(VISIBLE);
        tvTips.setVisibility(GONE);
    }

}