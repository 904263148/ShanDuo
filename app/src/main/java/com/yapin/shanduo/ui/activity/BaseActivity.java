package com.yapin.shanduo.ui.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.yapin.shanduo.utils.ActivityTransitionUtil;
import com.yapin.shanduo.utils.Constants;


public class BaseActivity extends AppCompatActivity{

    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    private int isEvent; //右滑关闭页面设置

    private ImmersionBar mImmersionBar; //沉浸式

    View decorView;
    int screenWidth;//屏宽
    float startX,startY,endX,endY,distanceX,distanceY;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mImmersionBar = ImmersionBar.with(this);
//        mImmersionBar.init();

        decorView=getWindow().getDecorView();
        DisplayMetrics metrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        screenWidth=metrics.widthPixels;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityTransitionUtil.finishActivityTransition(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                startX=event.getX();
//                startY=event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                endX=event.getX();
//                endY=event.getY();
//                distanceX=endX-startX;
//                distanceY=Math.abs(endY-startY);
//                //1.判断手势右滑  2.横向滑动的距离要大于竖向滑动的距离
//                if(endX-startX>0&&distanceY<distanceX){
//                    decorView.setX(distanceX);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                endX=event.getX();
//                distanceX=endX-startX;
//                endY=event.getY();
//                distanceY=Math.abs(endY-startY);
//                //1.判断手势右滑  2.横向滑动的距离要大于竖向滑动的距离 3.横向滑动距离大于屏幕三分之一才能finish
//                if(endX-startX>0&&distanceY<distanceX&&distanceX>screenWidth/3){
//                    moveOn(distanceX);
//                }
//                //1.判断手势右滑  2.横向滑动的距离要大于竖向滑动的距离 但是横向滑动距离不够则返回原位置
//                else if(endX-startX>0&&distanceY<distanceX){
//                    backOrigin(distanceX);
//                }else{
//                    decorView.setX(0);
//                }
//                break;
//        }
//        return super.dispatchTouchEvent(event);
//    }

    /**
     * 返回原点
     * @param distanceX 横向滑动距离
     */
    private void backOrigin(float distanceX){
        ObjectAnimator.ofFloat(decorView,"X",distanceX,0).setDuration(300).start();
    }
    /**
     * 划出屏幕
     * @param distanceX 横向滑动距离
     */
    private void moveOn(float distanceX){
        ValueAnimator valueAnimator=ValueAnimator.ofFloat(distanceX,screenWidth);
        valueAnimator.setDuration(300);
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                decorView.setX((Float) animation.getAnimatedValue());
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                onBackPressed();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//            //当手指按下的时候
//            x1 = event.getX();
//            y1 = event.getY();
//        }
//        if(event.getAction() == MotionEvent.ACTION_UP) {
//            //当手指离开的时候
//            x2 = event.getX();
//            y2 = event.getY();
//            //右滑
//            if(x2 - x1 > 50) {
//                if(getIsEvent() == Constants.IS_EVENT)
//                    onBackPressed();
//            }
//        }
//        return super.onTouchEvent(event);
//    }

    public int getIsEvent() {
        return isEvent;
    }

    public void setIsEvent(int isEvent) {
        this.isEvent = isEvent;
    }

    /**
     * 设置toolbar
     *
     * @param toolbar toolbar
     * @param titleId 标题资源
     */
    public void setToolbar(Toolbar toolbar, int titleId) {
        toolbar.setTitle(titleId);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * 设置toolbar
     *
     * @param toolbar toolbar
     * @param title 标题内容
     */
    public void setToolbar(Toolbar toolbar, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}
