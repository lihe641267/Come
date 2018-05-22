package com.lt.vs.come.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lt.vs.come.R;

import java.util.ArrayList;
import java.util.List;

public class RefreshAndLoadActivity extends AppCompatActivity {
    private RecyclerView recyclerview;
    private SwipeRefreshLayout swiperefreshlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_and_load);
        recyclerview= (RecyclerView) findViewById(R.id.recyclerview);
        swiperefreshlayout= (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        LinearLayoutManager manager=
                new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerview.setLayoutManager(manager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setAdapter(new MyRecyclerViewAdapter(this,getData()));

        //下拉刷新监听
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下面是网络请求数据的耗时操作，应该开启子线程
                Toast.makeText(RefreshAndLoadActivity.this,"刷新了",Toast.LENGTH_SHORT).show();
                swiperefreshlayout.setRefreshing(false);
            }
        });

    }

    public List<String> getData() {
        List<String> datas=new ArrayList();
        for (int i = 0; i <30 ; i++) {
            datas.add("小柠檬，爸爸妈妈爱你 "+i+" 万年");
        }
        return datas;
    }

    private class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.myViewHolder> {
        private Context context;
        private List<String> mydata;
        public MyRecyclerViewAdapter(Context context,List<String> mydata) {
            this.context=context;
            this.mydata=mydata;
        }

        @Override
        public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView= LayoutInflater.from(context).inflate(R.layout.mytext,null);
            myViewHolder holder=new myViewHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(myViewHolder holder, int position) {
            holder.textView.setText(mydata.get(position));
        }

        @Override
        public int getItemCount() {
            return mydata.size();
        }

        class myViewHolder extends RecyclerView.ViewHolder {
            TextView textView;
            public myViewHolder(View itemView) {
                super(itemView);
                textView= (TextView) itemView.findViewById(R.id.mytext);
            }
        }
    }


}
