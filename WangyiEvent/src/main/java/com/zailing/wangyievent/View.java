package com.zailing.wangyievent;

import com.zailing.wangyievent.litener.OnClickListener;
import com.zailing.wangyievent.litener.OnTouchListener;

public class View {
    private int left;
    private int top;
    private int right;
    private int bottom;

    private OnTouchListener mOnTouchListener;
    //设置一个点击事件，就代表消费了
    private OnClickListener onClickListener;
    public void setOnTouchListener(OnTouchListener mOnTouchListener) {
        this.mOnTouchListener = mOnTouchListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public View() {
    }

    public View(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }


    public boolean isContainer(int x, int y) {

        if (x >= left && x < right && y >= top && y < bottom) {
            return true;
        }
        return false;
    }

    //接收分发的代码
    public boolean dispatchTouchEvent(MotionEvent event){
        // 消息
        boolean result =false;
        if(mOnTouchListener !=null  && mOnTouchListener.onTouch(this,event)){
            result = true;
        }

        //这种写法，如果result = false，就会执行onTouchEvent，否则不会;false说明监听器的onTouch被执行并且返回true
        if(!result && onTouchEvent(event)){
            return true;
        }

        return result;
    }


    private boolean onTouchEvent(MotionEvent event) {
        if(onClickListener != null){
            onClickListener.onClick(this);
            return true;
        }
        return false;
    }


}
