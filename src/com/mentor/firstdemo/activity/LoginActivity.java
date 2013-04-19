package com.mentor.firstdemo.activity;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mentor.firstdemo.BaseActivity;
import com.mentor.firstdemo.MyApplication;
import com.mentor.firstdemo.R;
import com.mentor.firstdemo.R.layout;
import com.mentor.firstdemo.R.menu;
import com.mentor.firstdemo.model.AccessToken;
import com.mentor.firstdemo.model.AccessTokenHandler;
import com.mentor.firstdemo.model.PostHandler;
import com.mentor.firstdemo.util.Config;
import com.mentor.firstdemo.util.HttpUtil;
import com.mentor.firstdemo.util.LogUtil;
import com.mentor.firstdemo.util.UIUtils;
import com.mentor.firstdemo.util.XMLUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseActivity implements OnClickListener{
	
	private EditText login_NameEt;
	private EditText login_PwdEt;
	private Button login_LoginBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_main);
		initView();
	}
	public void initView(){
		login_NameEt=(EditText)this.findViewById(R.id.login_NameEditText);
		login_PwdEt=(EditText)this.findViewById(R.id.login_PasswordEditText);
		login_LoginBtn=(Button)this.findViewById(R.id.login_Button);
		login_LoginBtn.setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
			case R.id.login_Button:
				login();
				break;
		}
	}
	public  void login(){
		String uname = login_NameEt.getText().toString();
		String password = login_PwdEt.getText().toString();
		if (TextUtils.isEmpty(uname)) {
			UIUtils.showToastShort(this, "请输入邮箱");
			return;
		}
		if (TextUtils.isEmpty(password)) {
			UIUtils.showToastShort(this, "请输入密码");
			return;
		}
		new LoginAsyncTask().execute(uname,password);
	}
	class LoginAsyncTask extends AsyncTask<String,Void,AccessToken>{

		private ProgressDialog pd=null;
		public LoginAsyncTask(){
			pd=new ProgressDialog(LoginActivity.this);
			pd.setTitle("正在登陆，请稍候");
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			
			super.onPreExecute();
			pd.show();
		}

		@Override
		protected AccessToken doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlstr = new StringBuffer().append(Config.AUTH2LOGIN)
					.append("?").append("app_key=").append(Config.APPKEY)
					.append("&").append("app_secret=").append(Config.APPSECRET)
					.append("&").append("grant_type=password")
					.append("&").append("username=").append(params[0])
					.append("&").append("password=").append(params[1])
					.append("&").append("p_signature=").append("")
					.toString();

			InputStream resultXml=HttpUtil.httpByGet2InputStreamSSL(urlstr);
			//解析xml喽
			AccessTokenHandler ath=new AccessTokenHandler();
			XMLUtil.parseXml(resultXml,ath);
			return ath.getAccessToken();
		}
		
		@Override
		protected void onPostExecute(AccessToken result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pd.dismiss();
			if(result!=null&&!TextUtils.isEmpty(result.getAccess_token())){
				MyApplication.access_token=result.getAccess_token();
				Intent intent=new Intent(LoginActivity.this,HomeListActivity.class);
				//intent.putExtra("access_token", result.getAccess_token());
				LoginActivity.this.startActivity(intent);
			}else{
				UIUtils.showToastShort(getApplicationContext(), "登录失败，请检查网络或稍后重试");
			}
			
		}
	}

}
