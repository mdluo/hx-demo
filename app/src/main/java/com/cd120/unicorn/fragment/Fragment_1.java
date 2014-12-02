package com.cd120.unicorn.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cd120.unicorn.R;
import com.cd120.unicorn.chat.ChatActivity;

public class Fragment_1 extends ListFragment {
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

				convertView = mInflater.inflate(R.layout.chat_history_item,
						null);
				holder.img = (ImageView) convertView
						.findViewById(R.id.itemImage);
				holder.title = (TextView) convertView
						.findViewById(R.id.itemTitle);
				holder.info = (TextView) convertView
						.findViewById(R.id.itemText);
				// holder.viewBtn =
				// (Button)convertView.findViewById(R.id.view_btn);
				convertView.setTag(holder);

			} else {

				holder = (ViewHolder) convertView.getTag();
			}

			holder.img.setBackgroundResource((Integer) mData.get(position).get(
					"img"));
			holder.title.setText((String) mData.get(position).get("title"));
			holder.info.setText((String) mData.get(position).get("info"));

			// holder.viewBtn.setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// showInfo();
			// }
			// });

			return convertView;
		}

	}

	public final class ViewHolder {
		public ImageView img;
		public TextView info;
		// public Button viewBtn;
		public TextView title;
	}

	private List<Map<String, Object>> mData;

	private ListView mlistview;

	public Fragment_1() {
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "泌尿科");
		map.put("info", "请于5月18日来复查");
		map.put("img", R.drawable.mypic);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "放射科");
		map.put("info", "请来取X光片");
		map.put("img", R.drawable.mypic);
		list.add(map);

		return list;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mlistview = getListView();
		mData = getData();
		MyAdapter adapter = new MyAdapter(getActivity());
		setListAdapter(adapter);

		mlistview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				// Toast.makeText(getActivity(),
				// "点击历史记录",
				// Toast.LENGTH_LONG).show();

				// // 打开自定义的Activity
				Intent i = new Intent(getActivity(), ChatActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); 

				getActivity().startActivity(i);

			}
		});

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
		View layout = myInflater.inflate(R.layout.frag_1, container, false);

		return layout;
	}

}
