package com.wlsj2021.postwang.custom;

import android.content.Context;
import android.content.res.Resources;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wlsj2021.postwang.R;
import com.youth.banner.loader.ImageLoader;

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {

        Glide.with(context).load(path).placeholder(R.drawable.black_background).into(imageView);
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

}
