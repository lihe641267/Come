package com.lt.vs.come.utils;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.util.TimerTask;

/**
 * Created by 金色VS时空 on 2017/9/18.
 */

public  class NetSpeedUtil {
    private Handler handler=new Handler();
    private Context context;
    private TextView textView;
    //构造函数传入上下文
    public NetSpeedUtil(Context context, TextView textView) {
        this.context = context;
        this.textView=textView;
    }

    private void showNetSpeed(){
        long nowToalRxBytes=getToalRxBytes(context);
        long nowTimeStamp=System.currentTimeMillis();
        //毫秒转换
        double speed=((nowToalRxBytes-lastToalRxBytes)*1000f/(nowTimeStamp-lastTimeStamp));

        lastTimeStamp= nowTimeStamp;
        lastToalRxBytes=nowToalRxBytes;

        Message message = handler.obtainMessage();
        message.what=100;
        message.obj = String.valueOf(speed)+"kb/s";
        Log.e("====",speed+"kb/s");
        handler.sendMessage(message);
    }

    private long lastToalRxBytes=0;
    private long lastTimeStamp=0;

    private long getToalRxBytes(Context context){
        return TrafficStats.getUidRxBytes(context.getApplicationInfo().uid)==TrafficStats.UNSUPPORTED
                ? 0 : (TrafficStats.getTotalRxBytes()/1024);
    }
    TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
            showNetSpeed();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    handler=new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            if (msg!=null){
                                if (msg.what==100){
                                    textView.setText((String)(msg.obj));
                                }
                            }

                        }
                    };
                }
            });
        }
    };

    public TimerTask getCurrentSpeed() {
        return timerTask;
    }

}
