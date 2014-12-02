package com.cd120.unicorn;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.cd120.unicorn.main.MainActivity;
import com.cd120.unicorn.netutility.ObtainDataTask;
import com.cd120.unicorn.netutility.ObtainDataTask.DataPreparedListener;

public class LoginActivity extends Activity implements DataPreparedListener {
	// public static String account = null;
	private Button back;
	private Button login;
	private EditText password;
	private EditText username;

	// 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
	private void init() {
		JPushInterface.init(getApplicationContext());
		Log.e("jpush", "初始化");
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// SysApplication.getInstance().addActivity(this);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.login);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		password.setInputType(0x81);

		SharedPreferences user = getSharedPreferences("user_info", 0);
		String usernameStr = user.getString("name", null);
		String passwordStr = user.getString("pass", null);
		
		username.setText(usernameStr);
		password.setText(passwordStr);

		final SysApplication application = (SysApplication) this.getApplication();

		login = (Button) findViewById(R.id.login);

		login.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				// init();
				UserUtil.getLoginUserData().setUsername(
						username.getText().toString());
				UserUtil.getLoginUserData().setPassword(
						password.getText().toString());
				application.setUsername(username.getText().toString());
				application.setPassword(password.getText().toString());
				ObtainDataTask logintask = new ObtainDataTask(
						LoginActivity.this, LoginActivity.this,
						"http://unicorn.sinaapp.com/dev/user/uid", "GET");
				logintask.setmWaitMessage("正在登陆...");
				logintask.execute(new BasicNameValuePair("format", "json"));

				// 写入用户名，密码
				SharedPreferences userInfo = getSharedPreferences("user_info",
						0);
				userInfo.edit()
						.putString("name", UserUtil.getLoginUserData().username)
						.commit();
				userInfo.edit()
						.putString("pass", UserUtil.getLoginUserData().password)
						.commit();

			}
		});
		back = (Button) findViewById(R.id.cancel);
		back.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				LoginActivity.this.finish();
			}
		});

	}

	@Override
	public void onDataPrepared(String result) {
		// TODO Auto-generated method stub
		JSONObject jsonobject;
		if (result != null) {
			Intent newintent = new Intent();
			newintent.setClass(LoginActivity.this, MainActivity.class);
			newintent.putExtra("username", username.getText().toString());
			startActivity(newintent);

		} else {
			Toast.makeText(getApplicationContext(), "登陆失败，", Toast.LENGTH_SHORT)
					.show();
		}

		try {
			jsonobject = new JSONObject(result);
			UserUtil.getLoginUserData().setUserid(jsonobject.getString("uid"));
			Log.e("userid", UserUtil.getLoginUserData().getUserid());
			Log.e("username", UserUtil.getLoginUserData().getUsername());
			Log.e("password", UserUtil.getLoginUserData().getPassword());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// 当activity是单例的时候,再次启动该activity就不会再调用 oncreate->onstart这些方法了
	@Override
	protected void onNewIntent(Intent intent) {
		int flag = getIntent().getIntExtra("flag", 0);
		if (flag == SysUtil.EXIT_APPLICATION) {
			finish();
		}
		super.onNewIntent(intent);
	}

	@Override
	public void onResume() {
		super.onResume();

		// showChannelIds();
	}

	// 这里用来接受退出程序的指令
	@Override
	protected void onStart() {
		int flag = getIntent().getIntExtra("flag", 0);
		if (flag == SysUtil.EXIT_APPLICATION) {
			finish();
		}
		super.onStart();

	}

	@Override
	public void onStop() {
		super.onStop();
	}
}