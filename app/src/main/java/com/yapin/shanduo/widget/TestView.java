package com.yapin.shanduo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.yapin.shanduo.R;

/**
 * 作者：L on 2018/7/2 0002 17:29
 */
public class TestView extends View{

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(getResources().getColor(R.color.home_svip_color));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        paint.setAntiAlias(true);
        canvas.drawCircle(500 ,500 ,200 , paint);

    }
}
