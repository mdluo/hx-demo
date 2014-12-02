package com.cd120.unicorn.chat;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cd120.unicorn.LoginActivity;
import com.cd120.unicorn.R;
import com.cd120.unicorn.main.MainActivity;
import com.cd120.unicorn.netutility.ObtainDataTask;
import com.cd120.unicorn.netutility.SendDataTask;
import com.cd120.unicorn.push.MyReceiver;

public class ChatActivity extends Activity {
	private class ChatAdapter extends BaseAdapter {
		private class ChatHolder {
			private TextView contentTextView;
			private TextView timeTextView;
			private ImageView userImageView;
		}

		private List<ChatEntity> chatList = null;
		private int COME_MSG = 0;
		private Context context = null;
		private LayoutInflater inflater = null;

		private int TO_MSG = 1;

		public ChatAdapter(Context context, List<ChatEntity> chatList) {
			this.context = context;
			this.chatList = chatList;
			inflater = LayoutInflater.from(this.context);
		}

		@Override
		public int getCount() {
			return chatList.size();
		}

		@Override
		public Object getItem(int position) {
			return chatList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getItemViewType(int position) {
			// 区别两种view的类型，标注两个不同的变量来分别表示各自的类型
			ChatEntity entity = chatList.get(position);
			if (entity.isComeMsg()) {
				return COME_MSG;
			} else {
				return TO_MSG;
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ChatHolder chatHolder = null;
			if (convertView == null) {
				chatHolder = new ChatHolder();
				if (chatList.get(position).isComeMsg()) {
					convertView = inflater.inflate(R.layout.chat_from_item,
							null);
				} else {
					convertView = inflater.inflate(R.layout.chat_to_item, null);
				}
				chatHolder.timeTextView = (TextView) convertView
						.findViewById(R.id.tv_time);
				chatHolder.contentTextView = (TextView) convertView
						.findViewById(R.id.tv_content);
				chatHolder.userImageView = (ImageView) convertView
						.findViewById(R.id.iv_user_image);
				convertView.setTag(chatHolder);
			} else {
				chatHolder = (ChatHolder) convertView.getTag();
			}

			chatHolder.timeTextView.setText(chatList.get(position)
					.getChatTime());
			chatHolder.contentTextView.setText(chatList.get(position)
					.getContent());
			chatHolder.userImageView.setImageResource(chatList.get(position)
					.getUserImage());

			return convertView;
		}

		@Override
		public int getViewTypeCount() {
			// 这个方法默认返回1，如果希望listview的item都是一样的就返回1，我们这里有两种风格，返回2
			return 2;
		}

	}

//	private Button back = null;
	private ChatAdapter chatAdapter = null;
	private List<ChatEntity> chatList = null;
	private ListView chatListView = null;

	private EditText contentEditText = null;
	private MyReceiver receiver;
	private Button sendButton = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chat1);

		contentEditText = (EditText) this.findViewById(R.id.et_content);
		sendButton = (Button) this.findViewById(R.id.btn_send);
		chatListView = (ListView) this.findViewById(R.id.listview);
//		back = (Button) findViewById(R.id.button1);
		chatList = new ArrayList<ChatEntity>();

		ChatEntity chatEntity = null;
		for (int i = 0; i < 2; i++) {
			chatEntity = new ChatEntity();
			if (i % 2 == 0) {
				chatEntity.setComeMsg(false);
				chatEntity.setContent("你好，请问我接到检查通知，可以请假吗？");
				chatEntity.setChatTime("2013-07-3 15:12:32");
			} else {
				chatEntity.setComeMsg(true);
				chatEntity.setContent("您好，华西客服为您服务!");
				chatEntity.setChatTime("2013-07-3 15:13:32");
			}

			chatList.add(chatEntity);
		}

		chatAdapter = new ChatAdapter(this, chatList);
		chatListView.setAdapter(chatAdapter);

		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!contentEditText.getText().toString().equals("")) {
					// 发送消息
					send();
				} else {
					Toast.makeText(ChatActivity.this, "Content is empty",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

//		back.setOnClickListener(new Button.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Intent i = new Intent(ChatActivity.this, MainActivity.class);
//				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//				startActivity(i);
//			}
//		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent myIntent = new Intent();
			myIntent = new Intent(ChatActivity.this, MainActivity.class);
			startActivity(myIntent);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		// 这里定义接收一个新的Intent，接收到新的Intent后，由于Activity会pause，因此可在resume里面更新界面
		super.onNewIntent(intent);
		Log.i("intent", "处理新的intent");
		setIntent(intent);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		chatAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		ChatEntity mchat = (ChatEntity) getIntent().getSerializableExtra(
				"mchat");

		if (mchat != null) {
//			mchat.setContent("你好，华西为您服务");
			Log.i("回传数据mchat", mchat.getChatTime().toString());
			chatList.add(mchat);
			chatAdapter.notifyDataSetChanged();
			chatListView.setSelection(chatList.size() - 1);
		}
	}

	private void send() {
		ChatEntity chatEntity = new ChatEntity();
		SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日    HH:mm:ss     ");       
		Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间        
		String    str    =    formatter.format(curDate);       

		chatEntity.setChatTime(str);
		chatEntity.setContent(contentEditText.getText().toString());
		chatEntity.setComeMsg(false);
		
		SendDataTask sendDataHelper = new SendDataTask(
				ChatActivity.this, "http://unicorn.sinaapp.com/dev/direct_messages");
		sendDataHelper.execute("3", contentEditText.getText().toString());
		
		chatList.add(chatEntity);
		chatAdapter.notifyDataSetChanged();
		chatListView.setSelection(chatList.size() - 1);
		contentEditText.setText("");
	}
}