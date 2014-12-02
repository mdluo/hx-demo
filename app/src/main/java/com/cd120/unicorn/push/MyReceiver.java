package com.cd120.unicorn.push;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import org.json.JSONObject;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

import com.cd120.unicorn.chat.ChatActivity;
import com.cd120.unicorn.chat.ChatEntity;
import com.cd120.unicorn.main.MainActivity;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {

	public static final String ALER = "cn.jpush.android.ALERT";
	public static final String SER = "cn.jpush.android.EXTRA";
	private static final String TAG = "MyReceiver";
	    private NotificationManager nm;
	// private Context context;

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	public MyReceiver() {
		System.out.println("MyReceiver");
		// 构造函数，做一些初始化工作，本例中无任何作用
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "onReceive - " + intent.getAction() + ", extras: "
				+ printBundle(bundle));
		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "接收Registration Id : " + regId);
			// send the Registration Id to your server...
		} else if (JPushInterface.ACTION_UNREGISTER.equals(intent.getAction())) {
			String regId = bundle
					.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			Log.d(TAG, "接收UnRegistration Id : " + regId);
			// send the UnRegistration Id to your server...
		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
				.getAction())) {
			// 自定义消息不会展示在通知栏，完全要开发者写代码去处理
			Log.d(TAG,"接收到自定义消息: "+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
				.getAction())) {
			Log.d(TAG, "接收到通知");// 在这里可以做些统计，或者做些其他工作
//			int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
//			Log.d(TAG, "接收到通知的ID: " + notifactionId);
			
			receivingNotification(context,bundle);
			
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
				.getAction())) {
			// 在这里可以自己写代码去定义用户点击后的行为
			Log.d(TAG, "用户点击打开了通知");
		 openNotification(context,bundle);

		} else {
			Log.d(TAG, "Unhandled intent - " + intent.getAction());
		}
		
	}
	private void receivingNotification(Context context, Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d(TAG, "extras : " + extras);
        String content_type = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
        Log.d(TAG, "content_type : " + content_type);
    } 
 
   private void openNotification(Context context, Bundle bundle){
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue = ""; 
        try {
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("myKey");
        } catch (Exception e) {
            Log.w(TAG, "Unexpected: extras is not a valid json", e);
            return;
        }
        if (bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE).equals("新提醒事项")) {
        	Log.e("新提醒事项",bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
            Intent mIntent = new Intent(context, MainActivity.class);
//            int fragment_index=0;
//            Bundle indexbundle = new Bundle();
//            indexbundle.putInt("fragment_index", fragment_index);
//            mIntent.putExtras(indexbundle);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        } else if (bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE).equals("新消息")){
        	ChatEntity mchat = new ChatEntity();
			SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");       
			Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间        
			String    str    =    formatter.format(curDate);       
			mchat.setChatTime(str);
			Log.d("获取提醒消息时间",str);
			mchat.setComeMsg(true);
			mchat.setContent(bundle.getString(ALER));
			Log.i("bundle.getString(SER)", bundle.getString(ALER));

			// // 打开自定义的Activity
			Intent i = new Intent(context, ChatActivity.class);
			 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			Bundle mbundle = new Bundle();
			mbundle.putSerializable("mchat", mchat);
			i.putExtras(mbundle);
			context.startActivity(i);

        }
    }

}
