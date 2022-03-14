package com.wlsj2021.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.wlsj2021.myapplication.R;
import com.wlsj2021.myapplication.msg.EventBusMsg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
@BindView(R.id.home_img)
    ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);




       Bitmap bitmapH = BitmapFactory.decodeResource(getResources(),
               R.drawable.a1);
        Matrix matrix = new Matrix();
        matrix.setSinCos(0.2f,0.2f);
        Bitmap mBitmap = Bitmap.createBitmap(bitmapH, 0, 0, bitmapH.getWidth(),
                bitmapH.getHeight(), matrix, true);

        mImageView.setImageBitmap(mBitmap);

//        bitmap.recycle();
//
//        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.a7);
//        matrix.reset();
//        matrix.setSkew(0.5f,0.5f);
//        Bitmap bitmap2 = Bitmap.createBitmap(bitmap1, 0, 0, bitmap1.getWidth(), bitmap1.getHeight(), matrix, true);
//        mImageView.setImageBitmap(bitmap2);


//自由变换
        float[] src = new float[] { 0, 0, // 左上
                bitmapH.getWidth(), 0,// 右上
                bitmapH.getWidth(), bitmapH.getHeight(),// 右下
                0, bitmapH.getHeight() };// 左下
        float[] dst = new float[] { 0, 0,
                bitmapH.getWidth()-100, 50,
                bitmapH.getWidth()-50, bitmapH.getHeight() - 50,
                0,bitmapH.getHeight() };
        matrix.setPolyToPoly(src, 0, dst, 0, src.length/2);
        Bitmap mBitmap2 = Bitmap.createBitmap(bitmapH, 0, 0, bitmapH.getWidth(),
                bitmapH.getHeight(), matrix, true);
//        mImageView.setImageBitmap(mBitmap2);



//打印点
        String string = "";
        float[] values = new float[9];
        matrix.getValues(values);
        for (int i = 0; i < values.length; i++) {
            string += "matrix.at" + i + "=" + values[i];
        }
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
        Log.e("TAG", string);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventBusMsg event) {

        Log.e("TAG", "onMessageEvent: "+event.getMsgName(), null);
        Toast.makeText(this, "-------"+event.getMsgCode(), Toast.LENGTH_SHORT).show();

        /* Do something */};


    @Override
    protected void onStart() {
        super.onStart();
        //注册成功
        Log.e("TAG", "注册成功: ", null);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }
}