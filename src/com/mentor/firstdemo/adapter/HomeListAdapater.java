package com.mentor.firstdemo.adapter;

import java.util.List;

import com.mentor.firstdemo.R;
import com.mentor.firstdemo.model.Post;
import com.mentor.firstdemo.util.AsyncImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class HomeListAdapater extends BaseAdapter {

	private Context mContext;
	private List<Post> ssList;
	private ProgressBar pb;
	private AsyncImageLoader asyncImageLoader;
	private ListView listView;
	public HomeListAdapater(Context mContext, List<Post> ssList,ListView listView) {
		this.mContext =mContext; 
		this.ssList = ssList;
		this.listView =listView;
		asyncImageLoader = new AsyncImageLoader( mContext);
	}
	
	public int getCount() {
		if (ssList==null) {
			return 0;
		}
		return ssList.size();
	}

	public Object getItem(int position) {
		return ssList.get(position);
	}

	public long getItemId(int position) {
		return Integer.parseInt(ssList.get(position).getId());
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		PostHolder ph = null;
		if (convertView == null) {
			ph = new PostHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.homelist_itemview, null);
			ph.wbicon = (ImageView) convertView.findViewById(R.id.ivUserIcon);
			ph.wbuser = (TextView) convertView.findViewById(R.id.tvUserName);
			ph.wbcontent = (TextView) convertView.findViewById(R.id.tvWbContent);
			ph.wbreply = (TextView) convertView.findViewById(R.id.tvReplyNum);
			convertView.setTag(ph);
		} else {
			ph = (PostHolder) convertView.getTag();
		}
		
		Post wb = ssList.get(position);
		
		if (wb != null) {
			ph.wbicon.setVisibility(View.VISIBLE);
			asyncImageLoader.setImage(listView,ph.wbicon, wb.getUseravstar(),position,R.drawable.ic_launcher);
			ph.wbuser.setText(wb.getUsername());// 用户名称
			ph.wbcontent.setText(wb.getText());
			ph.wbreply.setText(wb.getReply_count());
	
		}

		return convertView;
	}

	public void addMoreData(List<Post> moreWbList) {
		this.ssList.addAll(moreWbList);
		this.notifyDataSetChanged();
	}

	public List<Post> getSsList() {
		return ssList;
	}

	static class PostHolder {

		public PostHolder() {
		}

		public ImageView wbicon;
		public TextView wbuser;
		public TextView wbcontent;
		public TextView wbreply;
		

	}
	
}