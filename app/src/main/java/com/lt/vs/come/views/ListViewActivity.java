package com.lt.vs.come.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lt.vs.come.R;
import com.lt.vs.come.adapters.BaseDataAdapter;
import com.lt.vs.come.entrys.MediaItem;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity{
    private ListView listView;
    private BaseDataAdapter adapter;
    private List<MediaItem> datas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        listView= (ListView) findViewById(R.id.lv_display);
        adapter=new BaseDataAdapter(this,getDatas());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewActivity.this,"您点击了第"+position+"项",Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewActivity.this,"您长按了第"+position+"项",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public List<MediaItem> getDatas() {
        datas=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MediaItem myData=new MediaItem();
            myData.setName("我的名字叫鸣人"+i);
            myData.setPicSouce(R.mipmap.ic_launcher);
            datas.add(myData);
        }
        return datas;
    }
}
