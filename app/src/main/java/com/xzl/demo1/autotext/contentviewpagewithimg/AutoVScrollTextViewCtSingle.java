

package com.xzl.demo1.autotext.contentviewpagewithimg;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.DimenRes;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;


import com.xzl.demo1.R;

import java.util.concurrent.TimeUnit;

public class AutoVScrollTextViewCtSingle extends android.support.v7.widget.AppCompatTextView {
    private int delayStart = 5;//延迟滚动时间（单位秒）
    private boolean resetOnFinish = false;//滚动结束之后是否重置
    private int step = 5;//步进距离
    private int speed = 1;//速度=n*50ms
    private int addLineSpace = 0;//额外行间隔
    private ScrollStatusListener mScrollStatusListener;//状态监听
    private volatile boolean isScrolling;//滚动中
    private boolean startFlag = false;//false:可以开始，true:不可以开始
    private int noticeHeight;

    public AutoVScrollTextViewCtSingle(Context context) {
        super(context);
        init(context, null);
    }

    public AutoVScrollTextViewCtSingle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AutoVScrollTextViewCtSingle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onDetachedFromWindow(){
        if (mMarqueeRunnable != null && !mMarqueeRunnable.finished) {
            mMarqueeRunnable.stop();
        }
        super.onDetachedFromWindow();
    }


    private MarqueeRunnable mMarqueeRunnable;

    public void setDragonView(boolean isDragonView) {
        if (isDragonView) {
            noticeHeight = noticeHeight + noticeHeight /2;
        }
    }

    public void setNoticeHeight(@DimenRes int resid) {
        noticeHeight = getResources().getDimensionPixelOffset(resid);
    }

