package com.mai.cat_chain.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.mai.cat_chain.R;
import com.mai.cat_chain.javabean.Record;
import com.mai.cat_chain.utils.DisplayUtils;
import com.mai.cat_chain.utils.MLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maijuntian on 2018/6/19.
 */
public class XLineChartView extends View {

    List<Record> inDatas = new ArrayList<>();
    List<Record> outDatas = new ArrayList<>();

    int maxValue = 20;
    float perWidth;

    float valueHeight;

    float textHeight;

    Paint greenPaint, yellowPaint, grayPaint, yellowBgPaint, greenBgPaint;

    public void setDatas(List<Record> inDatas, List<Record> outDatas) {
        this.inDatas = inDatas;
        this.outDatas = outDatas;
        getMax();
        requestLayout();
    }

    private void getMax() {
        List<Record> datas = new ArrayList<>();
        datas.addAll(inDatas);
        if (outDatas != null)
            datas.addAll(outDatas);
        if (datas.size() == 0)
            return;
        float temp = 0f;
        for (Record data : datas) {
            if (data.getCoin() > temp)
                temp = data.getCoin();
        }

        maxValue = ((int) (temp * 6 / 50) + 1) * 10;

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (inDatas.size() > 0) {

            float width = getMeasuredWidth();

            width = perWidth * inDatas.size() > width ? perWidth * inDatas.size() : width;

            int height = getMeasuredHeight();


            MLog.log("width-->" + width + "   height-->" + height);
            setMeasuredDimension((int) width, height);
        }
    }

    public XLineChartView(Context context) {
        super(context);
        initView();
    }

    public XLineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public XLineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        perWidth = DisplayUtils.dip2px(getContext(), 45);


        greenPaint = new Paint();
        greenPaint.setAntiAlias(true);
        greenPaint.setTextSize(getResources().getDimension(R.dimen.sp_10));
        greenPaint.setStyle(Paint.Style.STROKE);
        greenPaint.setColor(Color.parseColor("#00ffff"));

        greenBgPaint = new Paint();
        greenBgPaint.setAntiAlias(true);
        greenBgPaint.setStyle(Paint.Style.FILL);

        yellowPaint = new Paint();
        yellowPaint.setAntiAlias(true);
        yellowPaint.setTextSize(getResources().getDimension(R.dimen.sp_10));
        yellowPaint.setStyle(Paint.Style.STROKE);
        yellowPaint.setColor(Color.parseColor("#f0f174"));


        yellowBgPaint = new Paint();
        yellowBgPaint.setAntiAlias(true);
        yellowBgPaint.setStyle(Paint.Style.FILL);

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

        drawInData(canvas);
        drawOutData(canvas);
    }

    private void drawBg(Canvas canvas) {
        Rect yRect = new Rect();
        grayPaint.getTextBounds(maxValue + "", 0, (maxValue + "").length(), yRect);

        textHeight = yRect.height();
        valueHeight = getHeight() - textHeight * 3;
    }

    private void drawInData(Canvas canvas) {
        if (inDatas.size() > 0) {

            Path inPath = new Path();

            for (int i = 0, size = inDatas.size(); i < size; i++) {

                Record record = inDatas.get(i);

                PointF inPoint = getPoint(record, i);
                float realTextWidth = grayPaint.measureText(record.getTime());
                float x = inPoint.x - realTextWidth / 2f;

                canvas.drawText(record.getTime(), x, getHeight(), grayPaint);


                if (i < size - 1) {
                    PointF nextPoint = getPoint(inDatas.get(i + 1), i + 1);
                    canvas.drawLine(inPoint.x, inPoint.y, nextPoint.x, nextPoint.y, yellowPaint);
                }

                float valueTextWidth = yellowPaint.measureText(record.getCoin() + "");
                canvas.drawText(inDatas.get(i).getCoin() + "", inPoint.x - valueTextWidth / 2f, inPoint.y - 10, yellowPaint);


                float xLineHeight = getHeight() - textHeight * 2f;

                if (i == 0) {
                    inPath.moveTo(inPoint.x, xLineHeight);
                }

                inPath.lineTo(inPoint.x, inPoint.y);

                if (i == size - 1) {
                    inPath.lineTo(inPoint.x, xLineHeight);
                    inPath.lineTo(0.5f * perWidth, xLineHeight);

                    inPath.close();


                    Shader shader = new LinearGradient(getWidth() / 2f, textHeight, getWidth() / 2f, xLineHeight, new int[]{Color.parseColor("#80f0f174"), Color.parseColor("#807d6526")}, null, Shader.TileMode.CLAMP);
                    yellowBgPaint.setShader(shader);
                    canvas.drawPath(inPath, yellowBgPaint);
                }
            }
        }
    }

    private void drawOutData(Canvas canvas) {
        if (outDatas != null && outDatas.size() > 0) {

            Path outPath = new Path();

            for (int i = 0, size = outDatas.size(); i < size; i++) {

                Record record = outDatas.get(i);

                PointF outPoint = getPoint(record, i);

                if (i < size - 1) {
                    PointF nextPoint = getPoint(outDatas.get(i + 1), i + 1);
                    canvas.drawLine(outPoint.x, outPoint.y, nextPoint.x, nextPoint.y, greenPaint);
                }

                float valueTextWidth = greenPaint.measureText(record.getCoin() + "");
                canvas.drawText(record.getCoin() + "", outPoint.x - valueTextWidth / 2f, outPoint.y - 10, greenPaint);


                float xLineHeight = getHeight() - textHeight * 2f;

                if (i == 0) {
                    outPath.moveTo(outPoint.x, xLineHeight);
                }

                outPath.lineTo(outPoint.x, outPoint.y);

                if (i == size - 1) {
                    outPath.moveTo(outPoint.x, xLineHeight);
                    outPath.moveTo(0.5f * perWidth, xLineHeight);

                    outPath.close();


                    Shader shader = new LinearGradient(getWidth() / 2f, textHeight, getWidth() / 2f, xLineHeight, new int[]{Color.parseColor("#801cd6c2"), Color.parseColor("#80131d27")}, null, Shader.TileMode.CLAMP);
                    greenBgPaint.setShader(shader);
                    canvas.drawPath(outPath, greenBgPaint);
                }
            }
        }
    }


    private PointF getPoint(Record record, int index) {
        float y = valueHeight * (1 - (record.getCoin() / (float) maxValue)) + textHeight;
        float x = index * perWidth + 0.5f * perWidth;
        return new PointF(x, y);
    }

}
