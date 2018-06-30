package com.mai.cat_chain.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mai.cat_chain.R;
import com.mai.cat_chain.javabean.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maijuntian on 2018/6/19.
 */
public class YLineChartLandView extends View {

    List<Record> inDatas = new ArrayList<>();
    List<Record> outDatas = new ArrayList<>();

    int maxValue = 20;

    Paint greenPaint, yellowPaint, grayPaint;

    public void setDatas(List<Record> inDatas, List<Record> outDatas) {
        this.inDatas = inDatas;
        this.outDatas = outDatas;
        getMax();
        postInvalidate();
    }

    private void getMax() {
        List<Record> datas = new ArrayList<>();
        datas.addAll(inDatas);
        if (outDatas != null) {
            datas.addAll(outDatas);
        }
        if (datas.size() == 0)
            return;
        float temp = 0f;
        for (Record data : datas) {
            if (data.getCoin() > temp)
                temp = data.getCoin();
        }

        maxValue = ((int) (temp * 6 / 50) + 1) * 10;
    }

    public YLineChartLandView(Context context) {
        super(context);
        initView();
    }

    public YLineChartLandView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public YLineChartLandView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        greenPaint = new Paint();
        greenPaint.setAntiAlias(true);
        greenPaint.setTextSize(getResources().getDimension(R.dimen.sp_10));
        greenPaint.setStyle(Paint.Style.STROKE);
        greenPaint.setColor(Color.parseColor("#00ffff"));

        yellowPaint = new Paint();
        yellowPaint.setAntiAlias(true);
        yellowPaint.setTextSize(getResources().getDimension(R.dimen.sp_10));
        yellowPaint.setStyle(Paint.Style.STROKE);
        yellowPaint.setColor(Color.parseColor("#f0f174"));

        grayPaint = new Paint();
        grayPaint.setAntiAlias(true);
        grayPaint.setTextSize(getResources().getDimension(R.dimen.sp_7));
        grayPaint.setStyle(Paint.Style.STROKE);
        grayPaint.setColor(Color.parseColor("#68718b"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBg(canvas);

    }

    private void drawBg(Canvas canvas) {

        Rect yRect = new Rect();
        grayPaint.getTextBounds(maxValue + "", 0, (maxValue + "").length(), yRect);

        float perHeight = (getHeight() - yRect.height() * 3) / 5f;

        for (int i = 0; i < 6; i++) {
            String yTextValue = (int) (maxValue * (5 - i) / 5f) + "";
            float y = yRect.height() + perHeight * i;
            canvas.drawText(yTextValue, yRect.width() - grayPaint.measureText(yTextValue), y, grayPaint);

            canvas.drawLine(yRect.width() + yRect.height(), y, getWidth(), y, grayPaint);
        }

    }
}
