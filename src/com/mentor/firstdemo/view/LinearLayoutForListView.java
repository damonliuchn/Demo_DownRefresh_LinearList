package com.mentor.firstdemo.view;


import com.mentor.firstdemo.util.LogUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;

public class LinearLayoutForListView extends LinearLayout {

    private Adapter adapter;
    private OnClickListener onClickListener = null;
    private int currentNum=-1;
    private boolean isFoot=false;
    
    public LinearLayoutForListView(Context context) {
        super(context);
        

    }

    public LinearLayoutForListView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
    /**
     * 设置点击监听
     * 
     * @param OnClickListener
     */
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    /**
     * 设置数据适配器
     * 
     * @param adpater
     */
    public void setAdapter(Adapter adpater) {
        this.adapter = adpater;
        bindLinearLayout();
    }
    /**
     * addview
     */
    public void bindLinearLayout() {
        int count = adapter.getCount();
        for (int i = currentNum+1; i < count; i++) {
            View v = adapter.getView(i, null, null);
            v.setOnClickListener(this.onClickListener);
            v.setVisibility(View.VISIBLE);
            currentNum=i;
            addView(v, i);
            if(v==null)LogUtil.i("aaaa__null");
            LogUtil.i("aaaa__");
            
        }
    }
    /**
     * add more data
     * 
     * @param adpater
     */
    public void addMoreData() {
       try{
    	   bindLinearLayout();
       }catch(Exception e){}
    	
    }
 


    /**
     * ��ȡAdapter
     * 
     * @return adapter
     */
    public Adapter getAdpater() {
        return adapter;
    }



    /**
     * ��ȡ����¼�
     * 
     * @return
     */
    public OnClickListener getOnclickListner() {
        return onClickListener;
    }

	public void addFooterView(LinearLayout footerView, Object object, boolean b) {
		// TODO Auto-generated method stub
		addView(footerView, currentNum+1);
		isFoot=true;
	}

	public void removeFooterView() {
		// TODO Auto-generated method stub
		if(isFoot){
		 removeViewAt(currentNum+1);
		 isFoot=false;
		}
	}



}
