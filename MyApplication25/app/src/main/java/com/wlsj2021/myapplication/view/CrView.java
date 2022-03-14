package com.wlsj2021.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.wlsj2021.myapplication.R;
//首先继承自View 或者 ViewGroup 将构造写出来
public class CrView extends androidx.appcompat.widget.AppCompatTextView {

    private float moveX;
    private float moveY;

    private String mString = "";
    private int mColor;
    private int mSize;
    private Paint mPaint;

//new 一个对象的时候调用
    public CrView(Context context) {
        super(context,null);
    }
//在第二个构造写 可以直接作用在XML
    public CrView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
        //通过TypedArray从XML获取到对应的属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CrView);

        //拿到对应文本
        mString = typedArray.getString(R.styleable.CrView_MyText);
        //拿到对应的Color
        mColor = typedArray.getColor(R.styleable.CrView_MyColor, Color.RED);
        //拿到对应的Size
        mSize = typedArray.getDimensionPixelSize(R.styleable.CrView_MySize, 20);


        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "???", Toast.LENGTH_LONG).show();
            }
        });

    }

    public CrView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



////确定view的位置
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//    }
//计算view的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 1. EXACTLY
         * 表示父视图希望子视图的大小应该是由specSize的值来决定的，
         * 系统默认会按照这个规则来设置子视图的大小，
         * 简单的说（当设置width或height为match_parent时，
         * 模式为EXACTLY，因为子view会占据剩余容器的空间，所以它大小是确定的）
         * 2. AT_MOST
         * 表示子视图最多只能是specSize中指定的大小。
         * （当设置为wrap_content时，模式为AT_MOST,
         * 表示子view的大小最多是多少，这样子view会根据这个上限来设置自己的尺寸）
         * 3. UNSPECIFIED
         * 表示开发人员可以将视图按照自己的意愿设置成任意的大小，
         * 没有任何限制。这种情况比较少见，不太会用到。
         *
         *
         */
        setMeasuredDimension
                (measureWidth(widthMeasureSpec),
                        measureHeight(heightMeasureSpec));
    }
    private int measureWidth(int widthMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            //这样，当时用wrap_content时，View就得到一个默认值200px，而不是填充整个父布局。
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }
    private int measureHeight(int heightMeasureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            //这样，当时用wrap_content时，View就得到一个默认值200px，而不是填充整个父布局。
            result = 200;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        return result;
    }



//画一个view
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//创建一个画笔
        mPaint = new Paint();
        //将color属性设置到画笔
        mPaint.setColor(mColor);
        //将size属性设置到画笔
        mPaint.setTextSize(mSize);
        //在画布上画text
        canvas.drawText(mString,100,100,mPaint);
        //在画布上画方型
        canvas.drawRect(100,100,99,10,mPaint);
        mPaint.setColor(Color.YELLOW);
//        canvas.drawCircle(100,100,100,mPaint);
//        canvas.drawRect(100,100,10,10,mPaint);
    }
/**
 * 手指到屏幕


 */
//当事件被消费会直接调用
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moveX = event.getX();
                 moveY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                setTranslationX(getX() + (event.getX() - moveX));
                setTranslationY(getY() + (event.getY() - moveY));
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return true;
    }


//会生成一个事务
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }
//viewGroup比view多一个拦截的方法
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        return super.onInterceptTouchEvent(ev);
//    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
