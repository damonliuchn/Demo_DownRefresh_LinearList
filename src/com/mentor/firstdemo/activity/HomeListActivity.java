package com.mentor.firstdemo.activity;

import java.io.InputStream;
import java.util.List;

import com.mentor.firstdemo.BaseActivity;
import com.mentor.firstdemo.MyApplication;
import com.mentor.firstdemo.R;
import com.mentor.firstdemo.adapter.HomeListAdapater;
import com.mentor.firstdemo.model.Post;
import com.mentor.firstdemo.model.PostHandler;
import com.mentor.firstdemo.util.Config;
import com.mentor.firstdemo.util.HttpUtil;
import com.mentor.firstdemo.util.LogUtil;
import com.mentor.firstdemo.util.XMLUtil;
import com.mentor.firstdemo.view.DownRefreshListView;
import com.mentor.firstdemo.view.OnDownRefreshListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HomeListActivity extends BaseActivity implements OnClickListener,OnDownRefreshListener{
	
	private EditText home_searchEt;
	private Button home_searchBtn;
	private DownRefreshListView listView;
	private HomeListAdapater ssAdapater;
	private View progressBar;
	
	//private String accessToken;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homelist);
		initView();
		new GetDataTask().execute(MyApplication.access_token);
	}
	public void initView(){
		home_searchEt=(EditText)this.findViewById(R.id.home_searchET);
		home_searchBtn=(Button)this.findViewById(R.id.home_searchBtn);
		listView=(DownRefreshListView)this.findViewById(R.id.home_DownRefreshListView);
		listView.setOnItemClickListener(ssListItemClick);
		listView.setOnDownRefreshListener(this);
		home_searchBtn.setOnClickListener(this);
		progressBar=(View)this.findViewById(R.id.home_progress);
	}
	
	/**
	 * 数据异步加载
	 * 
	 */
	class GetDataTask extends AsyncTask<String, List<Post>,List<Post> > {

		@Override
		protected List<Post> doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String urlstr;
			if(arg0.length==1){
				urlstr = new StringBuffer().append(Config.POSTALL)
						.append("?").append("access_token=").append(arg0[0])
						.append("&").append("pagesize=").append(10)
						.toString();
			}else{
				
				urlstr= new StringBuffer().append(Config.POSTALL)
						.append("?").append("access_token=").append(arg0[0])
						.append("&").append("pagesize=").append(10)
						.append("&").append("keywords=").append(arg0[1])
						.toString();
			}
			
			
			InputStream resultXml=HttpUtil.httpByGet2InputStreamSSL(urlstr);
			//解析xml喽
			PostHandler p=new PostHandler();
			XMLUtil.parseXml(resultXml,p);
			
			return p.getResult();
		}

		@Override
		protected void onPostExecute(List<Post> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			ssAdapater = new HomeListAdapater(HomeListActivity.this,result, listView);
			listView.setAdapter(ssAdapater);
			progressBar.setVisibility(View.GONE);
			listView.onRefreshComplete();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressBar.setVisibility(View.VISIBLE);
		}
		
	}

	/**
	 * listview 单击到查看那详细页面
	 */
	AdapterView.OnItemClickListener ssListItemClick = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			Post obj = (Post) ssAdapater.getItem(position - 1);
			// 加了下拉刷新后就有了头部position就要减一
			
			if (obj != null) {
				Intent intent = new Intent(HomeListActivity.this,DetailActivity.class);
				Bundle b = new Bundle();
				b.putSerializable("post", obj);
				intent.putExtras(b);
				startActivity(intent);
			}
		}

	};
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
			case R.id.home_searchBtn:
				String keywords=home_searchEt.getText().toString();
				if(!TextUtils.isEmpty(keywords)){
					new GetDataTask().execute(MyApplication.access_token,keywords);
				}
				
				break;
		}
	}
	/**
	 * 下拉刷新监听函数
	 */
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		LogUtil.i("哈哈刷新咯");
		new GetDataTask().execute(MyApplication.access_token);
		
	}
}
