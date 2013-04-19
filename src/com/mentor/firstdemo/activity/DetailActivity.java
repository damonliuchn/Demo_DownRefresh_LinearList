package com.mentor.firstdemo.activity;

import java.io.InputStream;
import java.util.List;

import com.mentor.firstdemo.BaseActivity;
import com.mentor.firstdemo.MyApplication;
import com.mentor.firstdemo.R;
import com.mentor.firstdemo.R.layout;
import com.mentor.firstdemo.R.menu;
import com.mentor.firstdemo.adapter.HomeListAdapater;
import com.mentor.firstdemo.adapter.ReplyListAdapater;
import com.mentor.firstdemo.model.AccessTokenHandler;
import com.mentor.firstdemo.model.Post;
import com.mentor.firstdemo.model.Reply;
import com.mentor.firstdemo.model.ReplyHandler;
import com.mentor.firstdemo.util.AsyncImageLoader;
import com.mentor.firstdemo.util.Config;
import com.mentor.firstdemo.util.HttpUtil;
import com.mentor.firstdemo.util.LogUtil;
import com.mentor.firstdemo.util.UIUtils;
import com.mentor.firstdemo.util.XMLUtil;
import com.mentor.firstdemo.view.LinearLayoutForListView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends BaseActivity{
	
	private Post post;
	private ImageView detail_ivThisIcon;
	private TextView detail_tvThisName;
	private TextView detail_tvThisMessage;
	private LinearLayoutForListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);
		Bundle bundle=getIntent().getExtras();
		post=(Post)bundle.get("post");
		initView();
		new GetDataAsyncTask().execute(post.getGuid());
	}
	public void initView(){
		detail_ivThisIcon=(ImageView)findViewById(R.id.detail_ivThisIcon);
		new AsyncImageLoader(this).setImage(getContentView(this), detail_ivThisIcon, post.getUseravstar(), 0, R.drawable.ic_launcher);
		detail_tvThisName=(TextView)findViewById(R.id.detail_tvThisName);
		detail_tvThisName.setText(post.getUsername());
		detail_tvThisMessage=(TextView)findViewById(R.id.detail_tvThisMessage);
		detail_tvThisMessage.setText(post.getText());
		listView=(LinearLayoutForListView)findViewById(R.id.detail_lvCommentList);
	}
	
	class GetDataAsyncTask extends AsyncTask<String,Void,List<Reply>>{

		private ProgressDialog pd=null;
		public GetDataAsyncTask(){
			pd=new ProgressDialog(DetailActivity.this);
			
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			super.onPreExecute();
			pd.show();
		}

		@Override
		protected List<Reply> doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlstr = new StringBuffer().append(Config.POSTREPLY)
					.append("?").append("access_token=").append(MyApplication.access_token)
					.append("&").append("p_id=").append(params[0])
					.toString();
			InputStream resultXml=HttpUtil.httpByGet2InputStreamSSL(urlstr);
			
			//解析xml喽
			ReplyHandler ath=new ReplyHandler();
			XMLUtil.parseXml(resultXml,ath);
			return ath.getResult();
			
		}
		
		@Override
		protected void onPostExecute(List<Reply> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			if(result!=null&&result.size()>0){
				LogUtil.i(result.get(0).getText());
				
				ReplyListAdapater adapter = new ReplyListAdapater(DetailActivity.this,result, listView);
				listView.setAdapter(adapter);
			}
		}
	}
	/** 
     * getContentView
     * @param Activity 
     * @return view
     * @author liumeng 
     */
    public static View getContentView(Activity ac){
        ViewGroup v=(ViewGroup)ac.getWindow().getDecorView().findViewById(android.R.id.content);
		return v.getChildAt(0);
    }
}