    public void setNoticeHeightByValue(int resid) {
        noticeHeight = resid;
    }
    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AutoVScrollTextViewCtSingle, 0, 0);
            delayStart = array.getInt(R.styleable.AutoVScrollTextViewCtSingle_delayBegin, 5);
            resetOnFinish = array.getBoolean(R.styleable.AutoVScrollTextViewCtSingle_rstOnFinish, false);
            step = array.getInt(R.styleable.AutoVScrollTextViewCtSingle_byStep, 5);
            speed = array.getInt(R.styleable.AutoVScrollTextViewCtSingle_bySpeed, 1);
            addLineSpace = array.getDimensionPixelSize(R.styleable.AutoVScrollTextViewCtSingle_addLineSpace, 0);
            noticeHeight = array.getDimensionPixelSize(R.styleable.AutoVScrollTextViewCtSingle_noticeHeight, -1);
            array.recycle();
        }
    }

    /**
     * 获取延迟启动时间
     *
     * @return
     */
    public int getDelayStart() {
        return delayStart;
    }

    /**
     * 设置延迟启动时间
     *
     * @param delayStart
     */
    public void setDelayStart(int delayStart) {
        this.delayStart = delayStart;
    }

    /**
     * 滚动结束是否重置
     *
     * @return
     */
    public boolean isResetOnFinish() {
        return resetOnFinish;
    }

    /**
     * 设置滚动结束是否重置
     *
     * @param resetOnFinish
     */
    public void setResetOnFinish(boolean resetOnFinish) {
        this.resetOnFinish = resetOnFinish;
    }

    /**
     * 获取步进距离
     *
     * @return
     */
    public int getStep() {
        return step;
    }

    /**
     * 设置步进距离
     *
     * @param step
     */
    public void setStep(int step) {
        this.step = step;
    }

    /**
     * 获取步进速度
     *
     * @return
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * 获取滚动状态监听器
     *
     * @return
     */
    public ScrollStatusListener getScrollStatusListener() {
        return mScrollStatusListener;
    }

    /**
     * 设置滚动状态监听器
     *
     * @param scrollStatusListener
     */
    public void setScrollStatusListener(ScrollStatusListener scrollStatusListener) {
        mScrollStatusListener = scrollStatusListener;
    }

    /**
     * 设置步进速度
     *
     * @param speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * 是否正在滚动
     *
     * @return
     */
    public boolean isScrolling() {
        return isScrolling;
    }

    /**
     * 停止滚动并且重置状态
     */
    public void stopAndReset() {
        nowPoint = 0;
        startFlag = false;
        try {
            if (mMarqueeRunnable != null && !mMarqueeRunnable.finished) {
                mMarqueeRunnable.stop();
            }
            if (null != mScrollThread) mScrollThread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
        postInvalidate();
    }

    /**
     * 开启滚动
     */
    public void start() {
        startFlag = true;
    }

    /**
     * 重置状态
     */
    public void reset() {
        startFlag = true;
        resetStatus();
    }


    private Thread mScrollThread;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private StaticLayout mTextLayout;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            resetStatus();
        }
        if (noticeHeight == -1) {
            noticeHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
        }
        Log.i("AutoSingle", "noticeHeight=" + noticeHeight + ",Ptop=" + getPaddingTop() + ",Pbot=" + getPaddingBottom() + ",paheight=" + getHeight());
    }

    /**
     * 状态重置
     */
    private synchronized void resetStatus() {
        resetTextParams();
        resetThread();
    }

    /**
     * 重置/刷新文字配置信息
     */
    private synchronized void resetTextParams() {
        int currentTextColor = getCurrentTextColor();
        TextPaint textPaint = getPaint();
        textPaint.setColor(currentTextColor);
        mTextLayout = new StaticLayout(getText(), getPaint(),
                getWidth(), Layout.Alignment.ALIGN_NORMAL,
                getLineSpacingMultiplier(), addLineSpace, false);
    }

    /**
     * 重置线程状态
     */
    private synchronized void resetThread() {
        myHeight = getOneTxtH() * getLineCount();
        int mContainerHeight = getMeasuredHeight();
        if(mContainerHeight > myHeight){
            startFlag = false;
        }
        Log.d("textview", "resetThread: myHeight: " + myHeight + ", lineHeight:" + getOneTxtH() + ", lineCount: " + getLineCount() + ", noticeHeight: " + noticeHeight);
        if (mMarqueeRunnable != null && !mMarqueeRunnable.finished) {
            mMarqueeRunnable.stop();
        }
        if (!startFlag) {
            return;
        }
        mMarqueeRunnable = new MarqueeRunnable();
        mScrollThread = new Thread(mMarqueeRunnable);
        mScrollThread.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getLineCount() > 1){
            canvas.save();
            float textX = 0;
            float textY = nowPoint;
            //Log.d("AutoVsText","text=" + getText().subSequence(0,5)+"textX=" +textX+" textY="+textY);
            canvas.translate(textX, textY);
            if (mTextLayout != null) {
                mTextLayout.draw(canvas);
            }
            canvas.restore();
        }else{
            super.onDraw(canvas);
        }
    }

    private int nowPoint;
    private int myHeight;

    private final class MarqueeRunnable implements Runnable {
        private volatile boolean finished = false;

        public void stop() {
            finished = true;
        }

        public MarqueeRunnable() {
        }

        @Override
        public void run() {
            isScrolling = true;
            if (mScrollStatusListener != null) {
                mScrollStatusListener.onScrollPrepare();
            }
            try {
                TimeUnit.SECONDS.sleep(delayStart);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                if (mScrollStatusListener != null) {
                    mScrollStatusListener.onScrollStart();
                }
                while (!finished && startFlag) {
                    if (step == 0) {
                        break;
                    }
                    nowPoint -= step;
                    if (myHeight != 0 && nowPoint < -myHeight + addLineSpace) {
                        if (resetOnFinish) {
                            nowPoint = 0;
                            postInvalidate();
                        }
                        isScrolling = false;
                        if (mScrollStatusListener != null) {
                            mScrollStatusListener.onScrollStop(AutoVScrollTextViewCtSingle.this);
                        }
                        break;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(speed * 50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {

            }
        }
    }

    private int getOneTxtH(){
        return getLineHeight() + addLineSpace;
    }

    public interface ScrollStatusListener {
        void onScrollPrepare();

        void onScrollStart();

        void onScrollStop(AutoVScrollTextViewCtSingle viewCtSingle);

    }
}