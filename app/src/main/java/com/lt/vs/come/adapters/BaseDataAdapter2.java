package com.lt.vs.come.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lt.vs.come.R;
import com.lt.vs.come.entrys.MediaItem;

import com.lt.vs.come.utils.TimeUtil;

import java.util.List;

/**
 * Created by 金色VS时空 on 2017/9/26.
 */

public class BaseDataAdapter2 extends BaseAdapter {
    private Context context;
    private List<MediaItem> datas;

    private LayoutInflater inflater;
    public BaseDataAdapter2(Context context, List<MediaItem> datas) {
        this.context = context;
        this.datas = datas;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();

    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.base_item,parent,false);

            viewHolder=new ViewHolder();
            viewHolder.pic= (ImageView) convertView.findViewById(R.id.base_pic);
            viewHolder.name= (TextView) convertView.findViewById(R.id.base_name);
            viewHolder.author= (TextView) convertView.findViewById(R.id.base_author);
            viewHolder.time= (TextView) convertView.findViewById(R.id.base_time);
            convertView.setTag(viewHolder);
        }
        convertView.setBackgroundResource(R.drawable.selector);
        viewHolder= (ViewHolder) convertView.getTag();
        viewHolder.pic.setBackgroundResource(R.mipmap.ic_launcher);
        viewHolder.name.setText(datas.get(position).getName());
        viewHolder.author.setText(datas.get(position).getAuthor());
        long time=datas.get(position).getTotaltime();
        viewHolder.time.setText(TimeUtil.getTime(time));
        return convertView;
    }

    class ViewHolder{
        ImageView pic;
        TextView name;
        TextView author;
        TextView time;
    }
}
