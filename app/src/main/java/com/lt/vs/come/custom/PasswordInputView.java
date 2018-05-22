package com.lt.vs.come.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 金色VS时空 on 2018/2/19.
 */

public class PasswordInputView extends View {
    public PasswordInputView(Context context) {
        super(context);
        initPaint();
    }

    public PasswordInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public PasswordInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }
    private void initPaint() {

        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);;//Color.parseColor("#000000")
        paint.setStrokeWidth(6f);
        paint.setAntiAlias(true);
    }

    private Paint paint;
    private int mHeight;

    /**
     * 线宽
     */
    private float strokeWidth=1.5f;

    /**
     * 获取用户输入了第几位密码，根据位数绘制圆点代替,默认没有输入为0，不会绘制圆点
     */
    private int numberPath=2;
    /**
     * passwordsBoxsNumbers 密码框数量，可动态设置，默认为6个
     */
    private int passwordsBoxsNumbers =4;



    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public int getNumberPath() {
        return numberPath;
    }

    public void setNumberPath(int numberPath) {
        this.numberPath = numberPath;
    }
    private int radius=10;

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setPasswordsBoxsNumbers(int passwordsBoxsNumbers) {
        this.passwordsBoxsNumbers = passwordsBoxsNumbers;
    }

    /**
     * 间隔宽度设置为高
     * startX 初始X
     */
    private int startX;
    /**
     * 间隔宽度设置为高
     * startY 初始Y
     */
    private int startY;
    private int endY;
    /**
     * centerCircleX 画圆点的中心点X坐标
     */
    private int centerCircleX;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mHeight=getHeight();
        int reHeight=mHeight*2/3;//控件内容高
        int reWidth=reHeight*passwordsBoxsNumbers;//控件内容宽，是passwordsBoxsNumbers倍高
        int distance =(getWidth()-reWidth)/2;
        startX=distance;
        startY=(mHeight-reHeight)/2;
        endY=mHeight-startY;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2*strokeWidth);
        //画粗外框矩形
        canvas.drawRect(distance,startY,distance+reWidth,endY,paint);
        //画细竖线
        for (int i = 0; i < passwordsBoxsNumbers-1 ; i++) {
            paint.setStrokeWidth(strokeWidth);
            startX+=reHeight;
            canvas.drawLine(startX,startY,startX,endY,paint);
        }

        //准备画圆点
        paint.setStyle(Paint.Style.FILL);
        centerCircleX = distance+reHeight/2;
        for (int j = 0; j < numberPath; j++) {
            canvas.drawCircle(centerCircleX,mHeight/2,radius,paint);
            centerCircleX+=reHeight;
        }
    }



    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightSize=MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode==MeasureSpec.AT_MOST&&heightMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(800,80);
        }else if (widthMode==MeasureSpec.AT_MOST) {
            setMeasuredDimension(800,heightSize);
        }else if (heightMeasureSpec==MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSize,80);
        }


    }
}
