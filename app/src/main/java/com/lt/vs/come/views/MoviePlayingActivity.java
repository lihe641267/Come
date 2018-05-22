package com.lt.vs.come.views;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import com.lt.vs.come.R;

public class MoviePlayingActivity extends AppCompatActivity {
    private VideoView vv_player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_movie_playing);

        initView();
        String uriString = getIntent().getStringExtra("uri");
        Uri uri=Uri.parse(uriString);
        vv_player.setVideoURI(uri);
        vv_player.start();
    }

    private void initView() {
        vv_player= (VideoView) findViewById(R.id.vv_player);
    }

    @Override
    protected void onPause() {
        super.onPause();
        vv_player.pause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (vv_player!=null){
            vv_player.start();
        }
    }
}
