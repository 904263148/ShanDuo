package com.yapin.shanduo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.yapin.shanduo.R;

/**
 * 作者：L on 2018/5/17 0017 14:07
 */
//自定义view
public class QuickIndexBar extends View {

    //画笔
    private Paint paint;

    String[] lettres={"↑","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S"
            ,"T","U","V","W","X","Y","Z","#"};

    //自定义view的宽高
    private int viewHight;
    private int viewWidth;
    //每个文本的高度；（文本高度等于view高度除以文本个数）
    private int cellHeight;

    //文本的高度
    private float textHeight;

    private int currentIndex=-1;

    public OnLetterChangeListen onLetterChangeListen;

    public OnLetterChangeListen getOnLetterChangeListen(){
        return onLetterChangeListen;
    }


    public void setOnLetterChangeListener(OnLetterChangeListen onLetterChangeListen){
        this.onLetterChangeListen=onLetterChangeListen;
    }
    //自定义一个字母改变的监听
    public interface OnLetterChangeListen{
        void onLetterChange(String letter);
        void onResert();
    }

    public QuickIndexBar(Context context) {
        this(context,null);
    }
    public QuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint=new Paint();
        //设置字体颜色；默认为黑色；我们这里使用黑色
        //paint.setColor(Color.WHITE);
        //设置字体大小
        paint.setTextSize(30);
        //抗锯齿
        paint.setAntiAlias(true);
        //获取字体宽高，因为每个字体宽度不一样，所以获得宽度必须放在循环中做
        //首先获取字体的属性
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        //下边界 - 上边界
        textHeight = (float) Math.ceil(fontMetrics.descent-fontMetrics.ascent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取文本的宽高
//   getTextWH();
        //每个文本的高度；
        cellHeight=viewHight/lettres.length;

        //通过循环把字母画出来
        for(int x=0;x<lettres.length;x++){
            String text=lettres[x];

            //paint给我们提供了一个测量字体宽度的方法
            float textWidth = paint.measureText(text);
            if(currentIndex==x){
                //当点击某个字母的时候变色
                paint.setColor(getResources().getColor(R.color.home_title_color));
            }else{
                paint.setColor(Color.BLACK);
            }

            /*
             * 参数1：要画的内容   参数23：要画的位置   参数4：画笔
             * 参数23所画的位置指的是字母左下角坐标
             */
            canvas.drawText(text,viewWidth/2-textWidth/2,cellHeight/2+textHeight/2+cellHeight*x,paint);
        }

    }

    //测量view的宽高
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHight =getMeasuredHeight();
        viewWidth =getMeasuredWidth();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                //计算当前点击的字母，宽度不用考虑，因为宽度都是一样的，计算高度即可，根据你点击或者移动的高度计算你当前所点击的是哪个字母
                float downY=event.getY();
                currentIndex = (int)downY/cellHeight;
                if(currentIndex<0 || currentIndex>lettres.length-1){
                }else{
                    if(onLetterChangeListen!=null){
                        onLetterChangeListen.onLetterChange(lettres[currentIndex]);
                    }
                }
                //重新绘制;相当于重新调用onDraw()方法
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY=event.getY();
                currentIndex = (int)moveY/cellHeight;
                if(currentIndex<0 || currentIndex>lettres.length-1){
                }else{
                    if(onLetterChangeListen!=null){
                        onLetterChangeListen.onLetterChange(lettres[currentIndex]);
                    }
                }
                //重新绘制
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                currentIndex=-1;
                if(onLetterChangeListen!=null){
                    onLetterChangeListen.onResert();
                }
                break;

            default:
                break;
        }
        return true;
    }

}

