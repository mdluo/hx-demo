package com.cd120.unicorn.push;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.cd120.unicorn.R;

public class TestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("用户自定义打开的Activity");
		setContentView(R.layout.about);
	}

}
