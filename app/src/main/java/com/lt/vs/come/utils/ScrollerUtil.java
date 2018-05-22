package com.lt.vs.come.utils;

import android.os.SystemClock;

/**
 * Created by 金色VS时空 on 2017/11/3.
 */

public class ScrollerUtil {
    private float startX;
    private float startY;
    private int distanceX;
    private int distanceY;
    /**
     * isFinished  是否移动完成
     */
    private boolean isFinished;
    private long startTime;

    private long totalTime=200;

    public float getCurrX() {
        return currX;
    }

    private float currX;
    /**
     *
     * @param startX 起始值X
     * @param startY 起始值Y
     * @param distanceX   X方向移动距离
     * @param distanceY   Y方向移动距离
     */
    public void startScroll(float startX, float startY, int distanceX, int distanceY) {
        this.startX=startX;
        this.startY=startY;
        this.distanceX=distanceX;
        this.distanceY=distanceY;
        this.startTime= SystemClock.uptimeMillis();
        isFinished=false;
    }

    /**
     *
     * @return true移动结束
     */
    public boolean cumputeScrollOffest(){
        if (isFinished){
            return false;
        }
        long endTime=SystemClock.uptimeMillis();
        //移动一下段所花时间
        long passTime=endTime-startTime;
        if (passTime<totalTime){
            //没有移动结束
            //计算平均速度
            //float speed=distanceX/totalTime;
            float distanceSmallX=passTime*distanceX/totalTime;
            currX=startX+distanceSmallX;
        }else{
            //移动结束
            isFinished=true;
            currX=startX+distanceX;
        }
        return true;
    }
}
