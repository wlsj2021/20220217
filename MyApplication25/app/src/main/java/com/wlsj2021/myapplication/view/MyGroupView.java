package com.wlsj2021.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wlsj2021.myapplication.R;

public class MyGroupView extends RelativeLayout {
    private ImageView mImageView;
    private TextView mTextView;


    public MyGroupView(Context context) {
        super(context);
    }

    public MyGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.my_group_view,this);
        mImageView = findViewById(R.id.my_image_group);
        mTextView = findViewById(R.id.my_text_group);
    }

    public MyGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyGroupView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    public void setTextViewText(String textViewText){
        mTextView.setText(textViewText);
    }
    public void setImageViewListener(OnClickListener listener){
        mImageView.setOnClickListener(listener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount()>0){
            View childView = getChildAt(0);
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(getChildCount()>0){
            View childView = getChildAt(0);
            childView.layout(0,0,childView.getMeasuredWidth()
                    ,childView.getMeasuredHeight());
        }
    }
}
