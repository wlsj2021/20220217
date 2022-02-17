package com.wlsj2021.myapplication.Custom;

import android.view.animation.Interpolator;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: wlsj
 * @date: 2020/02/20
 * Time: 20:03
 */
public class CustomScaleInterpolator implements Interpolator {
    private float elasticFactor;

    public CustomScaleInterpolator(float elasticFactor) {
        this.elasticFactor = elasticFactor;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (Math.pow(2, -10 * input) * Math.sin((input - elasticFactor / 4) * (2 * Math.PI) / elasticFactor) + 1);
    }
}
