package com.cd120.unicorn.main;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.cd120.unicorn.R;

public class Web extends Activity {
	
	public final String Get_URL = "http://unicorn.sinaapp.com/dev/scales/mine?format=json";
	String resultStr = "";
	WebView show = null;
	public TextView tv1 = null;
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				String s = (String) msg.obj;
				tv1.setText(s);
				break;
			case 2:
				show.getSettings().setJavaScriptEnabled(true);
				show.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
				show.setWebViewClient(new MyWebViewClient());
				show.setHttpAuthUsernamePassword((String) msg.obj, "Protected",
						"test1", "123");
				Log.e("msg.obj",(String) msg.obj);
				show.loadUrl((String) msg.obj);

				break;
			}
			super.handleMessage(msg);
		}

	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);
		// button1 = (Button)findViewById(R.id.button1);
		show = (WebView) findViewById(R.id.webView1);
		tv1 = (TextView) findViewById(R.id.textView1);

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				HttpGet httpget = new HttpGet(Get_URL);
				DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
				// basic验证
				defaultHttpClient.getCredentialsProvider().setCredentials(
						new AuthScope(null, -1),
						new UsernamePasswordCredentials("test1", "123"));
				try {
					HttpResponse response;
					response = defaultHttpClient.execute(httpget);
					// System.out.println("返回数据:"+resultStr+"-----------");
					if (response.getStatusLine().getStatusCode() == 200) {
						// 获取返回的数据
						resultStr = EntityUtils.toString(response.getEntity(),
								"UTF-8");
						Log.i("HttpGet", "HttpGet方式请求成功，返回数据如下：");
						Message msg = handler.obtainMessage();
						msg.what = 1;
						msg.obj = resultStr;
						System.out.println("返回数据:" + resultStr + "-----------");
						handler.sendMessage(msg);
						
						String tempS = resultStr.replace("[", "").replace("]",
								"");
						System.out.println("改变后的json：" + tempS + "------");
						
						JSONObject demoJson = new JSONObject(tempS);
						String url = demoJson.getString("scale_uri");

						System.out.println("URL:" + url + "-----------");
						Message msg2 = handler.obtainMessage();
						msg2.what = 2;
						msg2.obj = url;
						handler.sendMessage(msg2);

					}

				}

				catch (UnsupportedEncodingException e) {

					e.printStackTrace();
				} catch (ClientProtocolException e) {

					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();


	}
	private class MyWebViewClient extends WebViewClient {

		@Override
		public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			Log.e("Hub", "Error: " + description);
			Toast.makeText(Web.this, "Oh no! " + description, Toast.LENGTH_LONG)
					.show();
		}

		@Override
		public void onReceivedHttpAuthRequest(WebView view,
				HttpAuthHandler handler, String host, String realm) {

			handler.proceed("test1", "123");
			Log.i("Hub", "Host =" + host + " with realm =" + realm);
		}
	}

}
