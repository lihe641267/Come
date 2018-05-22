package com.lt.vs.come.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lt.vs.come.R;
import com.lt.vs.come.utils.permissionUtil;

public class FunctionActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button_listView;
    private Button btn_musicplayer;
    private Button textButton;
    private Button btn_movieplayer;
    private Button btn_notification;
    private Button event_bus,passwordinput;
    private Button refresh_load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);
        findId();
        bindOnClick();
    }

    private void findId() {
        button_listView= (Button) findViewById(R.id.only_listview);
        btn_musicplayer= (Button) findViewById(R.id.music_player);
        textButton= (Button) findViewById(R.id.text_about);
        btn_movieplayer= (Button) findViewById(R.id.movie_player);
        btn_notification= (Button) findViewById(R.id.notification);
        event_bus= (Button) findViewById(R.id.event_bus);
        passwordinput= (Button) findViewById(R.id.passwordinput);
        refresh_load= (Button) findViewById(R.id.refresh_load);
    }

    private void bindOnClick() {
        button_listView.setOnClickListener(this);
        btn_musicplayer.setOnClickListener(this);
        textButton.setOnClickListener(this);
        btn_movieplayer.setOnClickListener(this);
        btn_notification.setOnClickListener(this);
        event_bus.setOnClickListener(this);
        passwordinput.setOnClickListener(this);
        refresh_load.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int Id=v.getId();
        switch (Id){
            case R.id.text_about://textView相关知识点
                startActivity(new Intent(this,TextActivity.class));break;
            case R.id.only_listview://列表展示
                startActivity(new Intent(this,ListViewActivity.class));break;
            case R.id.music_player://音乐播放器
                if (permissionUtil.isGrantExternalRW(FunctionActivity.this)){
                    startActivity(new Intent(this,MusicActivity.class));
                    finish();
                }
                break;
            case R.id.movie_player://视频播放器
                if (permissionUtil.isGrantExternalRW(FunctionActivity.this)){
                    startActivity(new Intent(this,MovieActivity.class));
                }
                break;
            case R.id.notification://3种Notification样式
                    startActivity(new Intent(this,NotificationActivity.class));
                break;
            case R.id.event_bus://3种Notification样式
                startActivity(new Intent(this,EventActivity.class));
                break;
            case R.id.passwordinput:
                startActivity(new Intent(this,PayKeyBoardActivity.class));
                break;
            case R.id.refresh_load:
                startActivity(new Intent(this,RefreshAndLoadActivity.class));

        }
    }
}
