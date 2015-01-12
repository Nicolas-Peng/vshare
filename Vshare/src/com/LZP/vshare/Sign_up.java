package com.LZP.vshare;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;





import com.LZP.vshare.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Sign_up extends Activity {
	private Button _sign;
	private EditText _email, _password, _confirm,_nickname;
	private String name ;
	private String nickname;
	private String password;
	private String Sign_up_xml;
	
	Handler mhandler;
	public class ThreadToAccessServer implements Runnable {  //将用户名id 密码等上传到服务器，并且检查是否id已被注册
		public ThreadToAccessServer() {
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			HttpPost httpPost=new HttpPost("http://gaarasapp.sinaapp.com/sign");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userID",name));
			params.add(new BasicNameValuePair("nickname",nickname));
			params.add(new BasicNameValuePair("password",password));
			params.add(new BasicNameValuePair("url","http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png"));
			try{
				httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
			}catch(UnsupportedEncodingException e){
				e.printStackTrace();
			}
			//执行post方法得到结果，将其转化为String
			DefaultHttpClient httpClient = new DefaultHttpClient();
			try{
				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity result=response.getEntity();
				Sign_up_xml=EntityUtils.toString(result);
				mhandler.obtainMessage(123).sendToTarget();
				/*
				Message msg = mhandler.obtainMessage();
				Bundle data = new Bundle();
				
				data.putString("signal", Sign_up_xml);
				msg.setData(data);
				mhandler.sendMessage(msg);
				*/
			}catch(IOException e){
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		_sign = (Button) findViewById(R.id.sign_up);
		_email = (EditText) findViewById(R.id.email);
		_nickname=(EditText)findViewById(R.id.nickname);
		_password = (EditText) findViewById(R.id.password);
		_confirm = (EditText) findViewById(R.id.confirm);
		
		mhandler=new Handler(){
			
			@Override
			public void handleMessage(Message msg){
				Log.d("222222222222","2222222222222");
				super.handleMessage(msg);
				switch (msg.what) {
				case 123:
					if(Sign_up_xml.equalsIgnoreCase("1")){
						MyDatabaseHelper db = new MyDatabaseHelper(Sign_up.this);
						db.insert2(name, password);
						/*
						Intent intent=new Intent(Sign_up.this,MainActivity.class);
						startActivity(intent);
						*/
						Intent intent = new Intent(Sign_up.this, MainActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("_email",
								name);
						bundle.putString("_nickname", 
								nickname);
						bundle.putString("_password", 
								password);
						intent.putExtras(bundle);
						setResult(android.app.Activity.RESULT_OK, intent);
						finish();
						Toast t=Toast.makeText(Sign_up.this, "注册成功！", 100);
						t.show();
					}
					else if(Sign_up_xml.equalsIgnoreCase("0")){//注册不成功
						Toast f=Toast.makeText(Sign_up.this, "该邮箱已存在！", 100);
						f.show();
					}
					else{
						Toast a=Toast.makeText(Sign_up.this, "未知错误！", 100);
						a.show();
					}
					break;
				}
				/*
				Bundle b=msg.getData();
				String judge;
				judge=(String) b.get("signal");
				*/
		
			}
		};
		
		_sign.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				name = _email.getText().toString();
				nickname=_nickname.getText().toString();
				password = _password.getText().toString();
				String c = _confirm.getText().toString();
				if (!name.equalsIgnoreCase("") && !password.equalsIgnoreCase("") 
						&& !c.equalsIgnoreCase("")) {
					if (!password.equalsIgnoreCase(c)) {
						Toast t = Toast.makeText(Sign_up.this, "两次输入的密码不一致!",
								Toast.LENGTH_SHORT);
						t.show();
					} else {
						new Thread(new  ThreadToAccessServer()).start();
					}
				} else {
					Toast t = Toast.makeText(Sign_up.this, "账号密码不能为空!",
							Toast.LENGTH_SHORT);
					t.show();
				}
			}
		});

	}

}
