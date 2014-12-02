package com.cd120.unicorn;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cd120.unicorn.main.MainActivity;
import com.cd120.unicorn.netutility.ObtainDataTask;
import com.cd120.unicorn.netutility.ObtainDataTask.DataPreparedListener;

public class UserActivity extends Activity implements DataPreparedListener {

	/** Called when the activity is first created. */
	private Button back = null;
	private TextView email = null;
	private TextView gender = null;
	private TextView hospitalization_number = null;
	private TextView identify_number = null;
	private TextView phone = null;
	// 用户信息
	private TextView username = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.user);
		username = (TextView) findViewById(R.id.textView1_username);
		gender = (TextView) findViewById(R.id.TextView01_gender);
		hospitalization_number = (TextView) findViewById(R.id.TextView02_hospitalization_number);
		identify_number = (TextView) findViewById(R.id.TextView03_identify_number);
		email = (TextView) findViewById(R.id.TextView04_email);
		phone = (TextView) findViewById(R.id.TextView05_phone);

		if (!UserUtil.getLoginUserData().flag) {
			ObtainDataTask userinfor = new ObtainDataTask(UserActivity.this,
					UserActivity.this,
					"http://unicorn.sinaapp.com/dev/user/me", "GET");
			userinfor.setmWaitMessage("获取信息...");
			userinfor.execute(new BasicNameValuePair("format", "json"));
		}
		back = (Button) findViewById(R.id.button1);
		back.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(UserActivity.this, MainActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
			}
		});

	}

	@Override
	public void onDataPrepared(String result) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		JSONObject obj = null;
		if (result != null) {
			try {
				obj = new JSONObject(result);

				UserUtil.getLoginUserData().setUserid(obj.getString("id"));
				UserUtil.getLoginUserData().setUsername(obj.getString("name"));
				// UserUtil.getLoginUserData().setPictureString(obj.getString("pictue"));
				UserUtil.getLoginUserData().setGender(obj.getString("gender"));
				UserUtil.getLoginUserData().setAge(obj.getString("age"));
				UserUtil.getLoginUserData().setHospitalization_number(
						obj.getString("hospitalization_number"));
				UserUtil.getLoginUserData().setIdentify_number(
						obj.getString("identify_number"));
				UserUtil.getLoginUserData().setEmail(obj.getString("email"));
				UserUtil.getLoginUserData().setPhone(obj.getString("phone"));

				// 显示用户信息
				username.setText(UserUtil.getLoginUserData().getUsername());
				gender.setText(UserUtil.getLoginUserData().getGender());
				hospitalization_number.setText(UserUtil.getLoginUserData()
						.getHospitalization_number());
				identify_number.setText(UserUtil.getLoginUserData()
						.getIdentify_number());
				email.setText(UserUtil.getLoginUserData().getEmail());
				phone.setText(UserUtil.getLoginUserData().getPhone());

				Log.e("userid", UserUtil.getLoginUserData().getUserid());
				Log.e("username", UserUtil.getLoginUserData().getUsername());
				Log.e("password", UserUtil.getLoginUserData().getPassword());

			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} else {
			Toast.makeText(getApplicationContext(), "获取用户信息失败，",
					Toast.LENGTH_SHORT).show();
		}

	}

}
