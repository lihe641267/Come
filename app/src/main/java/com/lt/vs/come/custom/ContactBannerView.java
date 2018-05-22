package com.lt.vs.come.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
//import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
//import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 金色VS时空 on 2018/2/20.
 */

public class ContactBannerView extends View {

    public ContactBannerView(Context context) {
        super(context);

    }

    public ContactBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint mPaint;
    /**
     * 字母列表资源
     */
    private String []letters={"A","B","C","D","E","F","G","H",
                                "I","J","K","L","M","N","O",
                                "P","Q","R","S","T","U","V",
                                "W","X","Y","Z"};

    /*private char[] chars={'A','B','C','D','E','F','G','H',
            'I','J','K','L','M','N','O',
            'P','Q','R','S','T','U','V',
            'W','X','Y','Z'};*/
    private OnLetterSelectedListener mOnLetterSelectedListener;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();

        //画初始默认背景色
        if (isTouch){
            //画按下时背景色
            mPaint.setColor(Color.parseColor(pressedColor));
        }else{
            //画默认背景色
            mPaint.setColor(Color.parseColor(defaultColor));
        }
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0,0,getWidth(),getHeight(),mPaint);


        //绘制字母列表
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(35);
        int length=letters.length;
        float padding=getHeight()/length;
        float startX=getWidth()/2;
        float startY=padding-10;
        Log.e("==========",""+startY);
        for (int i = 0; i <length ; i++) {
            if (i==(int)maginY/(int)padding){
                if (mOnLetterSelectedListener!=null){
                    mOnLetterSelectedListener.onLetterSelected(letters[i]);
                }
                if (textInfo!=null){
                    textInfo.setText(letters[i]);
                }
                mPaint.setColor(Color.BLUE);//选中字母绘制蓝色
            }else{
                mPaint.setColor(Color.BLACK);//未选中字母绘制黑色
            }
            canvas.drawText(letters[i],startX-mPaint.measureText(letters[i]) / 2,startY,mPaint);
            startY+=padding;

        }
    }

    /**
     * 获取按下或滑动时的高度
     */
    private float maginY;
    /**
     * 手是否按下本控件
     */
    private boolean isTouch = false;
    /**
     * 列表的触摸事件，获取高度
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action=event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                //记录按下时高度
                maginY=event.getRawY();
                isTouch=true;
                if (textInfo!=null){
                    textInfo.setVisibility(VISIBLE);
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                //记录滑动时高度
                maginY=event.getRawY();
                isTouch=true;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                isTouch=false;
                if (textInfo!=null){
                    textInfo.setVisibility(GONE);
                }
                invalidate();
                break;
        }
        return true;
    }

    /**
     * strokeWidth 绘制笔的粗细，给出方法可以设置，默认为4f
     */
    private static float strokeWidth=4f ;
    public ContactBannerView setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }

    private void initPaint() {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(strokeWidth);
    }

    /**
     * 外部回调接口，传值为当前选中的字母
     */
    public interface OnLetterSelectedListener{
        void onLetterSelected(String letter);
    }

    /**
     *
     * @param mOnLetterSelectedListener 设置外部调用，监听当前字母变化
     */
    public void setmOnLetterSelectedListener(OnLetterSelectedListener mOnLetterSelectedListener) {
        this.mOnLetterSelectedListener = mOnLetterSelectedListener;
    }

    /**
     * textInfo 外部传入的TextView控件，用于向用户展示选中字母
     */
    private TextView textInfo;
    public void setTextInfo(TextView textInfo) {
        this.textInfo = textInfo;
    }
    /**
     * 按下时控件背景
     */
    private static String pressedColor="#88000000";
    /**
     * 默认控件背景
     */
    private static String defaultColor="#33000000";
    public ContactBannerView setPressedColor(String pressedColor) {
        this.pressedColor = pressedColor;
        return this;
    }
    public ContactBannerView setDefaultColor(String defaultColor) {
        this.defaultColor = defaultColor;
        return this;
    }
}
