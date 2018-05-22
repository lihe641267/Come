package com.lt.vs.come.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lt.vs.come.R;
import com.lt.vs.come.Sevice.MusicSevice;
import com.lt.vs.come.adapters.BaseDataAdapter2;
import com.lt.vs.come.entrys.MediaItem;
import com.lt.vs.come.utils.MediaUtil;
import com.lt.vs.come.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

public class MusicActivity extends AppCompatActivity {
    private ListView listView;
    private BaseDataAdapter2 adapter;
    private List<MediaItem> datas;
    private MediaItem music;
    private ImageButton btn_top,btn_play,btn_next;
    private MusicAcitvityBroadcast broadcast;
    private TextView tv_music_back;
    private SeekBar seekBar;
    private TextView textViewTime,playMode;
    private int flag_play_mode=0;//当前播放模式标志
    private int index=0;//当前播放歌曲索引
    private int state=0x11;//当前播放状态 0x11：第一次播放， 0x12暂停，0x13:继续播放
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_music);
        init();
    }

    private void init() {
        listView= (ListView) findViewById(R.id.music_lv);
        btn_top= (ImageButton) findViewById(R.id.left);
        btn_play= (ImageButton) findViewById(R.id.middle);
        btn_next= (ImageButton) findViewById(R.id.right);
        tv_music_back= (TextView) findViewById(R.id.tv_music_back);
        seekBar= (SeekBar) findViewById(R.id.seekbar);
        textViewTime= (TextView) findViewById(R.id.curr_time_total);
        playMode= (TextView) findViewById(R.id.play_mode);
        datas=new ArrayList<>();
        datas= MediaUtil.getMusicData(this);
        adapter=new BaseDataAdapter2(this,datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
        btn_top.setOnClickListener(onClickListener);
        btn_next.setOnClickListener(onClickListener);
        btn_play.setOnClickListener(onClickListener);
        playMode.setOnClickListener(onClickListener);
        tv_music_back.setOnClickListener(onClickListener);
        seekBarChange();
        //注册广播
        broadcast=new MusicAcitvityBroadcast();
        IntentFilter filter=new IntentFilter("com.lt.vs.ado.activity");
        registerReceiver(broadcast,filter);
        //启动服务
        Intent intent=new Intent(this, MusicSevice.class);
        startService(intent);

        preferences=getSharedPreferences("Data",0);
        editor=preferences.edit();
        if (preferences.getInt("playMode",0)==0){
            flag_play_mode=0;
            playMode.setText("列表循环");
        }else if (preferences.getInt("playMode",0)==1){
            flag_play_mode=1;
            playMode.setText("单曲循环");
        }else if (preferences.getInt("playMode",0)==2){
            flag_play_mode=2;
            playMode.setText("随机循环");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            editor.putInt("state",state);
            editor.putInt("index",index);
            editor.commit();
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void seekBarChange(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Intent intent=new Intent("com.lt.vs.ado.Sevice");
                intent.putExtra("progress",seekBar.getProgress());
                sendBroadcast(intent);
            }
        });
    }
    //ListView的item点击事件
    private AdapterView.OnItemClickListener onItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            index=position;
            music=datas.get(position);
            Intent intent=new Intent("com.lt.vs.ado.Sevice");
            intent.putExtra("music",music);
            intent.putExtra("newmusic",1);
            intent.putExtra("state",0x13);
            sendBroadcast(intent);//发送广播到服务端
            //设置选中的项背景颜色：选中#96CDCD  未选中#FFEBCD


        }
    };
    //上一曲，下一曲，播放点击事件
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent("com.lt.vs.ado.Sevice");
            int Id=v.getId();
            switch(Id){
                case R.id.tv_music_back:
                    editor.putInt("playMode",flag_play_mode);
                    editor.commit();
                    startActivity(new Intent(MusicActivity.this,FunctionActivity.class));
                    finish();
                    break;
                //上一曲
                case R.id.left:
                    if(index==0){
                        index=datas.size()-1;
                    }else{
                        index--;
                    }
                    intent.putExtra("newmusic",1);//传1表示播放新歌曲
                    intent.putExtra("music",datas.get(index));
                    break;
                //下一曲
                case R.id.right:
                    if (index==datas.size()-1){
                        index=0;
                    }else{
                        index++;
                    }
                    intent.putExtra("newmusic",1);
                    intent.putExtra("music",datas.get(index));
                    break;
                //播放、暂停
                case R.id.middle:
                    if(music==null){
                        music=datas.get(index);//如果当前没有播放就播放第一首歌曲
                        intent.putExtra("music",music);
                    }
                    intent.putExtra("isplaying",1);

                    break;
                //选择播放模式  0表示列表循环，1表示单曲循环，2随机播放；
                case R.id.play_mode:
                    flag_play_mode++;
                    if (flag_play_mode>2){
                        flag_play_mode=0;
                    }
                    if (flag_play_mode==0){
                        //列表循环播放操作
                        playMode.setText("列表循环");
                    }else if(flag_play_mode==1){
                        //单曲循环播放操作
                        playMode.setText("单曲循环");
                    }else if(flag_play_mode==2){
                        //随机播放操作
                        playMode.setText("随机循环");
                    }
                    break;
            }

            sendBroadcast(intent);
        }
    };

    private class MusicAcitvityBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            state=intent.getIntExtra("state",-1);
            switch (state){
                case 0x12:
                    Toast.makeText(MusicActivity.this,"播放",Toast.LENGTH_SHORT).show();
                    break;
                case 0x13:
                    Toast.makeText(MusicActivity.this,"暂停",Toast.LENGTH_SHORT).show();
                    break;
            }
            boolean isover=intent.getBooleanExtra("over",false);
            if (isover){
                if (flag_play_mode==0){
                    if (index==datas.size()-1){
                        index=0;
                    }else{
                        index++;
                    }
                    Intent intent1=new Intent("com.lt.vs.ado.Sevice");
                    intent1.putExtra("newmusic",1);
                    intent1.putExtra("music",datas.get(index));
                    sendBroadcast(intent1);
                }else if (flag_play_mode==1){
                    Intent intent1=new Intent("com.lt.vs.ado.Sevice");
                    intent1.putExtra("newmusic",1);
                    intent1.putExtra("music",datas.get(index));
                    sendBroadcast(intent1);
                }else if (flag_play_mode==2){
                    index= (int) (Math.random()*datas.size());
                    Intent intent1=new Intent("com.lt.vs.ado.Sevice");
                    intent1.putExtra("newmusic",1);
                    intent1.putExtra("music",datas.get(index));
                    sendBroadcast(intent1);
                }
                editor.putInt("index",index);
                editor.commit();

            }


            int duration=intent.getIntExtra("duration",-1);
            int curTime=intent.getIntExtra("curTime",-1);
            if (curTime!=-1){
                seekBar.setProgress((int) ((curTime*1.0)/duration*100));//为拖动条设置当前播放进度
                textViewTime.setText(TimeUtil.getTime(curTime)+"/"+TimeUtil.getTime(duration));
            }
        }
    }


}
