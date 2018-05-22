package com.lt.vs.come.views;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.lt.vs.come.MainActivity;
import com.lt.vs.come.R;

public class NotificationActivity extends AppCompatActivity {
    private Button noti_normal,noti_cover,noti_fly;
    private NotificationManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initView();

    }

    private void initView() {
        noti_normal=(Button) findViewById(R.id.notification_normal);
        noti_cover=(Button) findViewById(R.id.notification_cover);
        noti_fly=(Button) findViewById(R.id.notification_fly);

        noti_normal.setOnClickListener(mOnClickListener);
        noti_cover.setOnClickListener(mOnClickListener);
        noti_fly.setOnClickListener(mOnClickListener);
        manager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }
    View.OnClickListener mOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int Id=v.getId();
            switch(Id){
                case R.id.notification_normal:
                    getNormalNotification();
                    break;
                case R.id.notification_cover:
                    getCoverNotification();
                    break;
                case R.id.notification_fly:
                    getFlyNotification();
                    break;
            }
        }
    };



    private void getNormalNotification() {
        Notification.Builder builder=new Notification.Builder(this);
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://baidu.com"));
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
        long []a={100,300};
        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher))
                .setAutoCancel(true)
                .setContentText("大神快点解救我吧")
                .setVibrate(a)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("这是一个普通的通知");
        Notification fication=builder.build();
        manager.notify(0,fication);

    }
    private void getCoverNotification() {
        //折叠通知要使用远程视图RemoteViews来实现
        Notification.Builder builder=new Notification.Builder(this);
        Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.baidu.com"));
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
        RemoteViews remoteViews=new RemoteViews(getPackageName(),R.layout.cover_view);
        Notification fication=builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher))
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentText("这是一个神话,天之涯海之角,天之涯海之角,天之涯海之角")
                .setContentTitle("这是折叠通知")
                .build();
        fication.bigContentView=remoteViews;
        manager.notify(1,fication);
    }

    private void getFlyNotification() {
        Notification.Builder bulider=new Notification.Builder(this);
        bulider.setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher))
                .setWhen(System.currentTimeMillis())
                .setContentText("过年就是根适合打麻将")
                .setContentTitle("我是悬挂通知")
                .setAutoCancel(true);
        //点击跳转
        Intent hangIntent=new Intent();
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        hangIntent.setClass(this, MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,hangIntent,PendingIntent.FLAG_CANCEL_CURRENT);
        bulider.setFullScreenIntent(pendingIntent,true);
        manager.notify(2,bulider.build());
        finish();
    }
}
