package com.wlsj2021.postwang.base;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;


import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.wlsj2021.postwang.R;


import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;


/**
 * Created with Android Studio.
 * Description: 静态工具方法
 *
 * @author: wlsj
 * @date: 2019/12/14
 * Time: 17:00
 */
public class Utils {


    /**
     * 获取主题颜色
     *
     * @param context
     * @return
     */
    public static int getColor(Context context) {
        SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(context);

        int defaultColor = ContextCompat.getColor(context, R.color.black);
        int color = setting.getInt("color", defaultColor);
        if (color != 0 && Color.alpha(color) != 255) {
            return defaultColor;
        } else {
            return color;
        }
    }

    /**
     * 设置主题颜色
     *
     * @param context
     * @param color
     */
    public static void setColor(Context context, int color) {
        SharedPreferences setting = PreferenceManager.getDefaultSharedPreferences(context);
        setting.edit().putInt("color", color).apply();
    }

    /**
     * BottomNavigation 适配颜色
     *
     * @param context
     * @return
     */
    public static final ColorStateList getColorStateList(Context context) {
//        Intrinsics.checkParameterIsNotNull(context, "context");

        int[] colors = new int[]{getColor(context), ContextCompat.getColor(context, R.color.design_default_color_primary)};
        int[][] states = new int[][]{{android.R.attr.state_checked, android.R.attr.state_checked}, new int[0]};
        return new ColorStateList(states, colors);
    }




    /**
     * 获取状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }


    /**
     * dp与px转换
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dpToPx(Context context, int dp) {
        float density;
        density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    /**
     * px转换为dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int pxToDp(Context context, float pxValue) {
        if (context == null) {
            return -1;
        }

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取ActionBar高度
     *
     * @param context
     * @return
     */
    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        int actionBarHeight = 0;
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }
        return actionBarHeight;
    }

    /**
     * 显示SnackBar
     *
     * @param activity
     * @param msg
     */
    public static void showSnackMessage(Activity activity, String msg) {
        final Snackbar snackbar = Snackbar.make(activity.getWindow().getDecorView(), msg, Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(ContextCompat.getColor(activity, R.color.white));
        snackbar.setActionTextColor(ContextCompat.getColor(activity, R.color.white));
        view.setBackgroundColor(getColor(activity));
        snackbar.setAction("知道了", v -> {
            snackbar.dismiss();
        }).show();
        snackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
            }
        });
    }

    /**
     * 设置震动
     *
     * @param context
     * @param milliseconds
     */
    public static void Vibrate(Context context, long milliseconds) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }
}
