package com.lt.vs.come.Sevice;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.lt.vs.come.entrys.MediaItem;

import java.io.IOException;

/**
 * Created by 金色VS时空 on 2017/9/28.
 */

public class MusicSevice extends Service{
    private int newmusic;
    private MediaItem music=null;
    private MediaPlayer mediaPlayer=new MediaPlayer();
    private int state=0x11;//0x11：第一次播放， 0x12暂停，0x13:继续播放
    private int curTime,duration;//当前时间，总时间
    @Override
    public void onCreate() {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent intent=new Intent("com.lt.vs.ado.activity");
                intent.putExtra("over",true);
                sendBroadcast(intent);
            }
        });
        MusicSeviceBroadcast broadcast=new MusicSeviceBroadcast();
        IntentFilter filter=new IntentFilter("com.lt.vs.ado.Sevice");
        registerReceiver(broadcast,filter);
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    public class MusicSeviceBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            newmusic=intent.getIntExtra("newmusic",-1);
            if (newmusic!=-1){
                music= (MediaItem) intent.getSerializableExtra("music");//获取歌曲对象
                if (music!=null){
                    playMusic(music);
                    state=0x12;
                }
            }

            int isplay=intent.getIntExtra("isplaying",-1);
            if (isplay!=-1){
                switch(state){
                    //0x11：第一次播放， 0x12暂停，0x13:继续播放
                    case 0x11:
                        music= (MediaItem) intent.getSerializableExtra("music");//获取歌曲对象
                        playMusic(music);
                        state=0x12;
                        break;
                    case 0x12:
                        mediaPlayer.pause();
                        state=0x13;
                        break;
                    case 0x13:
                        mediaPlayer.start();
                        state=0x12;
                        break;
                }
                Intent intent1=new Intent("com.lt.vs.ado.activity");
                intent1.putExtra("state",state);
                sendBroadcast(intent1);//把当前的状态发送给Activity

            }


            int progress=intent.getIntExtra("progress",-1);
            if (progress!=-1){
                curTime= (int) (((progress*1.0)/100)*duration);//将当前位置转化为毫秒
                mediaPlayer.seekTo(curTime);
            }
        }
    }
    public void playMusic(MediaItem musics){
        if (mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.reset();
            try {
                //获得播放该路径
                mediaPlayer.setDataSource(musics.getPath());
                mediaPlayer.prepare();
                mediaPlayer.start();
                duration=mediaPlayer.getDuration();//获得当前歌曲总时长
                new Thread(){
                    @Override
                    public void run() {
                        while(curTime<duration){
                            try {
                                sleep(1000);
                                curTime=mediaPlayer.getCurrentPosition();
                                Intent intent=new Intent("com.lt.vs.ado.activity");
                                intent.putExtra("curTime",curTime);
                                intent.putExtra("duration",duration);
                                sendBroadcast(intent);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
