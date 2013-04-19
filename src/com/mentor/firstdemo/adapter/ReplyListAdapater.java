package com.mentor.firstdemo.adapter;

import java.util.List;

import com.mentor.firstdemo.R;
import com.mentor.firstdemo.model.Reply;
import com.mentor.firstdemo.util.AsyncImageLoader;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
public class ReplyListAdapater extends BaseAdapter {
	
	private static final String TAG="CommentListAdapater";
	
	private Context mContext;
	private List<Reply> rbList;
	private AsyncImageLoader asyncImageLoader;
	private View listView;
	public ReplyListAdapater(Context mContext,List<Reply> rbList,View listView) {
		this.mContext = mContext;
		this.rbList = rbList;
		this.listView =listView;
		asyncImageLoader = new AsyncImageLoader( mContext);
	}

	public int getCount() {
		if (rbList==null||rbList.size()==0) {
			return 0;
		}
		return rbList.size();
	}

	public Object getItem(int position) {
		return rbList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ReplyHolder rpHolder = null;
		if (convertView == null) {
			rpHolder = new ReplyHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.replylist_itemview, null);
			rpHolder.ivMsgCommentUserIcon = (ImageView) convertView.findViewById(R.id.ivCommentUserIcon);
			
			rpHolder.tvMsgCommentUserName = (TextView) convertView.findViewById(R.id.tvCommentUserName);
			
			rpHolder.tvMsgCommentWbContent = (TextView) convertView.findViewById(R.id.tvCommentWbContent);
			convertView.setTag(rpHolder);
		}else{
			rpHolder = (ReplyHolder) convertView.getTag();
		}
		
		Reply wb = rbList.get(position);
		//执行
		if (wb != null) {

			asyncImageLoader.setImage(listView,rpHolder.ivMsgCommentUserIcon, wb.getUseravstar(),position,R.drawable.ic_launcher);
			
			rpHolder.tvMsgCommentUserName.setText(wb.getUsername());//用户
			
			rpHolder.tvMsgCommentWbContent.setText(wb.getText());//内容
			
			
		}
		return convertView;
	}
	
	public void addMoreData(List<Reply> moreWbList){
		this.rbList.addAll(moreWbList);
		this.notifyDataSetChanged();
	}

	public List<Reply> getSsList() {
		return rbList;
	}
	
	class ReplyHolder {

		public ReplyHolder() {
		}
		
		public ImageView ivMsgCommentUserIcon;
		public ImageView ivMsgCommentUserIconV;
		public TextView tvMsgCommentUserName;
		public TextView tvMsgCommentWbTime;
		public TextView tvMsgCommentWbContent;
		public TextView tvMsgCommentRtContent;
	}
}