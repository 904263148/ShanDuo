package com.yapin.shanduo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.yapin.shanduo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 成长值进度条
 */

public class GrowthValueProgress extends View {

    private MyCallback callback;

    private Context context;

    private int currentValues = 0;//当前成长值

    private int v0Values = 1;//v0会员成长值

    private int v1Values = 300;//v1会员成长值

    private int v2Values = 900;//v2会员成长值

    private int v3Values = 1800;//v3会员成长值

    private int v4Values = 3600;//v4会员成长值

    private int v5Values = 7200;//v5会员成长值

    private int v6Values = 14400;//v6会员成长值

    private int v7Values = 28799;//v7会员成长值

    private Paint paint;//会员画笔

    private Paint grayPaint;

    private Paint pointPaint0;

    private Paint pointPaint1;

    private Paint pointPaint2;

    private Paint pointPaint3;

    private Paint pointPaint4;

    private Paint pointPaint5;

    private Paint pointPaint6;

    private Paint pointPaint7;

    private int lineHeight = 6;//线的高度

    private int pointSize = 6;//圆心的半径

    private int offsetX = 0;//x的坐标;

    private int width = 0;

    private int hight = 8;

    private List<Paint> paintList;

    public GrowthValueProgress(Context context) {
        super(context);
        this.context = context;
        initPaint();
    }

