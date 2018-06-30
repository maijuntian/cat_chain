package com.mai.cat_chain.widget;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 滚动选择器
 *
 * @author maijuntian
 */
public class TimePickerView extends View {

    public static final String TAG = "PickerView";
    /**
     * text之间间距和minTextSize之比
     */
    public static float MARGIN_ALPHA = 4f;
    /**
     * 自动回滚到中间的速度
     */
    public static final float SPEED = 2;

    private List<String> mDataList;
    /**
     * 选中的位置，这个位置是mDataList的中心位置，一直不变
     */
    private int mCurrentSelected;
    private Paint mPaint; // 未选中的画笔
    private Paint mPaint_selected; // 选中的画笔
    private Paint mPaint_bg; // 背景颜色的画笔
    private Paint mPaint_right; // 右边单位的画笔
    private String mtextRight; // 右边选中的字

    private float mMaxTextSize = 80;
    private float mMinTextSize = 40;

    private float mMaxTextAlpha = 255;
    private float mMinTextAlpha = 120;

    private int mColorText_selected = 0x000000; // 选中的颜色
    private int mColorText = 0x000000; // 未选中的颜色
    private int mColorBg_selected = 0x00000000; // 选中的背景颜色
    private int mColorText_Right = 0x000000; // 右边单位的字

    Rect textRect; // 选中字体的大小
    private String selectedText = "0";// 选中的文本
    private float textSizeSacle = 8f;

    private int mViewHeight;
    private int mViewWidth;

    private int moveUpItemCount;

    private boolean isNotCycle;// 是否循环，true为不循环

    private float mLastDownY;
    /**
     * 滑动的距离
     */
    private float mMoveLen = 0;
    private boolean isInit = false;
    private onSelectListener mSelectListener;
    private onSelectItemListener mSelectItemListener;
    private Timer timer;
    private MyTimerTask mTask;

    private Camera mCamera;

    private int selectPosition = -1;

    public void setNotCycle(boolean isNotCycle) {
        this.isNotCycle = isNotCycle;
        postInvalidate();
    }

