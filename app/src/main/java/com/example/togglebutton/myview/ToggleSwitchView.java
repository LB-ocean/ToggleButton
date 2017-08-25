package com.example.togglebutton.myview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.togglebutton.R;
import com.example.togglebutton.util.MyLog;


/**
 * Created by li biao
 * 2017/8/23
 * email:207563927@qq.com
 */

/**
 * 大概思路:
 * 1.先画开关两边半圆
 * 2.画中间矩形区域
 * 3.画开关圆点
 * 4.点击开关时改变 圆点所处位置(左 和 右)
 */
public class ToggleSwitchView extends View
{
    private Paint paint_arch ,paint_dot;
    private int nWidth,nHeight;
    private boolean openState = true;
    private  float radius,xPosition;
    boolean isFocus;
    private int i =0;
    private int paintWidth,animatorTime,dotGap;
    private int switchCloseBg,switchOpenBg,dotBg;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            MyLog.e("ToggleButton---handleMessage()");
            i++;
            if(isFocus)
            {
                i = 0;
                showTurnState(openState);
            }
            else
            {
                if(i<20)
                {
                    MyLog.e("ToggleButton---handleMessage()----i:"+i);
                    handler.sendEmptyMessageDelayed(1,2);
                }

            }
        }
    };
    public ToggleSwitchView(Context context)
    {
        super(context);
        init();
    }

    public ToggleSwitchView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        attrs(context, attrs);
        init();
    }

    public ToggleSwitchView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    private void attrs(Context context, AttributeSet attrs)
    {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ToggleView);
        switchCloseBg =typedArray.getColor(R.styleable.ToggleView_switchCloseBg,Color.GRAY);
        switchOpenBg =typedArray.getColor(R.styleable.ToggleView_switchOpenBg,Color.GREEN);
        dotBg =typedArray.getColor(R.styleable.ToggleView_dotBg,Color.BLUE);
        dotGap = (int) typedArray.getDimension(R.styleable.ToggleView_dotGap,10);
        paintWidth = (int) typedArray.getDimension(R.styleable.ToggleView_paintWidth,2);
        animatorTime = typedArray.getInteger(R.styleable.ToggleView_animatorTime,1000);
    }
    private void init()
    {
        MyLog.e("ToggleSwitchView--init");
        isFocus = false;
        paint_arch = new Paint();
        paint_arch.setAntiAlias(true);
        paint_arch.setColor(switchCloseBg);
        paint_arch.setStrokeWidth(paintWidth);
        paint_arch.setStyle(Paint.Style.FILL_AND_STROKE);

        paint_dot = new Paint();
        paint_dot.setAntiAlias(true);
        paint_dot.setColor(dotBg);
        paint_dot.setStrokeWidth(paintWidth);
        paint_dot.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec) ;
        int heightSize = MeasureSpec.getSize(heightMeasureSpec) ;
        setMeasuredDimension(widthSize,heightSize);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        nWidth = w;
        nHeight = h;
        radius = (float) (Math.min(nWidth,nHeight)/2*0.8);
        xPosition=radius/2;
        isFocus = true;
        MyLog.e("ToggleSwitchView--onSizeChanged-  nHeight:"+h+";nWidth:"+w);
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        MyLog.e("ToggleSwitchView--onDraw");
        canvas.translate(nWidth/2,nHeight/2);
        canvas.scale(1,-1);
        if(openState)
        {
            paint_arch.setColor(switchCloseBg);
        }
        else
        {
            paint_arch.setColor(switchOpenBg);
        }

        RectF rectFLeft = new RectF(-radius,-radius/2,0,radius/2);
        canvas.drawArc(rectFLeft,90,180,true,paint_arch);
        RectF rectCentre = new RectF(-radius/2,-radius/2,radius/2,radius/2);
        canvas.drawRect(rectCentre,paint_arch);
        RectF rectFRight = new RectF(0,-radius/2,radius,radius/2);
        canvas.drawArc(rectFRight,90,-180,true,paint_arch);
        canvas.drawCircle(xPosition,0,radius/2-dotGap,paint_dot);
    }

    public void changeOpenState(boolean state)
    {
        MyLog.e("ToggleSwitchView--changeOpenState");
        if(openState !=state)
        {
            openState =state;
            if(isFocus)
            {
                showTurnState(openState);
            }

        }



    }
    private void showTurnState(boolean state)
    {
        float start = state?-radius/2:radius/2;
        float end = state?radius/2:-radius/2;
        ValueAnimator animator = ValueAnimator.ofFloat(start,end);
        animator.setDuration(animatorTime);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                xPosition = (float) animation.getAnimatedValue();
                MyLog.e("ToggleSwitchView----xPosition:"+xPosition);
                invalidate();
            }
        });
        animator.start();
    }


}