    public GrowthValueProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initPaint();
    }

    public GrowthValueProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initPaint();
    }

    public int getCurrentValues() {
        return currentValues;
    }

    public void setCurrentValues(int currentValues) {
        this.currentValues = currentValues;
    }

    private void initPaint() {

        lineHeight=hight;//线的高度设置为布局的一半高度
        pointSize=hight;//圆点的半径设置为布局的一半高度

        grayPaint = new Paint();
        grayPaint.setColor(0x7060090);
        grayPaint.setStrokeWidth(lineHeight);
        grayPaint.setAntiAlias(true);
        grayPaint.setTextAlign(Paint.Align.CENTER);
        grayPaint.setStyle(Paint.Style.STROKE);

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(lineHeight);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setStyle(Paint.Style.STROKE);

        pointPaint0 = new Paint();
        pointPaint1 = new Paint();
        pointPaint2 = new Paint();
        pointPaint3 = new Paint();
        pointPaint4 = new Paint();
        pointPaint5 = new Paint();
        pointPaint6 = new Paint();
        pointPaint7 = new Paint();

        paintList=new ArrayList<>();
        paintList.add(pointPaint0);
        paintList.add(pointPaint1);
        paintList.add(pointPaint2);
        paintList.add(pointPaint3);
        paintList.add(pointPaint4);
        paintList.add(pointPaint5);
        paintList.add(pointPaint6);
        paintList.add(pointPaint7);
        for (int i = 0; i < paintList.size(); i++) {
            Paint mPaint = paintList.get(i);
            mPaint.setStrokeWidth(10);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextAlign(Paint.Align.CENTER);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int lineLength = width / 7;

        //绘制底部长灰线
        canvas.drawLine(0, lineHeight, width, lineHeight, grayPaint);

        drawProgress(canvas, lineLength);
    }


    /**
     * 画进度
     *
     * @param canvas
     * @param lineLength 每个区间的平均长度
     */
    private void drawProgress(Canvas canvas, int lineLength) {

        //在V0~V1区间内
        if (currentValues >= v0Values  && currentValues < v1Values) {
            int stopX = (currentValues - v0Values) * lineLength / (v1Values - v0Values);
            //x起始位置，y起始位置，x停止位置，y停止位置
            offsetX = lineLength * 0 + stopX;
            pointPaint0.setColor(Color.RED);
            pointPaint1.setColor(0x7060090);
            pointPaint2.setColor(0x7060090);
            pointPaint3.setColor(0x7060090);
            pointPaint4.setColor(0x7060090);
            pointPaint5.setColor(0x7060090);
            pointPaint6.setColor(0x7060090);
            pointPaint7.setColor(0x7060090);
        } else if (currentValues >= v1Values && currentValues < v2Values) {
            //在V1~V2区间内
            int stopX = (currentValues - v1Values) * lineLength / (v2Values - v1Values);
            offsetX = lineLength * 1 + stopX;
            pointPaint0.setColor(Color.RED);
            pointPaint1.setColor(Color.RED);
            pointPaint2.setColor(0x7060090);
            pointPaint3.setColor(0x7060090);
            pointPaint4.setColor(0x7060090);
            pointPaint5.setColor(0x7060090);
            pointPaint6.setColor(0x7060090);
            pointPaint7.setColor(0x7060090);
        } else if (currentValues >= v2Values && currentValues < v3Values) {
            //在V2~V3区间内
            int stopX = (currentValues - v2Values) * lineLength / (v3Values - v2Values);
            offsetX = lineLength * 2 + stopX;
            pointPaint0.setColor(Color.RED);
            pointPaint1.setColor(Color.RED);
            pointPaint2.setColor(Color.RED);
            pointPaint3.setColor(0x7060090);
            pointPaint4.setColor(0x7060090);
            pointPaint5.setColor(0x7060090);
            pointPaint6.setColor(0x7060090);
            pointPaint7.setColor(0x7060090);
        } else if (currentValues >= v3Values && currentValues <= v4Values) {
            //在V3~V4区间内
            int stopX = (currentValues - v3Values) * lineLength / (v4Values - v3Values);
            offsetX = lineLength * 3 + stopX;
            pointPaint0.setColor(Color.RED);
            pointPaint1.setColor(Color.RED);
            pointPaint2.setColor(Color.RED);
            pointPaint3.setColor(Color.RED);
            pointPaint4.setColor(0x7060090);
            pointPaint5.setColor(0x7060090);
            pointPaint6.setColor(0x7060090);
            pointPaint7.setColor(0x7060090);
        } else if (currentValues >= v4Values && currentValues <= v5Values) {
            //在V4~V5区间内
            int stopX = (currentValues - v4Values) * lineLength / (v5Values - v4Values);
            offsetX = lineLength * 4 + stopX;
            pointPaint0.setColor(Color.RED);
            pointPaint1.setColor(Color.RED);
            pointPaint2.setColor(Color.RED);
            pointPaint3.setColor(Color.RED);
            pointPaint4.setColor(Color.RED);
            pointPaint5.setColor(0x7060090);
            pointPaint6.setColor(0x7060090);
            pointPaint7.setColor(0x7060090);
        }else if (currentValues >= v5Values && currentValues <= v6Values) {
            //在V5~V6区间内
            int stopX = (currentValues - v5Values) * lineLength / (v6Values - v5Values);
            offsetX = lineLength * 5 + stopX;
            pointPaint0.setColor(Color.RED);
            pointPaint1.setColor(Color.RED);
            pointPaint2.setColor(Color.RED);
            pointPaint3.setColor(Color.RED);
            pointPaint4.setColor(Color.RED);
            pointPaint5.setColor(Color.RED);
            pointPaint6.setColor(0x7060090);
            pointPaint7.setColor(0x7060090);
        } else if (currentValues >= v6Values && currentValues <= v7Values) {
            //在V6~V7区间内
            int stopX = (currentValues - v6Values) * lineLength / (v7Values - v6Values);
            offsetX = lineLength * 6 + stopX;
            pointPaint0.setColor(Color.RED);
            pointPaint1.setColor(Color.RED);
            pointPaint2.setColor(Color.RED);
            pointPaint3.setColor(Color.RED);
            pointPaint4.setColor(Color.RED);
            pointPaint5.setColor(Color.RED);
            pointPaint6.setColor(Color.RED);
            pointPaint7.setColor(0x7060090);
        }else if (currentValues > v7Values) {
            int stopX = 0;//超过8使用固定值
            offsetX = lineLength * 7 + stopX;
            pointPaint0.setColor(Color.RED);
            pointPaint1.setColor(Color.RED);
            pointPaint2.setColor(Color.RED);
            pointPaint3.setColor(Color.RED);
            pointPaint4.setColor(Color.RED);
            pointPaint5.setColor(Color.RED);
            pointPaint6.setColor(Color.RED);
            pointPaint7.setColor(Color.RED);
        }

        canvas.drawLine(0, lineHeight, offsetX, lineHeight, paint);


        //圆心的XY坐标，圆心半径
        canvas.drawCircle((0 * lineLength)+7 , pointSize, pointSize, pointPaint0);
        canvas.drawCircle(1 * lineLength - pointSize, pointSize, pointSize, pointPaint1);
        canvas.drawCircle(2 * lineLength - pointSize, pointSize, pointSize, pointPaint2);
        canvas.drawCircle(3 * lineLength - pointSize, pointSize, pointSize, pointPaint3);
        canvas.drawCircle(4 * lineLength - pointSize, pointSize, pointSize, pointPaint4);
        canvas.drawCircle(5 * lineLength - pointSize, pointSize, pointSize, pointPaint5);
        canvas.drawCircle(6 * lineLength - pointSize, pointSize, pointSize, pointPaint6);
        canvas.drawCircle((7 * lineLength)+3 - pointSize, pointSize, pointSize, pointPaint7);

        if (callback != null) {
            callback.callBack(offsetX,currentValues);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = this.getMeasuredWidth();
        hight = this.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void getOffsetX(MyCallback callback){
        this.callback=callback;
    }

    public interface MyCallback {
        void callBack(int offsetX, int currentValues);
    }

}