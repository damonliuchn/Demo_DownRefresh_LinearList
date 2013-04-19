package com.mentor.firstdemo.util;

import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;


public class AsyncImageLoader {
	private HashMap<String, SoftReference<Drawable>> imageCache;
	
	public AsyncImageLoader(Context mContext){
		imageCache = new HashMap<String, SoftReference<Drawable>>();//软引用的使用使得图片缓存很简单了
	}
	
	/**
	 * 下载图片。
	 */ 
	public Drawable loadDrawable(final String imageUrl, final ImageCallback imageCallback)
	{
		if(imageCache.containsKey(imageUrl)){
			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if(drawable != null){
				return drawable;
			}
		}
		final Handler handler = new Handler(){
			public void handleMessage(Message message){
				imageCallback.imageLoaded((Drawable)message.obj, imageUrl);
			}
		};
		new Thread(){
			public void run(){
				Drawable drawable = loadImageFromUrl(imageUrl);//下载图片
				imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));//加入缓存
				Message message = handler.obtainMessage(0, drawable);
				handler.sendMessage(message);
			}
		}.start();
		return null;
	}
	
	public static Drawable loadImageFromUrl(String url){
		InputStream is = null;
		try{
			URL mUrl = new URL(url);
			is = (InputStream)mUrl.getContent();
		}catch(Exception e){
			LogUtil.e("There", e.toString());
		}
		Drawable d = Drawable.createFromStream(is, "src");
		return d;
	}
	
	public interface ImageCallback{
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}


	public void setImage(final View listView, ImageView imageV ,String tag,final int position,int preId) {
		imageV.setTag(tag+position);
		Drawable drawable = this.loadDrawable(tag, 
				new ImageCallback() {
				    public void imageLoaded(Drawable imageDrawable, String imageUrl) {
				        ImageView imageViewByTag = (ImageView) listView.findViewWithTag(imageUrl+position);
				        if (imageViewByTag!=null) {
				            imageViewByTag.setImageDrawable(imageDrawable);
				        }
				    }
				    });
	    if(drawable==null){
	    	imageV.setImageResource(preId);
	    }
	    else{
	    	//Bitmap bitmap=ImgUtil.drawableToBitmap(drawable);
	        //imageV.setImageBitmap(bitmap);
	        imageV.setImageDrawable(drawable);
	    }
	}
}
