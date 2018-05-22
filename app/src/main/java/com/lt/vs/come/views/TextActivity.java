package com.lt.vs.come.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lt.vs.come.R;

public class TextActivity extends AppCompatActivity {
    private TextView textView1,textView2;
    private String s1,s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        init();
        //textView1效果;显示个人联系方式并实现超链接
        s1="个人主页：http://www.baidu.com\n";
        s1+="电子邮箱：sophie@baidu.com\n";
        s1+="联系电话：13547916579";
        textView1.setText(s1);


        s2="点击【这里】显示";
        SpannableString ss=new SpannableString(s2);
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(TextActivity.this,"点击了这里",Toast.LENGTH_SHORT).show();
            }
        }, 3, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView2.setText(ss);
        textView2.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void init() {
        textView1= (TextView) findViewById(R.id.textview1);
        textView2= (TextView) findViewById(R.id.textview2);

    }
}
