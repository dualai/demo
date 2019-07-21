package com.zailing.wangyievent;

import java.util.ArrayList;
import java.util.List;

public class ViewGroup extends View {
    public ViewGroup(int left, int top, int right, int bottom) {
//        super(left, top, right, bottom);
    }

    List<View> childList = new ArrayList<>();
    private View[] mChildren = new View[0];

    public void addView(View view) {
        if (view == null) {
            return;
        }
        childList.add(view);
        mChildren = (View[]) childList.toArray(new View[childList.size()]);
    }


    /**
     * 事件分发的入口
     *
     * @param event
     * @return
     */
    public boolean dispatchTouchEvent(MotionEvent event) {
        boolean intercepted = onInterceptTouchEvent(event);

        int actionMasked = event.getActionMasked();
        if (actionMasked != MotionEvent.ACTION_CANCEL && !intercepted) {
            if (actionMasked == MotionEvent.ACTION_DOWN) {//Down
                //比如所有事件一共100ms down:60ms
                final View[] children = mChildren;
                //遍历当前的所有的子控件(非递归，只遍历一层)，down事件需要建立关系
                //耗时,最后一个view接收到事件的规律大一些，所以倒过来，有时候有可能重叠，重叠的话最上面的被点击的概率大(framelayout)
                for (int i = children.length - 1; i >= 0; i--) {
                    View child = mChildren[i];
                    // 判断view能够接收到事件
                    // View 能够接收到事件
                    if(!child.isContainer(event.getX(),event.getY())){
                        continue;
                    }

                    //能够接受事件, child 分发给他
                    if(dispatchTransformedTouchEvent(event,child)){
                        //View[] 采取了Message的方式进行实现 链表结构


                    }

                }

            }

        }
        return true;

    }


    /**
     *  能够接收到事件的view
     *  很多时候用单向链表？
     */
    private static final class TouchTarget{
        private View child; //当前缓存的view
        //回收池（一个对象）
        private static TouchTarget sRecycleBin;
        private static final Object sRecycleLock = new Object[0];
        public TouchTarget next;
        // size
        private static int sRecycledCount;
        // up事件
        public static TouchTarget obtain(View child){
            TouchTarget target;
            synchronized (sRecycleLock){
                if(sRecycleBin == null){
                    target = new TouchTarget();
                }else{
                    target = sRecycleBin;
                }
                sRecycleBin = target.next;
                sRecycledCount--;
                target.next = null;
            }
            target.child = child;
            return target;
        }

        /**
         * 回收
         */
        public void recycle(){
            if(child == null){
                throw new IllegalStateException("已经被回收了");
            }
            synchronized (sRecycleLock){
                if(sRecycledCount <32){
                    next = sRecycleBin;
                    sRecycleBin = this;
                    sRecycledCount +=1;
                }
            }
        }

    }



    /**
     * 分发处理,分发给子控件
     * @param event
     * @param child
     * @return
     */
    private boolean dispatchTransformedTouchEvent(MotionEvent event, View child) {
        final boolean handled;
        // 当前view消费了
        handled = child.dispatchTouchEvent(event);
        return handled;
    }

    private boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
