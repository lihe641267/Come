package com.lt.vs.come.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 金色VS时空 on 2017/11/7.
 */

public class PasswordsInputKeyBoardView extends View {
    public PasswordsInputKeyBoardView(Context context) {
        super(context);
    }

    public PasswordsInputKeyBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PasswordsInputKeyBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 需要绘制的资源数据
     */
    private String [] numbers={"1","2","3","4","5","6","7","8","9","n","0","×"};
    /**
     * 行高
     */
    private float singleHeight;
    /**
     * 列宽
     */
    private float singleWidth;
    /**
     * 起始X,Y值，方便绘制网格线
     */
    private float startX;
    private float startY;
    /**
     * 绘制数字时的X位置
     */
    private float x;
    /**
     * 网格线颜色，和按下时背景颜色相同
     */
    private String gridLineOrPressColor="#cccccc";
    /**
     * 数字颜色
     */
    private String textNumberColor="#000000";
    /**
     * 默认背景颜色
     */
    private String defaultBackgroundColor="#ffffff";

    public void setDefaultBackgroundColor(String defaultBackgroundColor) {
        this.defaultBackgroundColor = defaultBackgroundColor;
    }

    /**
     * 绘制数字大小
     */
    private int textSize=60;
    /**
     * 网格线宽
     */
    private float strokeWidth=1.5f;

    public void setNumbers(String[] numbers) {
        this.numbers = numbers;
    }

    public void setGridLineOrPressColor(String gridLineOrPressColor) {
        this.gridLineOrPressColor = gridLineOrPressColor;
    }

    public void setTextNumberColor(String textNumberColor) {
        this.textNumberColor = textNumberColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint(canvas);
        singleWidth=getWidth()/3;
        singleHeight=getHeight()/4;


        //画背景颜色
        mPaint.setColor(Color.parseColor(defaultBackgroundColor));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);

        //根据是否按下，变换背景颜色
        if (isPressed){
            mPaint.setColor(Color.parseColor(gridLineOrPressColor));
            mPaint.setStyle(Paint.Style.FILL);
            //绘制按下时背景（按下的那一格）
            canvas.drawRect((float)pressedRectPosition.get(0),(float)pressedRectPosition.get(1),
                    (float)pressedRectPosition.get(2),(float)pressedRectPosition.get(3),mPaint);
        }else if (pressedRectPosition!=null&&pressedRectPosition.size()!=0){
            mPaint.setColor(Color.parseColor(defaultBackgroundColor));
            mPaint.setStyle(Paint.Style.FILL);
            //绘制离开时背景（按下的那一格）
            canvas.drawRect((float)pressedRectPosition.get(0),(float)pressedRectPosition.get(1),
                    (float)pressedRectPosition.get(2),(float)pressedRectPosition.get(3),mPaint);
            pressedRectPosition.clear();
        }

        mPaint.setColor(Color.parseColor(gridLineOrPressColor));
        for (int i = 0; i < 5 ; i++) {
            //画横线
            canvas.drawLine(startX,startY,startX+getWidth(),startY,mPaint);
            startY+=singleHeight;
        }
        //重置
        startX=0;
        startY=0;
        for (int j = 0; j < 4 ; j++) {
            //画竖线
            canvas.drawLine(startX,startY,startX,startY+getHeight(),mPaint);
            startX+=singleWidth;
            //画数据
        }
        //重置
        startX=0;
        startY=0;
        float y=0;
        mPaint.setColor(Color.parseColor(textNumberColor));
        mPaint.setFakeBoldText(true);
        mPaint.setTextSize(textSize);
        for (int i = 0; i < 4 ; i++) {
            //画数据,一行一行画
            for (int j = 0; j < 3; j++) {
                if (j==0){
                    //获取文字宽
                    float textWidth=mPaint.measureText(numbers[i*3+j]);
                    x=singleWidth/2-textWidth/2;
                }
                if (i==0){
                    //获取文字高
                    Rect re=new Rect();
                    mPaint.getTextBounds(numbers[i*3+j],0,1,re);
                    int textHeight=re.height();
                    y=singleHeight/2+textHeight/2;
                }
                if (!numbers[i*3+j].equals("n")){
                    canvas.drawText(numbers[i*3+j],x,y,mPaint);
                }
                //X内层变化(放里面)
                x+=singleWidth;
            }
            //Y外层变化(放外面)
            y+=singleHeight;
        }


    }
    private Paint mPaint;
    private void initPaint(Canvas canvas) {
        mPaint=new Paint();
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setAntiAlias(true);
    }
    //是否按下
    private boolean isPressed=false;
    //
    //表示记录按下时所在矩形框位置的集合
    private List pressedRectPosition=new ArrayList();
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action=event.getAction();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                //记录按下时刻的X,Y
                float downX=event.getX();
                float downY=event.getY();
                //边界值 （left,top,right,bottom）对应（minX,minY,maxX,maxY）
                float minX=0;
                float minY=0;
                float maxX=getWidth()/3;
                float maxY=getHeight()/4;
                for (int i = 0; i < 4; i++) {
                    //确定点击事件所在行
                    if (maxY>downY&&downY>minY){
                        for (int j = 0; j < 3; j++) {
                            //确定点击事件所在列
                            if (maxX>downX&&downX>minX&&(i*3+j)!=9){
                                mOnNumberSelectedListener.onNumberSelected(numbers[i*3+j]);
                                //按下是所在矩形位置 (minX,minY,maxX,maxY);
                                //按下状态为true
                                isPressed=true;
                                pressedRectPosition.add(minX);
                                pressedRectPosition.add(minY);
                                pressedRectPosition.add(maxX);
                                pressedRectPosition.add(maxY);
                                invalidate();
                            }
                            minX=maxX;
                            maxX=maxX+singleWidth;
                        }
                    }
                    minY=maxY;
                    maxY=maxY+singleHeight;
                }
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                isPressed=false;
                invalidate();
                break;
        }
        return true;
    }

    private OnNumberSelectedListener mOnNumberSelectedListener;

    public void setmOnNumberSelectedListener(OnNumberSelectedListener mOnNumberSelectedListener) {
        this.mOnNumberSelectedListener = mOnNumberSelectedListener;
    }

    /**
     * 点击事件接口
     */
    public interface OnNumberSelectedListener{
        void onNumberSelected(String numberStr);
    }
}
