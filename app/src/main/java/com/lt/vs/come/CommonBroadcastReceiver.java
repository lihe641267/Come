package com.lt.vs.come;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 金色VS时空 on 2017/9/15.
 */

public class CommonBroadcastReceiver extends BroadcastReceiver {
    private TextView textView;

    public CommonBroadcastReceiver(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent!=null){
            String info = intent.getStringExtra("info");
            String net=intent.getStringExtra("net");
            if (null!=info&&null!=net){
                textView.setText(net);
                Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
            }

        }
    }
}
