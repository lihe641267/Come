package com.lt.vs.come.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lt.vs.come.R;
import com.lt.vs.come.adapters.BaseDataAdapter2;
import com.lt.vs.come.entrys.MediaItem;
import com.lt.vs.come.utils.MediaUtil;

import java.util.List;

public class MovieActivity extends AppCompatActivity {
    private ListView lv_movie;
    private BaseDataAdapter2 adapter;
    private Context context=MovieActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        initView();
        initClick();
        adapter=new BaseDataAdapter2(this, MediaUtil.getMovieData(context));
        lv_movie.setAdapter(adapter);



    }

    private void initClick() {
        lv_movie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<MediaItem> data=MediaUtil.getMovieData(context);
//                Log.e("======",data.get(position).getPath());
                if (data!=null && data.size()!=0){
                    String uri=data.get(position).getPath();
                    Intent intent=new Intent(context,MoviePlayingActivity.class);
                    intent.putExtra("uri",uri);
                    startActivity(intent);
                }
            }
        });
    }


    private void initView() {
        lv_movie= (ListView) findViewById(R.id.lv_movie);
    }
}
