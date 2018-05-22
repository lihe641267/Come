package com.lt.vs.come.utils;

import java.text.SimpleDateFormat;

/**
 * Created by 金色VS时空 on 2017/9/29.
 */

public class TimeUtil {
    public static  int getTimeFen(int time){
        int fen=time/60;
        return fen;
    }
    public static  int getTimeMiao(int time){
        int miao=time%60;
        return miao;
    }
    public static String getTime(long time){
        SimpleDateFormat formats=new SimpleDateFormat("mm:ss");
        String times=formats.format(time);
        return times;
    }
}
