package com.yapin.shanduo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：L on 2018/5/18 0018 17:43
 */
public class MyView extends View{
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        defalutSize = dp_px(defalutSize);
        wPaint=new Paint();
        wPaint.setAntiAlias(true);
        wPaint.setColor(Color.WHITE);
        wPaint.setAlpha(50);//透明度，取值范围为0~255，数值越小越透明
        wPaint.setStyle(Paint.Style.STROKE);
        wPaint.setStrokeWidth(10);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private int radus;//半径
    private int defalutSize ;//默认大小
    private Paint wPaint;//外环画笔

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        final RectF rf=new RectF(getPaddingLeft(),getPaddingTop(),getWidth()-getPaddingRight(),getHeight()-getPaddingBottom());
        //绘制外环
        canvas.drawArc(rf,-195,210,false,wPaint);//总360
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int width,height;
        if(wMode == MeasureSpec.EXACTLY){
            width = wSize;
        }else{
            width=Math.min(wSize,defalutSize);
        }

        if(hMode == MeasureSpec.EXACTLY){
            height = hSize;
        }else{
            height=Math.min(hSize,defalutSize);
        }
        radus = width/2;
        setMeasuredDimension(width,height);
    }

    /**
     * dp转px
     *
     * @param values
     * @return
     */
    public int dp_px(int values)
    {

        float density = getResources().getDisplayMetrics().density;
        return (int) (values * density + 0.5f);
    }








}
