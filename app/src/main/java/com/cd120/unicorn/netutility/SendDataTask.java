package com.cd120.unicorn.netutility;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.cd120.unicorn.SysApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

/**
 * @author dujicheng
 * 聊天信息发送
 *
 */
public class SendDataTask extends AsyncTask<String, Void, Boolean> {

	private String errorMsg;
	private Context mContext;
	private String url;

	public SendDataTask(Context mContext, String url) {
		this.mContext = mContext;
		this.url = url;
	}

	@Override
	protected Boolean doInBackground(String... params) {

		Log.d("Debug", "in doInBackground...");
		Log.d("url", url);
		
		boolean retValue = false;

		HttpPost httpRequest = new HttpPost(url);
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			postParams.add(new BasicNameValuePair("id", params[0]));
			postParams.add(new BasicNameValuePair("text", params[1]));
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(postParams,
					HTTP.UTF_8));

			DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
			// basic验证
			defaultHttpClient.getCredentialsProvider().setCredentials(
					new AuthScope(null, -1),
					new UsernamePasswordCredentials(SysApplication.username, SysApplication.password));
			
			defaultHttpClient.getParams().setParameter(
					CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
			HttpResponse httpResponse = defaultHttpClient.execute(httpRequest);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();

				if (entity != null) {
					String strRes = EntityUtils.toString(entity, "utf-8");
					Log.d("debug", "result1: " + strRes);

					if (strRes!=null) {
						retValue = true;
					} else {
						errorMsg = "sendfalse1!";
					}
				}
			} else {
				errorMsg = "sendfalse2!";
				Log.d("debug", "result2: "
						+ httpResponse.getStatusLine().toString());
			}
		} catch (ConnectTimeoutException cte) {
			errorMsg = "senderr1!";
			cte.printStackTrace();
		} catch (Exception e) {
			errorMsg = "senderr2";
			e.printStackTrace();
		}

		return retValue;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (result) {
			Toast.makeText(mContext, "发送成功", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
		}
	}
}
