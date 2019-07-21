package com.zailing.wangyievent;

public class Activity {
    public static void main(String[] args) {
        //自己制造事件
        MotionEvent motionEvent = new MotionEvent(100,100);
        motionEvent.setActionMasked(MotionEvent.ACTION_DOWN);

    }
}
