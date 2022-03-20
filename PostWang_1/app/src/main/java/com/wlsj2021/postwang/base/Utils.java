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

}
