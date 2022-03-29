package com.wlsj2021.postwang.base;

import android.view.animation.Interpolator;

public class ElasticScaleInterpolator implements Interpolator {
    private float elasticFactor;

    public ElasticScaleInterpolator(float elasticFactor) {
        this.elasticFactor = elasticFactor;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (Math.pow(2, -10 * input) * Math.sin((input - elasticFactor / 4) * (2 * Math.PI) / elasticFactor) + 1);
    }
}