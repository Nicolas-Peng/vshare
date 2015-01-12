package com.LZP.vshare;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;

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
import com.LZP.vshare.Sign_up.ThreadToAccessServer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import io.rong.imkit.RongIM;
import io.rong.imkit.parse.JSONParser;
import io.rong.imlib.RongIMClient;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	private Button forgot;
	private Button login;
	private EditText user, password;
	private TextView signup;
	private String nickname = "";
	private String Login_xml = "1";
	private String token = "0";
	Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		forgot = (Button) findViewById(R.id.forgot);
		login = (Button) findViewById(R.id.login);
		user = (EditText) findViewById(R.id.user);
		password = (EditText) findViewById(R.id.password);
		signup = (TextView) findViewById(R.id.signup);

		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Log.d("222222222222", "2222222222222");
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
				}
			}
		};

		int mode = MODE_PRIVATE;
		final SharedPreferences sp_user = getSharedPreferences("SaveSetting",
				mode);
		user.setText(sp_user.getString("user", ""));
		token = sp_user.getString("token", "0");
		nickname = sp_user.getString("nickname", "");
		// 初始化
		RongIM.init(this, "82hegw5uh73kx", R.drawable.ic_launcher);

		// 获取token
		// String token =
		// "+n8mXCGvk0zBzGZbWAfVi79puf6MRFO+ZFfmXwcxGf8rB/vxBVKBvrcFf8Y9NaY4LIDtKUeNAv/pw9U4eBIAOw==";
		// "+n8mXCGvk0zBzGZbWAfVi79puf6MRFO+ZFfmXwcxGf8rB/vxBVKBvrcFf8Y9NaY4LIDtKUeNAv/pw9U4eBIAOw==";
		// userID=1的Token
		// "40ngQdUMmuYZOAuy5Nc7zOCyZZqrUsm3E58d3vQF8Z9KimxJ/kt902N8YCBdYNkAdHSvOI9I8wE=";

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						// MyDatabaseHelper dao = new MyDatabaseHelper(
						// MainActivity.this);
						Boolean flag = false;
						String _user = user.getText().toString();
						String _password = password.getText().toString();
						String _error = "密码或账号错误";
						// Cursor c = dao.query();
						/*
						 * while (c.moveToNext()) { Log.d("Flag====True",
						 * "Flag====True");
						 * 
						 * if (c.getString(1).equalsIgnoreCase(_user) &&
						 * c.getString(2).equalsIgnoreCase( _password)) { flag =
						 * true; break; } }
						 */
						new Thread(new ThreadToAccessServer()).start();
						while (true) {
							if (Login_xml.equals("1")) {

							} else if (Login_xml.equals("0")) {
								Toast t = Toast.makeText(MainActivity.this,
										_error, Toast.LENGTH_SHORT);
								t.show();
								break;
							} else {
								flag = true;
								break;
							}
						}

						if (flag) {
							new Thread(new ThreadToAccessServer()).start();
							while (true) {
								if (token.equals("0")) {
								} else {
									System.out.println(token);
									// 连接融云服务器
									RongIM.connect(token,
											new RongIMClient.ConnectCallback() {

												@Override
												public void onSuccess(String s) {
													Log.d("Connect:",
															"Login successfully");
												}

												@Override
												public void onError(
														ErrorCode errorCode) {
													Log.d("Connect",
															"Login failed");
												}
											});
									// 设置用户信息提供者。
									RongIM.setGetUserInfoProvider(
											new RongIM.GetUserInfoProvider() {
												@Override
												public RongIMClient.UserInfo getUserInfo(
														String userId) {
													if (userId.equals("3169104444@qq.com")) {
														RongIMClient.UserInfo user0 = new RongIMClient.UserInfo(
																"3169104444@qq.com",
																"3169104444@qq.com",
																"http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png");
														return user0;
													} else if (userId
															.equals(user
																	.getText()
																	.toString())) {
														RongIMClient.UserInfo user0 = new RongIMClient.UserInfo(
																user.getText()
																		.toString(),
																user.getText()
																		.toString(),
																"http://p.qq181.com/cms/1210/2012100413195471481.jpg");
														return user0;
													}
													return null;
												}
											}, false);
									// 设置好友信息提供者。
									RongIM.setGetFriendsProvider(new RongIM.GetFriendsProvider() {
										@Override
										public List<RongIMClient.UserInfo> getFriends() {
											// 返回 App 的好友列表给 IMKit
											// 界面组件，供会话列表页中选择好友时使用。
											List<RongIMClient.UserInfo> list = new ArrayList<RongIMClient.UserInfo>();

											RongIMClient.UserInfo user1 = new RongIMClient.UserInfo(
													"3169104444@qq.com",
													"3169104444@qq.com",
													"http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png");

											list.add(user1);

											RongIMClient.UserInfo user2 = new RongIMClient.UserInfo(
													user.getText().toString(),
													user.getText().toString(),
													"http://p.qq181.com/cms/1210/2012100413195471481.jpg");

											list.add(user2);

											return list;
										}
									});
									// 启动会话列表
									RongIM.getInstance().startConversationList(
											MainActivity.this);
									break;
								}
							}

						} else {
							Toast t = Toast.makeText(MainActivity.this, _error,
									Toast.LENGTH_SHORT);
							t.show();
						}
					}
				});

				/*
				 * 客服 String customerServiceId = "KEFU1417442752450";
				 * 
				 * RongIM.getInstance().startCustomerServiceChat(MainActivity.this
				 * , customerServiceId, "在线客服");
				 */

			}
		});

		signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, Sign_up.class);
				startActivityForResult(intent, 1);
			}
		});

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		int mode = MODE_PRIVATE;
		String name = "SaveSetting";
		SharedPreferences sp = getSharedPreferences(name, mode);
		SharedPreferences.Editor ed = sp.edit();
		ed.putString("token", token);
		ed.putString("user", user.getText().toString());
		// ed.putString(autoText.getText().toString(),
		// password.getText().toString());
		ed.commit();
		super.onDestroy();
	}

	// 选择的结果
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				// get the uri of the selected file
				String email = data.getExtras().getString("_email");
				user.setText(email);
				nickname = data.getExtras().getString("_nickname");
				int mode = MODE_PRIVATE;
				String name = "SaveSetting";
				SharedPreferences sp = getSharedPreferences(name, mode);
				SharedPreferences.Editor ed = sp.edit();
				ed.putString("nickname", nickname);
				// ed.putString(autoText.getText().toString(),
				// password.getText().toString());
				ed.commit();
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	class ThreadToAccessServer implements Runnable {
		public ThreadToAccessServer() {
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

			HttpPost httpPost = new HttpPost(
					"http://gaarasapp.sinaapp.com/login");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userID", user.getText()
					.toString()));
			params.add(new BasicNameValuePair("nickname", nickname));
			params.add(new BasicNameValuePair("password", password.getText()
					.toString()));
			params.add(new BasicNameValuePair("url",
					"http://rongcloud-web.qiniudn.com/docs_demo_rongcloud_logo.png"));
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			// 执行post方法得到结果，将其转化为String
			DefaultHttpClient httpClient = new DefaultHttpClient();
			try {
				HttpResponse response = httpClient.execute(httpPost);
				HttpEntity result = response.getEntity();
				Login_xml = EntityUtils.toString(result);
				// Login_xml="["+Login_xml+"]";

				Log.d("Json", "json");
				if (token.equals("0") && !Login_xml.equals("0")) {
					Gson gson = new Gson();
					Map<String, String> map = gson.fromJson(Login_xml,
							Map.class);
					Log.d("Json2", "json2");
					System.out.println(Login_xml);

					token = map.get("token");

					System.out.println(map.get("token"));
					mHandler.obtainMessage(1).sendToTarget();
				}
			} catch (IOException e) {
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
