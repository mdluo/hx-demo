package com.cd120.unicorn.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.cd120.unicorn.R;
import com.cd120.unicorn.SysUtil;
import com.cd120.unicorn.fragment.Fragment_0;
import com.cd120.unicorn.fragment.Fragment_1;
import com.cd120.unicorn.fragment.Fragment_2;
import com.cd120.unicorn.fragment.Fragment_3;
import com.cd120.unicorn.widget.BottomBar;
import com.cd120.unicorn.widget.BottomBar.OnItemChangedListener;

/**
 * This demo shows how to use FragmentActivity to build the frame of a common
 * application. To replace the deprecated class such as TabActivity,
 * ActivityGroup,and so on.
 * 
 * ���demoչʾ�����ʹ��FragmentActivity������Ӧ�ó���Ŀ��
 * ����ʹ����������ԭ����TabActivity��ActivityGroup�ȵ�
 * 
 * @author MichaelYe&&DU Jicheng
 * @since 2012-9-6
 * @see http://developer.android.com/reference/android/app/Fragment.html
 * @see http://developer.android.com/training/basics/fragments/index.html
 * @see http://developer.android.com/guide/components/fragments.html
 * */
public class MainActivity extends FragmentActivity {
	public BottomBar bottomBar;
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
//		final Bundle bundle = getIntent().getExtras();  
//		final int getindex=bundle.getInt("fragment_index", 0);
//		
//      if (bundle!=null) {
//    	  Log.i("跳转新提醒", getindex+"");
//        showDetails(getindex);
// } else {
//      
// }
		super.onResume();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
//		super.onNewIntent(intent);
//		Log.i("intent", "处理新的intent");
//		setIntent(intent);
	}

	@Override
	protected void onResumeFragments() {
		// TODO Auto-generated method stub
		
		super.onResumeFragments();
	}

	public static final int MENU_EXITAPPLICATION = Menu.FIRST;

	private void exitPage() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this).setTitle("关于").setMessage("要退出吗？")
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						SysUtil mSysUtil = new SysUtil(MainActivity.this);
						mSysUtil.exit();

					}
				}).show();

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		bottomBar = (BottomBar) findViewById(R.id.ll_bottom_bar);
		bottomBar.setOnItemChangedListener(new OnItemChangedListener() {

			@Override
			public void onItemChanged(int index) {

	                showDetails(index);
            
			}
		});
		bottomBar.setSelectedState(0);
	
		// bottomBar.hideIndicate();//�������ԭ�����ƺ�ɫСͼ��Ŀɼ���
		// bottomBar.showIndicate(12);

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add(0, MENU_EXITAPPLICATION, 0, "退出程序");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Intent i = new Intent(Intent.ACTION_MAIN);
			i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			i.addCategory(Intent.CATEGORY_HOME);
			startActivity(i);

		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		if (item.getItemId() == MENU_EXITAPPLICATION) {
			// 其他Activity退出程序这样调用就ok了
			exitPage();

		}
		return super.onOptionsItemSelected(item);

	}

	private void showDetails(int index) {
		Fragment details = getSupportFragmentManager()
				.findFragmentById(R.id.details);
		switch (index) {
		case 0:
			details = new Fragment_0();
			break;
		case 1:
			details = new Fragment_1();
			break;
		case 2:
			details = new Fragment_2();
			break;
		// case 3:
		// details = new FragmentSearch();
		// break;
		case 3:
			details = new Fragment_3();
			break;
		}
		// Execute a transaction, replacing any existing
		// fragment with this one inside the frame.
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.details, details);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		// ft.addToBackStack(null);//���д�����Է���֮ǰ�Ĳ���������������£������߶���ʾ������£�
		ft.commit();
	}

}
