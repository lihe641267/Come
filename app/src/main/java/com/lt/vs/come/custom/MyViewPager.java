package com.lt.vs.come.custom;

import android.content.Context;
import android.gesture.Gesture;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.lt.vs.come.utils.ScrollerUtil;
import com.lt.vs.come.views.EventActivity;

/**
 * Created by 金色VS时空 on 2017/11/2.
 * 作用：仿ViewPager
 */

public class MyViewPager extends ViewGroup {
    private GestureDetector gestureDetector;
    private int currIndex;//当前下标位置
    private ScrollerUtil scroller;

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        scroller=new ScrollerUtil();
        gestureDetector=new GestureDetector(context, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                //Log.e("<<<<<<<<<",e.getRawX()+"=="+e.getRawY());
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                EventActivity.getTv_offset().setText("X偏移量："+distanceX+"\n"+"Y偏移量："+distanceY);
                scrollBy((int)distanceX,getScrollY());
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count=getChildCount();
        for (int i = 0; i < count; i++) {

            View childview=getChildAt(i);
            //给每个孩子分配位置
            childview.layout(i*getWidth(),0,(i+1)*getWidth(),getHeight());
        }
    }

    private float startX;
    /**
     * 手势识别器
     */
    private long quickSlidTimeUp;
    private long quickSlidTimeDown;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        gestureDetector.onTouchEvent(event);
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN :
                quickSlidTimeDown= SystemClock.currentThreadTimeMillis();
                startX=event.getX();
                break;
            case MotionEvent.ACTION_MOVE :

                break;
            case MotionEvent.ACTION_UP :
                //计算滑动时间
                quickSlidTimeUp=SystemClock.currentThreadTimeMillis();
               // Log.e("Time----",quickSlidTimeUp-quickSlidTimeDown+"");
                //快速滑动翻页
                if (quickSlidTimeUp-quickSlidTimeDown<50){
                    float endX=event.getX();
                    Log.e("Time----",quickSlidTimeUp-quickSlidTimeDown+"??"+(int)(endX-startX));
                    int tempIndex=currIndex;
                    //快速滑动并且X方向产生偏移量大于10才进行翻页
                    if (startX-endX>10){
                        tempIndex++;
                    }else if (endX-startX>10){
                        tempIndex--;
                    }

                    scrollToPager(tempIndex);
                }else{
                    //慢速滑动是否滑动控件一半距离及以上
                    float endX=event.getX();
                    int tempIndex=currIndex;
                    if (startX-endX>getWidth()/2){
                        tempIndex++;
                    }else if (endX-startX>getWidth()/2){
                        tempIndex--;
                    }

                    scrollToPager(tempIndex);
                }



                break;

        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.cumputeScrollOffest()){
            float currX=scroller.getCurrX();
            scrollTo((int) currX,0);
            invalidate();
        }
    }

    /**
     * 屏蔽非法值，根据位置移动到指定页面
     * @param tempIndex
     */
    private void scrollToPager(int tempIndex) {
        if (tempIndex<0){
            tempIndex=0;
        }
        if (tempIndex>getChildCount()-1){
            tempIndex=getChildCount()-1;
        }
        currIndex=tempIndex;
        //暴力移动
        //scrollTo(currIndex*getWidth(),getScrollY());
        //平滑移动
        int distance=currIndex*getWidth()-getScrollX();
        scroller.startScroll(getScrollX(),getScrollY(),distance,0);
        invalidate();
    }
}
