package com.lt.vs.come.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.lt.vs.come.R;
import com.lt.vs.come.custom.MyView;
import com.lt.vs.come.custom.MyViewPager;

public class EventActivity extends AppCompatActivity {
    private MyViewPager myViewPager;

    public static TextView getTv_offset() {
        return tv_offset;
    }

    private static TextView tv_offset;

    public static TextView getTv_offset2() {
        return tv_offset2;
    }

    private static TextView tv_offset2;
    private MyView myview;
    private int [] Ids={R.drawable.a1,R.drawable.a3,R.drawable.a4,R.drawable.a5};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_event);
        myViewPager= (MyViewPager) findViewById(R.id.myviewpager);
        tv_offset= (TextView) findViewById(R.id.tv_offset);
        tv_offset2= (TextView) findViewById(R.id.tv_offset2);
        myview= (MyView) findViewById(R.id.myview);

        addPicData();


    }

    private void addPicData() {
        for (int i = 0; i <Ids.length ; i++) {
            ImageView imageView=new ImageView(this);
            imageView.setImageResource(Ids[i]);
            myViewPager.addView(imageView);
        }
    }


}
