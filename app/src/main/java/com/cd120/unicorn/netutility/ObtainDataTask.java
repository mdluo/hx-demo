package com.cd120.unicorn.netutility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.cd120.unicorn.SysApplication;
import com.cd120.unicorn.fragment.Fragment_0.FragmentCallback;

/*
 *负责数据收发*/
public class ObtainDataTask extends AsyncTask<BasicNameValuePair, Void, String> {

	public interface DataPreparedListener {
		public void onDataPrepared(String result);
	}

	private static final Credentials String = null;

	private String mAlertMessage = "失败";

	private int mConnectionTimeout = 10000;
	private Context mContext;
	private DataPreparedListener mDataPreparedListener;
	private FragmentCallback mFragmentCallback;
	private String mType = "GET";
	private String mUrl = null;
	private ProgressDialog mWaitDialog;

	private String mWaitMessage = "正在初始化数据";

	public ObtainDataTask(Context context,
			DataPreparedListener mDataPreparedListener, String url, String type) {
		this.mContext = context;
		this.mUrl = url;
		this.mDataPreparedListener = mDataPreparedListener;
		this.mType = type;
	}

	private AlertDialog createInfoDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		AlertDialog alert = null;

		builder.setMessage(mAlertMessage).setCancelable(false)
				.setNegativeButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		alert = builder.create();

		return alert;
	}

	@Override
	protected String doInBackground(BasicNameValuePair... params) {

		Log.d("Debug", "in doInBackground...");

		if (mType.equalsIgnoreCase("GET")) {

			mUrl += "?";
			for (BasicNameValuePair nameValuePair : params) {
				mUrl += (nameValuePair.getName() + "="
						+ nameValuePair.getValue() + "&");
			}

			mUrl = mUrl.substring(0, mUrl.length() - 1);// �������ַ�
			Log.d("debug", "get url: " + mUrl);
			HttpGet httpRequest = new HttpGet(mUrl);
			DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
			// basic验证
			defaultHttpClient.getCredentialsProvider().setCredentials(
					new AuthScope(null, -1),
					new UsernamePasswordCredentials(SysApplication.username, SysApplication.password));
			defaultHttpClient.getParams()
					.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
							mConnectionTimeout);
			try {
				HttpResponse httpResponse = defaultHttpClient
						.execute(httpRequest);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();

					if (entity != null) {
						String resultStr = EntityUtils
								.toString(entity, "utf-8");
						Log.d("ObtainDataTask", "result: " + resultStr);
						if (resultStr == null || resultStr.equals("")) {
							return "";
						} else {
							return resultStr;
						}
					}
				} else {
					Log.d("debug", "status code: "
							+ httpResponse.getStatusLine().toString());
					return null;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (mType.equalsIgnoreCase("POST")) {
			HttpPost httpRequest = new HttpPost(mUrl);
			List<NameValuePair> postParams = new ArrayList<NameValuePair>();
			for (BasicNameValuePair nameValuePair : params) {
				postParams.add(nameValuePair);
			}

			try {
				httpRequest.setEntity(new UrlEncodedFormEntity(postParams,
						HTTP.UTF_8));
				DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
				// basic验证
				defaultHttpClient.getCredentialsProvider().setCredentials(
						new AuthScope(null, -1),
						new UsernamePasswordCredentials(SysApplication.username, SysApplication.password));

				defaultHttpClient.getParams().setParameter(
						CoreConnectionPNames.CONNECTION_TIMEOUT,
						mConnectionTimeout);
				HttpResponse httpResponse = defaultHttpClient
						.execute(httpRequest);

				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = httpResponse.getEntity();

					if (entity != null) {
						String resultStr = EntityUtils
								.toString(entity, "utf-8");
						Log.d("debug", "result: " + resultStr);
						if (resultStr == null || resultStr.equals("")) {
							return "";
						} else {
							return resultStr;
						}
					}
				} else {
					Log.d("debug", "status code: "
							+ httpResponse.getStatusLine().toString());
					return null;
				}
			} catch (ConnectTimeoutException cte) {
				cte.printStackTrace();
			} catch (ClientProtocolException cpe) {
				cpe.printStackTrace();
			} catch (UnsupportedEncodingException uue) {
				uue.printStackTrace();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

		} else {
			throw new IllegalArgumentException("Unknown type: " + mType);
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		mWaitDialog.dismiss();
		if (result == null || result.equals("")) {
			createInfoDialog().show();
		} else {
			//UserUtil.getLoginUserData().setResult(result);
			mDataPreparedListener.onDataPrepared(result);
		}
	}

	@Override
	protected void onPreExecute() {

		mWaitDialog = new ProgressDialog(mContext);
		mWaitDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mWaitDialog.setMessage(mWaitMessage);
		mWaitDialog.setIndeterminate(true);
		mWaitDialog.setTitle("");
		mWaitDialog.setCancelable(false);
		mWaitDialog.show();
	}

	public void setmAlertMessage(String mAlertMessage) {
		this.mAlertMessage = mAlertMessage;
	}

	public void setmConnectionTimeout(int mConnectionTimeout) {
		this.mConnectionTimeout = mConnectionTimeout;
	}

	public void setmWaitMessage(String mWaitMessage) {
		this.mWaitMessage = mWaitMessage;
	}
}
