package com.lt.vs.come.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lt.vs.come.R;
import com.lt.vs.come.entrys.MediaItem;

import java.util.List;

/**
 * Created by 金色VS时空 on 2017/9/26.
 */

public class BaseDataAdapter extends BaseAdapter {
    private Context context;
    private List<MediaItem> datas;
    private LayoutInflater inflater;
    public BaseDataAdapter(Context context, List<MediaItem> datas) {
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
            convertView=inflater.inflate(R.layout.base_data_layout,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.base_iv);
            viewHolder.textView= (TextView) convertView.findViewById(R.id.base_tv);
            convertView.setTag(viewHolder);
        }
        viewHolder= (ViewHolder) convertView.getTag();
        viewHolder.imageView.setImageResource(datas.get(position).getPicSouce());
        viewHolder.textView.setText(datas.get(position).getName());
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}
