package com.cd120.unicorn.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.achartengine.ChartFactory;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cd120.unicorn.R;
import com.cd120.unicorn.UserActivity;
import com.cd120.unicorn.UserUtil;
import com.cd120.unicorn.fragment.Fragment_0.FragmentCallback;
import com.cd120.unicorn.main.PhonefeedbackActivity;
import com.cd120.unicorn.main.Web;
import com.cd120.unicorn.netutility.ObtainData_FragmentTask;
import com.cd120.unicorn.push.PushSetActivity;
import com.cd120.unicorn.push.SettingActivity;
import com.cd120.unicorn.push.TestActivity;
import com.cd120.unicorn.util.AverageTemperatureChart;

public class Fragment_3 extends ListFragment {
	public class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {

				holder = new ViewHolder();

				convertView = mInflater.inflate(R.layout.set_item, null);

				holder.title = (TextView) convertView
						.findViewById(R.id.more_item_text);

				convertView.setTag(holder);

			} else {

				holder = (ViewHolder) convertView.getTag();
			}

			holder.title.setText((String) mData.get(position).get("title"));

			return convertView;
		}

	}

	public final class ViewHolder {

		public TextView title;
	}

	private List<Map<String, Object>> mData;

	private ListView mlistview;

	public Fragment_3() {
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "用户信息");
		map.put("img", R.drawable.set);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "推送设置");
		map.put("img", R.drawable.set);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "免打扰设置");
		map.put("img", R.drawable.set);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "意见反馈");
		map.put("img", R.drawable.set);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "用户随访");
		map.put("img", R.drawable.set);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "随访记录");
		map.put("img", R.drawable.set);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "关于");
		map.put("img", R.drawable.set);
		list.add(map);

		return list;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mlistview = getListView();
		mData = getData();
		MyAdapter adapter = new MyAdapter(getActivity());
		setListAdapter(adapter);

		mlistview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				switch (position) {
				case 0:
					// 用户信息
					Intent intent0 = new Intent(getActivity(),
							UserActivity.class);
					getActivity().startActivity(intent0);

					break;
				case 1:
					// 推送设置
					Intent intent1 = new Intent(getActivity(),
							PushSetActivity.class);
					getActivity().startActivity(intent1);
					break;
				case 2:
					// 免打扰设置
					Intent intent2 = new Intent(getActivity(),
							SettingActivity.class);
					getActivity().startActivity(intent2);
					break;
				case 3:
					// 意见反馈

					Intent intent3 = new Intent(getActivity(),
							PhonefeedbackActivity.class);
					getActivity().startActivity(intent3);
					break;
				case 4:
					// 随访功能
					Intent intent4 = new Intent(getActivity(), Web.class);
					getActivity().startActivity(intent4);
					// getActivity().finish();
					break;

				case 5:
					// 随访历史记录图
					try {

//						历史数据获取
						ObtainData_FragmentTask mtask = new ObtainData_FragmentTask(
								getActivity(), new FragmentCallback() {

									@Override
									public void onTaskDone() {
										// TODO Auto-generated method stub
										onDataPrepared();
									}

									private void onDataPrepared() {
										// TODO Auto-generated method stub
										String result = UserUtil.getLoginUserData().getResult();
										Log.i("onTaskDone.UserUtil", result);
										try {
											JSONArray jarry=new JSONArray(result);
											Map<String, Object> map = new HashMap<String, Object>();
											
											for (int i = 0; i < jarry.length(); i++) {
												JSONObject sub_item = jarry.getJSONObject(i);
												String useridString=sub_item.getString("user_id");
												String created_at=sub_item.getString("created_at");
												//由于数据接收存在null情况，因此需先判断值为null情况，这里直接赋初值
												int goal1=0,goal2=0,goal3=0,goal4 = 0,goal5=0,goal6=0,goal7=0;
//												int goal[]=(int) 0;
												
//												if(sub_item.getString("item1")==null){
//									                 goal1=0;
//												}else{
//													 goal1=Integer.parseInt(sub_item.getString("item1"));
//												}
//												if(sub_item.getString("item2")==null){
//													 goal2=0;
//												}else{
//													goal2=Integer.parseInt(sub_item.getString("item2"));
//												}
//												if(sub_item.getString("item3")==null){
//													goal3=0;
//												}else{
//													
//													 goal3=Integer.parseInt(sub_item.getString("item3"));
//												}
//												if(sub_item.getString("item4")==null){
//													goal4=0;
//													
//												}else{
//													 goal4=Integer.parseInt(sub_item.getString("item4"));
//												}
//												if(sub_item.getString("item5")==null){
//													 goal5=0;
//													
//												}else{
//													 goal5=Integer.parseInt(sub_item.getString("item5"));
//												}
//												if(sub_item.getString("item6")==null){
//													goal6=0;
//													 
//												}else{
//													goal6=Integer.parseInt(sub_item.getString("item6"));
//												}
//												if(sub_item.getString("item7")==null){
//													 goal7=0;
//													 
//												}else{
//													goal7=Integer.parseInt(sub_item.getString("item7"));
//												}
//												goal=goal1+goal2+goal3+goal4+goal5+goal6+goal7;
									Log.d("量表数据1", useridString);
									Log.d("量表数据2", created_at);
//									Log.d("量表数据3", goal+"");
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										//生成图表
										Intent achartIntent =new AverageTemperatureChart().execute(getActivity());
										startActivity(achartIntent);
//										View view_LineChart = ChartFactory.getLineChartView(getActivity(), dataset, renderer);
									}

								}, "http://unicorn.sinaapp.com/dev/scale_results/ipss", "GET");
						mtask.setmWaitMessage("历史数据读取...");
						mtask.execute(new BasicNameValuePair("format", "json"));
						

					} catch (Exception e) {

						Log.d("oncreate", e.getMessage());

					}
					break;
				case 6:
					// 关于

					Intent intent6 = new Intent(getActivity(),
							TestActivity.class);
					getActivity().startActivity(intent6);

					break;
				}
			}
		});

		super.onActivityCreated(savedInstanceState);

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
		View layout = myInflater.inflate(R.layout.frag_3, container, false);

		return layout;
	}

}
