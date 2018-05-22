package com.lt.vs.come.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.lt.vs.come.R;
import com.lt.vs.come.custom.PasswordsInputKeyBoardView;

import java.util.ArrayList;
import java.util.List;

public class PayKeyBoardActivity extends AppCompatActivity {
    private PasswordsInputKeyBoardView passwordinputkkeyboardview;
    private TextView textnumbers;
    StringBuffer str=new StringBuffer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pay_key_board);
        passwordinputkkeyboardview= (PasswordsInputKeyBoardView) findViewById(R.id.passwordinputkkeyboardview);
        textnumbers= (TextView) findViewById(R.id.textnumbers);
        passwordinputkkeyboardview.setmOnNumberSelectedListener(new PasswordsInputKeyBoardView.OnNumberSelectedListener() {
            @Override
            public void onNumberSelected(String numberStr) {
                    //Log.e("=====@@","按下；了吗");

                if (str.toString().length()==6){//等于6位只能删除
                    if (numberStr.equals("×")){
                        str.deleteCharAt(str.length()-1);
                    }
                }else if(str.toString().length()<6&&str.toString().length()>0){//小于6位并且大于0可以删除或添加
                        if (numberStr.equals("×")){
                            str.deleteCharAt(str.length()-1);
                        }else{
                            str.append(numberStr);
                        }
                }else{
                    //等于0，只能添加
                    if (!numberStr.equals("×"))
                        str.append(numberStr);
                }
                textnumbers.setText(str.toString());
                }

        });
    }
}
