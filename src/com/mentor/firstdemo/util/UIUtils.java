package com.mentor.firstdemo.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


/**
 * 管理UI相关
 * @author RAIN
 */
public class UIUtils {
	
	public static final int LLW = LinearLayout.LayoutParams.WRAP_CONTENT;
	public static final int LLF = LinearLayout.LayoutParams.FILL_PARENT;
	public static final int RLW = RelativeLayout.LayoutParams.WRAP_CONTENT;
	public static final int RLF = RelativeLayout.LayoutParams.FILL_PARENT;
	public static final int FLW = FrameLayout.LayoutParams.WRAP_CONTENT;
	public static final int FLF = FrameLayout.LayoutParams.FILL_PARENT;
	
	public static LinearLayout.LayoutParams getLllp(int width,int height){
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, height);
		return lp;
	}
	
	public static RelativeLayout.LayoutParams getRllp(int width,int height){
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
		return lp;
	}
	
	public static FrameLayout.LayoutParams getFllp(int width,int height){
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(width, height);
		return lp;
	}
	
	public static int getScreenWidth(Activity a){
		DisplayMetrics metric = new DisplayMetrics();
        a.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        return width;
	}
	
	public static void showToastLong(Context ctx , int resId){
		Toast.makeText(ctx, ctx.getText(resId), Toast.LENGTH_LONG).show();
	}
	
	public static void showToastLong(Context ctx , String content){
		Toast.makeText(ctx, content, Toast.LENGTH_LONG).show();
	}
	
	public static void showToastShort(Context ctx , String content){
		Toast.makeText(ctx, content, Toast.LENGTH_SHORT).show();
	}
	
	public static void showToastShort(Context ctx , int resId){
		Toast.makeText(ctx, ctx.getText(resId), Toast.LENGTH_SHORT).show();
	}
	
    /** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  

    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }
    /**
     * 显示不定时进度条
     * @param con
     * @param resId
     * @return
     */
	public static ProgressDialog createLoadingDialog(Context con, int resId) {
		ProgressDialog mProgressDialog = new ProgressDialog(con);
		mProgressDialog.setMessage(con.getText(resId));
		mProgressDialog.setIndeterminate(true);
		return mProgressDialog;
	}
	
    /**
     * 显示不定时进度条
     * @param con
     * @param resId
     * @return
     */
	public static ProgressDialog createLoadingDialog(Context con, String message) {
		ProgressDialog mProgressDialog = new ProgressDialog(con);
		mProgressDialog.setMessage(message);
		mProgressDialog.setIndeterminate(true);
		return mProgressDialog;
	}
	
	/**
	 * 创建进度条对话框
	 * @param con
	 * @param title
	 * @param max
	 * @return
	 */
	public static ProgressDialog createLoadingDialog(Context con, String title, int max) {
		ProgressDialog mProgressDialog = new ProgressDialog(con);
		mProgressDialog.setTitle(title);
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setMax(max);
		mProgressDialog.setProgress(0);
		mProgressDialog.setMessage(mProgressDialog.getProgress()+"/"+mProgressDialog.getMax());
		return mProgressDialog;
	}
	
	/**
	 * 创建提示对话框
	 * @param ctx
	 * @param title
	 * @param message
	 * @return
	 */
	public static AlertDialog createAlertDialog(Context ctx, String title,String message){
		AlertDialog alertDialog = new AlertDialog.Builder(ctx)
			    .setTitle(title)
			    .setMessage(message)
			    .setNeutralButton("", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				})
			    .create(); 
		return alertDialog;
	}

	/**
	 * 创建确认对话框
	 * @param ctx
	 * @param title
	 * @param message
	 * @param confirmButtonString 确认按钮显示的内容
	 * @param confirmOnClickListener 点击确认按钮执行的操作
	 * @return
	 */
	public static AlertDialog createAlertDialog(Context ctx,String title,String message, 
			String confirmButtonString,
			DialogInterface.OnClickListener confirmOnClickListener){
		AlertDialog alertDialog = new AlertDialog.Builder(ctx)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton(confirmButtonString, confirmOnClickListener)
		.setNegativeButton("", 
			new android.content.DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			}
		)
		.create();
		return alertDialog;
	}
	/**
     * 设置按钮各状态的颜色
     * @param
     * @return
     * @author liumeng 2012-12-24下午03:20:51
     */
    public static StateListDrawable getDrawable(Context context,int unselectId,int selectedId){
		StateListDrawable bg=new StateListDrawable();
		Drawable normal=context.getResources().getDrawable(unselectId);
		Drawable pressed=context.getResources().getDrawable(selectedId);
		bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
		bg.addState(new int[] { android.R.attr.state_enabled }, normal);
		bg.addState(new int[]{}, normal);
		return bg;
	}
    
    /** 
     * getContentView
     * @param Activity 
     * @return view
     * @author liumeng 2013-1-9上午11:43:43
     */
    public static View getContentView(Activity ac){
        //ViewGroup view = (ViewGroup)ac.getWindow().getDecorView();
        //FrameLayout content = (FrameLayout)view.getChildAt(0);
        //return content.getChildAt(0);
        //以上代码在4.2 下有问题
        ViewGroup v=(ViewGroup)ac.getWindow().getDecorView().findViewById(android.R.id.content);
		return v.getChildAt(0);
    }

	
}
