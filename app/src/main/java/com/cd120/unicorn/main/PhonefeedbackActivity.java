package com.cd120.unicorn.main;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cd120.unicorn.R;

public class PhonefeedbackActivity extends Activity {
	private TextView address;
	private String address_Str;
	private String device_ID;
	private TextView deviceID;
	/** Called when the activity is first created. */
	// private ImageButton back;
	private ImageButton makephone;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.feedback);
		// back = (ImageButton) findViewById(R.id.back);
		makephone = (ImageButton) findViewById(R.id.imageButton1);

		// Get the preference
		// back.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// // TODO Auto-generated method stub
		// finish();
		//
		// }
		//
		// });
		makephone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// TODO Auto-generated method stub
				// 生成呼叫意图
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ "10086"));
				startActivity(intent);
			}

		});
	}
}
