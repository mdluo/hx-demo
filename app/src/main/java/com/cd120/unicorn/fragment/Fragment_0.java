package com.cd120.unicorn.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cd120.unicorn.R;
import com.cd120.unicorn.UserUtil;
import com.cd120.unicorn.netutility.ObtainData_FragmentTask;

/**
 * Caused by: java.lang.InstantiationException: can't instantiate class
 * com.michael.fragment.FragmentExecute; no empty constructor
 * 
 * @see http
 *      ://stackoverflow.com/questions/7016632/unable-to-instantiate-fragment
 * */
public class Fragment_0 extends ListFragment {

	public interface FragmentCallback {
		public void onTaskDone();
	}

	private Activity activity; // 存储上下文对象
	private Context context;
	private TextView description_text;// 描述
	private ListView mlistview;
	private TextView sickeness_tile;// sickeness_title

	private TextView tvbutton;

	// private DataPreparedListener mDataPreparedListener;

	public Fragment_0() {
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		Log.i("tag", "onActivityCreated");
		ObtainData_FragmentTask mtask = new ObtainData_FragmentTask(
				getActivity(), new FragmentCallback() {

					@Override
					public void onTaskDone() {
						// TODO Auto-generated method stub
						onDataPrepared();
					}

				}, "http://unicorn.sinaapp.com/dev/treatments", "GET");
		mtask.setmWaitMessage("正在刷新列表...");
		mtask.execute(new BasicNameValuePair("format", "json"));

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.activity = getActivity();
		this.context = getActivity();

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			// Currently in a layout without a container, so no
			// reason to create our view.
			return null;
		}
		LayoutInflater myInflater = (LayoutInflater) getActivity()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = myInflater.inflate(R.layout.frag_0, container, false);
		// sickeness_tile=

		return layout;
	}

	public void onDataPrepared() {

		String result = UserUtil.getLoginUserData().getResult();
		Log.i("onTaskDone.UserUtil", result);
		// JSONObject jsonObject;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {

			Map<String, Object> map = null;
			JSONArray jsonArray = new JSONArray(result);
			// for (int i = 0; i < jsonArray.length(); i++) {
			// for (int i = 0; i < 2; i++) {
			JSONObject item = jsonArray.getJSONObject(0);

			int id = item.getInt("id");
			String name = item.getString("name");
			String description = item.getString("description");

			sickeness_tile = (TextView) getActivity().findViewById(
					R.id.sickness_title);
			description_text = (TextView) getActivity().findViewById(
					R.id.description);

			sickeness_tile.setText(name);
			sickeness_tile.setTextColor(Color.BLACK);
			sickeness_tile.setTextSize(25.0f);
			sickeness_tile.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);

			description_text.setText("手术描述 --" + description);
			description_text.setTextSize(15);
			description_text.setTextColor(Color.BLACK);

			JSONArray sub_jsonArrary = item.getJSONArray("reminders");
			Log.e("sub_jsonArrary", sub_jsonArrary.toString());
			for (int j = 0; j < sub_jsonArrary.length(); j++) {
				JSONObject sub_item = sub_jsonArrary.getJSONObject(j);
				int sub_id = sub_item.getInt("id");
				String sub_title = sub_item.getString("title");
				String sub_time = null;
				String sub_location = null;
				// if(sub_item.getString("time").equalsIgnoreCase("null")){
				// sub_time="事先预约";
				// }else{
				sub_time = sub_item.getString("time");
				// }
				// if(sub_item.getString("location").equalsIgnoreCase("null")){
				// sub_location ="华西医院泌尿科";
				// }else{
				sub_location = sub_item.getString("location");
				// }

				String sub_note = sub_item.getString("note");
				String sub_confirmed = sub_item.getString("confirmed");

				map = new HashMap<String, Object>(); // 存放到MAP里面
				map.put("id", sub_id + "");
				map.put("sub_tile", sub_title);
				map.put("sub_time", sub_time);
				map.put("sub_location", sub_location);
				map.put("sub_note", sub_note);
				map.put("sub_confirmed", sub_confirmed);
				list.add(map);
			}
			mlistview = getListView();
			ListviewAdapter myadapter = new ListviewAdapter(getActivity(),
					list, R.layout.remind_item, new String[] { "id",
							"sub_tile", "sub_time", "sub_location", "sub_note",
							"sub_confirmed" }, new int[] { R.id.title,
							R.id.time, R.id.location, R.id.cbox });

			setListAdapter(myadapter);
			// }

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
}
