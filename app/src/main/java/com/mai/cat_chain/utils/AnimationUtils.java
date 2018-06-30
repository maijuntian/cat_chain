package com.mai.cat_chain.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

/**
 * 动画工具
 */
public class AnimationUtils {

    /**
     * 渐隐渐现动画
     *
     * @param view     需要实现动画的对象
     * @param duration 动画实现的时长（ms）
     */
    public static void showAnimation(final View view, long duration) {
        float start = 0f;
        float end = 1f;
        view.setVisibility(View.VISIBLE);

        AlphaAnimation animation = new AlphaAnimation(start, end);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }
        });
        view.setAnimation(animation);
        animation.start();
    }

    /**
     * 渐隐渐现动画
     *
     * @param view     需要实现动画的对象
     * @param duration 动画实现的时长（ms）
     */
    public static void hideAnimation(final View view, long duration) {
        float start = 1f;
        float end = 0f;
        view.setVisibility(View.INVISIBLE);
        AlphaAnimation animation = new AlphaAnimation(start, end);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }
        });
        view.setAnimation(animation);
        animation.start();
    }
}