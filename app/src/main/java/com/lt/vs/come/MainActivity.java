package com.lt.vs.come;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lt.vs.come.R;
import com.lt.vs.come.custom.FlowView;
import com.lt.vs.come.utils.IntenetUtil;
import com.lt.vs.come.utils.NetSpeedUtil;
import com.lt.vs.come.views.FunctionActivity;

import java.util.Timer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button sendButton;
    private TextView textInfo;
    private CommonBroadcastReceiver commonBroadcastReceiver;
    private Button button;
    private FlowView flowView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sendButton= (Button) findViewById(R.id.broadcast_common);
        textInfo= (TextView) findViewById(R.id.text_info);
        button= (Button) findViewById(R.id.function);
        flowView= (FlowView) findViewById(R.id.flowview);


        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.lt.vs.ado");
        commonBroadcastReceiver=new CommonBroadcastReceiver(textInfo);
        registerReceiver(commonBroadcastReceiver,intentFilter);
        sendButton.setOnClickListener(this);
        button.setOnClickListener(this);

        for (int i = 0; i < 10; i++) {
           TextView tv= new TextView(this);
            tv.setText("===999");
            flowView.addView(tv);
        }

    }

    @Override
    public void onClick(View v) {
        int Id=v.getId();
        if (Id==R.id.broadcast_common){
            NetSpeedUtil netSpeedUtil=new NetSpeedUtil(this,textInfo);
            new Timer().schedule(netSpeedUtil.getCurrentSpeed(),1000,2000);

            Intent intent=new Intent();
            intent.setAction("com.lt.vs.ado");
            intent.putExtra("info","这是普通广播消息");
            //检测当前网络环境
            String currentNet= IntenetUtil.getNetworkState(this);
            intent.putExtra("net",currentNet);
            sendBroadcast(intent);
        }else if (Id==R.id.function){
            startActivity(new Intent(this, FunctionActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(commonBroadcastReceiver);
    }
}
