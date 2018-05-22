package com.lt.vs.come.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lt.vs.come.R;
import com.lt.vs.come.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 金色VS时空 on 2017/11/23.
 */

public class FlowView extends ViewGroup {

    //记录标志  flag=0 :表示未有任何记录； flag=1 ：表示有记录；
    private int flag=0; //默认没有任何记录；
    //记录内容
    private List<String> strDatas=new ArrayList<>();
    //当前点击内容
    private String currStr=null;

    public String getCurrStr() {
        return currStr;
    }

    public void setCurrStr(String currStr) {
        this.currStr = currStr;
    }

    public FlowView(Context context) {
        super(context);
    }

    public FlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *
     * @param changed
     * @param l 左
     * @param t 上
     * @param r  右
     * @param b  下
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //获得子控件的数量
        int childCount = getChildCount();
        //当前子控件的左边坐标
        int cl = 0;
        //当前子控件的上边坐标
        int ct = 0;
        //ViewGroup整体宽度
        int width = r - l;
        //行高
        int lineHeight = 0;

        //判断是否有记录
        if (childCount==0){
            //没有记录
            TextView tv=new TextView(getContext());
            tv.setText("没有任何记录");
            addView(tv);
            View v=getChildAt(0);
            if (v instanceof TextView){
                int w=v.getMeasuredWidth();
                int h=v.getMeasuredHeight();


                ((TextView) v).setGravity(Gravity.CENTER);
                v.setPadding(DensityUtil.dip2px(getContext(),3),DensityUtil.dip2px(getContext(),3),
                        DensityUtil.dip2px(getContext(),3),DensityUtil.dip2px(getContext(),3));
                //给各自空间设置样式
                v.setBackground(getResources().getDrawable(R.drawable.round));
                getChildAt(0).layout((getWidth()-w)/2,0,(getWidth()-w)/2+w,h);

            }

        }else{
            //有记录 ，遍历所有子控件
            for(int i = 0; i < childCount; i++){
                //获取当前控件
                final View childAt = getChildAt(i);
                //将记录添加到集合
                if (childAt instanceof TextView){
                    ((TextView) childAt).setGravity(Gravity.CENTER);
                    childAt.setPadding(DensityUtil.dip2px(getContext(),3),DensityUtil.dip2px(getContext(),3),
                            DensityUtil.dip2px(getContext(),3),DensityUtil.dip2px(getContext(),3));
                    strDatas.add(((TextView) childAt).getText().toString());
                    //为子控件设置监听事件
                    childAt.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setCurrStr(((TextView) childAt).getText().toString());
                        }
                    });
                    //给各自空间设置样式
                    childAt.setBackground(getResources().getDrawable(R.drawable.round));
                }

                //获取宽度
                int cw = childAt.getMeasuredWidth();
                //获取高度
                int ch = childAt.getMeasuredHeight();
                //当前控件右边
                int cr = cl + cw;
                //当前控件下边
                int cb = ct + ch;
                //判断是否换行
                if(cr > width){
                    //如果换行重新计算上下左右地值
                    cl = 0;
                    cr = cl + cw;
                    ct += lineHeight;
                    cb = ct + ch;
                    //换行后，第一个控件作为最大行高
                    lineHeight = ch;
                }else{
                    //如果不换行，需要计算最大高度
                    lineHeight = Math.max(lineHeight,ch);
                }
                childAt.layout(cl,ct,cr,cb);
                //横向向后移动一个，前面控件的右边作为后面控件的左边
                cl = cr;
            }
        }
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        if (flag==0){

        }else{
            flag=1;
            //遍历如果加入内容与前面一致那就改变顺序，并且不再增加记录
            for (int i = 0; i < getChildCount() ; i++) {
                if (child instanceof TextView){
                    if (((TextView)child).getText().toString().equals(getChildAt(i).toString())){
                        //删除记录控件
                        removeView(getChildAt(i));
                        //移除记录数据
                        strDatas.remove(i);
                        //追加数据到尾部
                        strDatas.add(getChildCount()+1,((TextView)child).getText().toString());
                        //刷新数据
                        invalidate();

                        break;
                    }else{
                        if (i==getChildCount()-1 && i<15){
                            strDatas.add(((TextView)child).getText().toString());
                            //刷新数据
                            invalidate();
                        }
                    }
                }
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量所有子控件
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }
}