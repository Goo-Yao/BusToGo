package com.rdc.bustogo.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;

import com.rdc.bustogo.R;


/**
 * 动画管理类
 */
public class AnimationManager {
    private static int mShowMenuTime = 200;
    public static final int TO_HIDE = 0;
    public static final int TO_SHOW = 1;

    /**
     * 旋转打开动画
     *
     * @param fabView
     * @return
     */
    public static Animation getFABRotateToOpenAnim(View fabView) {
        Animation rotateAnimation = new RotateAnimation(0, -270,
                fabView.getWidth() / 2, fabView.getHeight() / 2);
        rotateAnimation.setDuration(mShowMenuTime);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new OvershootInterpolator());
        return rotateAnimation;
    }

    /**
     * 旋转关闭动画
     *
     * @param fabView
     * @return
     */
    public static Animation getFABRotateToCloseAnim(View fabView) {
        Animation rotateAnimation = new RotateAnimation(-270, 0,
                fabView.getWidth() / 2, fabView.getHeight() / 2);
        rotateAnimation.setDuration(mShowMenuTime);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new OvershootInterpolator());
        return rotateAnimation;
    }


    /**
     * 缩放显示动画
     *
     * @param context
     * @return
     */
    public static Animation getScaleToShowAnim(Context context) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale_to_show);
        animation.setDuration(mShowMenuTime);
        animation.setInterpolator(new OvershootInterpolator());  // 设置插值器
        return animation;
    }

    /**
     * 缩放隐藏动画
     *
     * @param context
     * @return
     */
    public static Animation getScaleToHideAnim(Context context) {
        Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_to_hide);
        scaleAnimation.setDuration(mShowMenuTime);
        scaleAnimation.setFillAfter(true);
        return scaleAnimation;
    }

    /**
     * @param v
     * @param type
     * @return
     */
    public static ValueAnimator getAlphaAnimator(final View v, int type) {
        int beginValue, endValue;
        if (type == TO_HIDE) {
            beginValue = 1;
            endValue = 0;
        } else {
            beginValue = 0;
            endValue = 1;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(beginValue, endValue);
        valueAnimator.setDuration(mShowMenuTime);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float tempValue = (float) animation.getAnimatedValue();
                v.setAlpha(tempValue);
            }
        });
        return valueAnimator;
    }
}
