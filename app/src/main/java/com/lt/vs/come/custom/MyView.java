package com.lt.vs.come.custom;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 金色VS时空 on 2017/11/2.
 */
public class MyView extends View{

    private OnDatePositionChanged mOnDatePositionChanged;

    public void setmOnDatePositionChanged(OnDatePositionChanged mOnDatePositionChanged) {
        this.mOnDatePositionChanged = mOnDatePositionChanged;
    }

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int lastX;
    private int lastY;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x=(int) event.getRawX();
        int y=(int) event.getRawY();
        int midWith=(getRight()-getLeft())/2;
        int midHeight=(getBottom()-getTop())/2;
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                lastX=x;
                lastY=y;
                //中心坐标()
                layout((int)event.getRawX()-midWith,(int)event.getRawY()-midHeight,
                        (int)event.getRawX()+midWith,(int)event.getRawY()+midHeight);

                break;
            case MotionEvent.ACTION_MOVE:

                //计算X,Y方向的偏移量
                int offsetX=(int)event.getRawX()-lastX;
                int offsetY=(int) event.getRawY()-lastY;
                //调用layout()方法刷新位置
                layout((int)event.getRawX()-midWith,(int)event.getRawY()-midHeight,
                        (int)event.getRawX()+midWith,(int)event.getRawY()+midHeight);
                /*layout(getLeft()+offsetX , getTop()+offsetY,
                       getRight()+offsetX , getBottom()+offsetY);*/
                /*layout((int)(event.getRawX()-getWidth()/2),(int)(event.getRawY()-getHeight()/2),
                        (int)(event.getRawX()+getWidth()/2),(int)(event.getRawY()+getHeight()/2));*/
                lastX=(int) event.getRawX();
                lastY=(int) event.getRawY();
                /*EventActivity.getTv_offset2().setText("X偏移量："+(event.getRawX()-getWidth()/2)+
                        "\n"+"Y偏移量："+(event.getRawY()-getHeight()/2));*/
                Log.e("======",(event.getRawX()-getWidth()/2)+"=="+(event.getRawY()-getHeight()/2));


                /*offsetLeftAndRight(offsetX);
                offsetTopAndBottom(offsetY);*/

                break;
        }
        return true;
    }
    interface OnDatePositionChanged{
        void onPositionChange(float X , float Y);
    }
}












