package com.zailing.wangyievent.litener;


import com.zailing.wangyievent.MotionEvent;
import com.zailing.wangyievent.View;

public interface OnTouchListener {
    boolean onTouch(View v, MotionEvent event);
}
