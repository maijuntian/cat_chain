package com.mai.cat_chain.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mai.cat_chain.R;
import com.mai.cat_chain.utils.AnimationUtils;
import com.mai.cat_chain.utils.SoundUtils;

/**
 * Created by maijuntian on 2018/6/19.
 */
@SuppressLint("AppCompatCustomView")
public class DiamondView extends LinearLayout {

    boolean isShow = false;

    public DiamondView(Context context) {
        super(context);
    }

    public DiamondView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DiamondView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Glide.with(getContext()).load(R.mipmap.main_icon_zuan_git).into(((ImageView) getChildAt(0)));
    }

    public void show(String type, float number) {
        if (!isShow) {
            isShow = true;
            ((TextView) getChildAt(1)).setText("+"+number);
            AnimationUtils.showAnimation(this, 1000);
        }
    }

    public void hide() {
        if (isShow) {
            isShow = false;
            SoundUtils.INSTANCE.playSound(R.raw.ding);
            AnimationUtils.hideAnimation(this, 1000);
        }
    }
}