    Handler updateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (Math.abs(mMoveLen) < SPEED) {
                mMoveLen = 0;
                if (mTask != null) {
                    mTask.cancel();
                    mTask = null;
                    performSelect();
                }
            } else
                // 这里mMoveLen / Math.abs(mMoveLen)是为了保有mMoveLen的正负号，以实现上滚或下滚
                mMoveLen = mMoveLen - mMoveLen / Math.abs(mMoveLen) * SPEED;
            invalidate();
        }

    };

    /**
     * 设置未选中字的颜色
     *
     * @param colorId
     */
    public void setTextColor(int colorId) {
        this.mColorText = colorId;
        if (mPaint != null)
            mPaint.setColor(mColorText);
    }

    /**
     * 设置选中字的颜色
     *
     * @param colorId
     */
    public void setSelectTextColor(int colorId) {
        this.mColorText_selected = colorId;
        if (mPaint_selected != null)
            mPaint_selected.setColor(colorId);
    }

    public TimePickerView(Context context) {
        super(context);
        init();
    }

    public TimePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setOnSelectListener(onSelectListener listener) {
        mSelectListener = listener;
    }

    public void setOnSelectItemListener(onSelectItemListener listener) {
        mSelectItemListener = listener;
    }

    private void performSelect() {
        if (mSelectListener != null || mSelectItemListener != null) {
            int item = mCurrentSelected + moveUpItemCount;
            if (item < 0) {
                item += mDataList.size()
                        * (Math.abs(item / mDataList.size()) + 1);
            }
            item = item % mDataList.size();
            if (mSelectListener != null) {

                mSelectListener.onSelect(mDataList.get(item));

            }
            if (mSelectItemListener != null) {

                mSelectItemListener.onSelect(item);
            }
        }

    }

    public void setData(List<String> datas) {
        moveUpItemCount = 0;
        mDataList = datas;
        mCurrentSelected = datas.size() / 2;
        invalidate();
    }

    /**
     * 选择选中的item的index
     *
     * @param selected
     */
    public void setSelected(int selected) {
        if (isNotCycle) {
            moveUpItemCount = selected - mCurrentSelected;
        } else {
            mCurrentSelected = selected;
            int distance = mDataList.size() / 2 - mCurrentSelected;
            if (distance < 0)
                for (int i = 0; i < -distance; i++) {
                    moveHeadToTail();
                    mCurrentSelected--;
                }
            else if (distance > 0)
                for (int i = 0; i < distance; i++) {
                    moveTailToHead();
                    mCurrentSelected++;
                }
        }
        invalidate();
    }

    /**
     * 选择选中的内容
     *
     * @param mSelectItem
     * @return index 选中的序号 -1为没选中
     */
    public int setSelected(String mSelectItem) {
        selectedText = mSelectItem;
        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).equals(mSelectItem)) {
                setSelected(i);
                return i;
            }
        }
        return -1;
    }

    /**
     * 设置选中区域的背景颜色
     *
     * @param colorId
     */
    public void setSelecteBg(int colorId) {
        if (mPaint_bg != null)
            mPaint_bg.setColor(colorId);
    }

    /**
     * 设置右边选中的字
     *
     * @param text 内容
     */
    public void setTextRight(String text) {
        this.mtextRight = text;
    }

    /**
     * 设置右边选中的字
     *
     * @param text    内容
     * @param colorId
     */
    public void setTextRight(String text, int colorId) {
        this.mtextRight = text;
        if (mPaint_right != null)
            mPaint_right.setColor(colorId);
    }

    public void setTextSizeSacle(float textSizeSacle) {
        this.textSizeSacle = textSizeSacle;
    }

    public String getSelectedText() {
        return selectedText;
    }

    public int getSelectPosition() {
        return selectPosition;
    }

    public int getDataSize() {
        if (mDataList != null)
            return mDataList.size();
        return 0;
    }

    private void moveHeadToTail() {
        String head = mDataList.get(0);
        mDataList.remove(0);
        mDataList.add(head);
        moveUpItemCount++;
    }

    private void moveTailToHead() {
        String tail = mDataList.get(mDataList.size() - 1);
        mDataList.remove(mDataList.size() - 1);
        mDataList.add(0, tail);
        moveUpItemCount--;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();
        // 按照View的高度计算字体大小
        mMaxTextSize = mViewHeight / textSizeSacle;
        mMinTextSize = mMaxTextSize / 2f;
        isInit = true;
        invalidate();
    }

    private void init() {
        timer = new Timer();
        mDataList = new ArrayList<String>();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Style.FILL);
        mPaint.setTextAlign(Align.CENTER);
        mPaint.setColor(mColorText);

        mPaint_selected = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_selected.setStyle(Style.FILL);
        mPaint_selected.setTextAlign(Align.CENTER);
        mPaint_selected.setColor(mColorText_selected);

        mPaint_bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_bg.setStyle(Style.FILL);
        mPaint_bg.setTextAlign(Align.CENTER);
        mPaint_bg.setColor(mColorBg_selected);

        mPaint_right = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint_right.setStyle(Style.FILL);
        mPaint_right.setTextAlign(Align.CENTER);
        mPaint_right.setColor(mColorText_Right);

        mCamera = new Camera();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 根据index绘制view
        if (isInit && !mDataList.isEmpty())
            drawData(canvas);
    }

    private void drawData(Canvas canvas) {
        // 先绘制选中的text再往上往下绘制其余的text
        int position = isNotCycle ? mCurrentSelected + moveUpItemCount
                : mCurrentSelected;
        float scale = parabola(mViewHeight / 4.0f, mMoveLen);
        float size = (mMaxTextSize - mMinTextSize) * scale + mMinTextSize;
        mPaint_selected.setTextSize(size);
        mPaint_selected
                .setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
        // text居中绘制，注意baseline的计算才能达到居中，y值是text中心坐标
        float x = (float) (mViewWidth / 2.0);
        float y = (float) (mViewHeight / 2.0 + mMoveLen);
        FontMetricsInt fmi = mPaint_selected.getFontMetricsInt();
        float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));

        String text_selected = mDataList.get(position); // 选中的值
        selectPosition = position;
        selectedText = text_selected;
        /**
         * 绘制选中背景图
         */
        if (textRect == null) {
            textRect = new Rect();
            mPaint_selected.getTextBounds(getDataMostSizeString(), 0,
                    getDataMostSize(), textRect);
            mPaint_right.setTextSize(getWidth() / 10);
        }
        float upLineY = (mViewHeight / 2f) - (textRect.height());
        float downLineY = (mViewHeight / 2f) + (textRect.height());
        /*
		 * canvas.drawRect(0, (mViewHeight / 2f) - (textRect.height()),
		 * mViewWidth, (mViewHeight / 2f) + (textRect.height()), mPaint_bg);
		 */
        canvas.drawLine(0, upLineY, mViewWidth, upLineY, mPaint_bg);
        canvas.drawLine(0, downLineY, mViewWidth, downLineY, mPaint_bg);
        // 绘制选中的字
        canvas.drawText(text_selected, x, baseline, mPaint_selected);

        // 绘制右边的单位的字
        if (mtextRight != null && !"".equals(mtextRight)) {
            canvas.drawText(mtextRight, (mViewWidth / 2f) + textRect.width()
                            + (mPaint_right.measureText(mtextRight) / 2f),
                    (mViewHeight / 2f) + (textRect.height() / 2f), mPaint_right);
        }

        // 绘制上方data
        for (int i = 1; (position - i) >= 0; i++) {
            drawOtherText(canvas, i, -1);
        }
        // 绘制下方data
        for (int i = 1; (position + i) < mDataList.size(); i++) {
            drawOtherText(canvas, i, 1);
        }

    }

    /**
     * @param canvas
     * @param position 距离mCurrentSelected的差值
     * @param type     1表示向下绘制，-1表示向上绘制
     */
    private void drawOtherText(Canvas canvas, int position, int type) {
        float d = (float) (MARGIN_ALPHA * mMinTextSize * position + type
                * mMoveLen);
        float scale = parabola(mViewHeight / 4.0f, d);
        float size = (mMaxTextSize - mMinTextSize) * scale + mMinTextSize;
        mPaint.setTextSize(mPaint_selected.getTextSize());
        mPaint.setAlpha((int) ((mMaxTextAlpha - mMinTextAlpha) * scale + mMinTextAlpha));
        float y = (float) (mViewHeight / 2.0 + type * d);
        FontMetricsInt fmi = mPaint.getFontMetricsInt();
        float baseline = (float) (y - (fmi.bottom / 2.0 + fmi.top / 2.0));
        canvas.setMatrix(Rotate3d(-type * position * 20, baseline, type
                * position * 3));

        if (position <= 2) {
            if (isNotCycle) {
                canvas.drawText(
                        mDataList.get(mCurrentSelected + moveUpItemCount + type
                                * position), (float) (mViewWidth / 2.0),
                        baseline, mPaint);
            } else {
                canvas.drawText(
                        mDataList.get(mCurrentSelected + type * position),
                        (float) (mViewWidth / 2.0), baseline, mPaint);
            }
        }
    }

    /**
     * 抛物线
     *
     * @param zero 零点坐标
     * @param x    偏移量
     * @return scale
     */
    private float parabola(float zero, float x) {
        float f = (float) (1 - Math.pow(x / zero, 2));
        return f < 0 ? 0 : f;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                doDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isNotCycle) {
                    doMove4Cycle(event);
                } else {
                    doMove(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                doUp(event);
                break;
        }
        return true;
    }

    private void doDown(MotionEvent event) {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        mLastDownY = event.getY();
    }

    private void doMove(MotionEvent event) {

        mMoveLen += (event.getY() - mLastDownY);
        if (mMoveLen > MARGIN_ALPHA * mMinTextSize / 2) {
            // 往下滑超过离开距离
            moveTailToHead();
            mMoveLen = mMoveLen - MARGIN_ALPHA * mMinTextSize;

        } else if (mMoveLen < -MARGIN_ALPHA * mMinTextSize / 2) {
            // 往上滑超过离开距离
            moveHeadToTail();
            mMoveLen = mMoveLen + MARGIN_ALPHA * mMinTextSize;
        }
        mLastDownY = event.getY();
        invalidate();

    }

    private void doMove4Cycle(MotionEvent event) {
        if (event.getY() < mLastDownY
                && mCurrentSelected + moveUpItemCount >= mDataList.size() - 1) {
            return;
        }
        if (event.getY() > mLastDownY
                && mCurrentSelected + moveUpItemCount <= 0) {
            return;
        }
        mMoveLen += (event.getY() - mLastDownY);
        if (mMoveLen > MARGIN_ALPHA * mMinTextSize / 2) {
            // 往下滑超过离开距离
            if (mCurrentSelected + moveUpItemCount > 0)
                moveUpItemCount--;

            mMoveLen = mMoveLen - MARGIN_ALPHA * mMinTextSize;

        } else if (mMoveLen < -MARGIN_ALPHA * mMinTextSize / 2) {
            // 往上滑超过离开距离
            if (mCurrentSelected + moveUpItemCount < mDataList.size() - 1)
                moveUpItemCount++;

            mMoveLen = mMoveLen + MARGIN_ALPHA * mMinTextSize;
        }
        mLastDownY = event.getY();
        invalidate();
    }

    private void doUp(MotionEvent event) {
        // 抬起手后mCurrentSelected的位置由当前位置move到中间选中位置
        if (Math.abs(mMoveLen) < 0.0001) {
            mMoveLen = 0;
            return;
        }
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        mTask = new MyTimerTask(updateHandler);
        timer.schedule(mTask, 0, 10);
    }

    class MyTimerTask extends TimerTask {
        Handler handler;

        public MyTimerTask(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            handler.sendMessage(handler.obtainMessage());
        }

    }

    public Matrix Rotate3d(float degress, float centerY, float delayX) {
        delayX = Math.abs(delayX);
        Matrix mMatrix = new Matrix();
        mCamera.save();
        mCamera.translate(0.0f, 0.0f, 100);
        mCamera.rotateX(degress);
        mCamera.getMatrix(mMatrix);
        mCamera.restore();

        mMatrix.preTranslate(-getWidth() / 2, -getHeight() / 2);
        mMatrix.postTranslate(getWidth() / 2, getHeight() / 2);

        return mMatrix;

    }

    public String getDataMostSizeString() {
        int max = -1000;
        String str = "";
        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).length() > max) {
                max = mDataList.get(i).length();
                str = mDataList.get(i);
            }
        }
        return str;
    }

    public int getDataMostSize() {
        int max = -1000;
        for (int i = 0; i < mDataList.size(); i++) {
            if (mDataList.get(i).length() > max) {
                max = mDataList.get(i).length();
            }
        }
        return max;
    }

    public interface onSelectListener {
        void onSelect(String text);
    }

    public interface onSelectItemListener {
        void onSelect(int item);
    }

}
