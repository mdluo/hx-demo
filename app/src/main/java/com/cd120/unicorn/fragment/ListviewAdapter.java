package com.cd120.unicorn.fragment;

import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cd120.unicorn.R;

public class ListviewAdapter extends BaseAdapter {

	public final class ViewHolder {
		public ImageView img;
		public TextView location;
		public TextView time;
		public TextView title;

	}

	private Context context;
	private String flag[];// ��ı��
	private int ItemIDs[];// ������
	private int layoutID;// ��Ӧ�Ĳ���
	private List<Map<String, Object>> list;

	private LayoutInflater mInflater;

	public ListviewAdapter(Context context, List<Map<String, Object>> list,
			int layoutID, String flag[], int ItemIDs[]) {
		this.mInflater = LayoutInflater.from(context);
		this.list = list;
		this.layoutID = layoutID;
		this.flag = flag;
		this.ItemIDs = ItemIDs;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(layoutID, null);
			// for (int i = 0; i < flag.length; i++) {// ��ע1
			// if (convertView.findViewById(ItemIDs[i]) instanceof ImageView)
			// {// listview�еľ���item
			// holder.img = (ImageView)convertView.findViewById(ItemIDs[i]);
			//
			// holder.img.setBackgroundResource((Integer)list.get(position).get(flag[i]));
			// } else if (convertView.findViewById(ItemIDs[i]) instanceof
			// TextView) {
			// TextView tv = (TextView) convertView.findViewById(ItemIDs[i]);
			//
			// tv.setText((CharSequence) list.get(position).get(flag[i]));
			// tv.setTextColor(Color.BLACK);
			// // tv.setTextSize(18);
			//
			// } else {
			// // ...��ע2
			// }
			// }
			holder.title = (TextView) convertView.findViewById(ItemIDs[0]);
			holder.time = (TextView) convertView.findViewById(ItemIDs[1]);
			holder.location = (TextView) convertView.findViewById(ItemIDs[2]);

			// holder.title.setText(list.get(position).get(flag[0]) + ":"
			// + list.get(position).get(flag[1]));
			holder.title.setText("" + list.get(position).get(flag[1]));

			if (list.get(position).get(flag[2]).equals("null")) {
				holder.time.setVisibility(8);
			} else {
				holder.time.setText((CharSequence) list.get(position).get(
						flag[2]));
			}
			if (list.get(position).get(flag[3]).equals("null")) {
				holder.location.setVisibility(8);
			} else {
				holder.location.setText((CharSequence) list.get(position).get(
						flag[3]));
			}

			final CheckBox ck = (CheckBox) convertView.findViewById(R.id.cbox);
			ck.setChecked(false);
			ck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					// TODO Auto-generated method stub
					Toast.makeText(context, "CheckBox点击事件", Toast.LENGTH_SHORT)
							.show();
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					if (isChecked) {// 勾上这个checkBox 对应的动作
						String title = " 确认提醒事项";
						// String msg = "确认提醒日程有效吗？";
						builder.setTitle(title)
						// .setMessage(msg)
								.setPositiveButton("确认",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												// stub
											}
										});
						builder.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int whichButton) {
										// 这里添加点击确定后的逻辑
										// isChecked=false;
										ck.setChecked(false);
									}
								});
						builder.create().show();

					} else { // 勾上这个checkBox 对应的动作

						String title = " 确认对话框";
						String msg = "确认取消吗？";
						builder.setTitle(title)
								.setMessage(msg)
								.setPositiveButton("确认",
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// TODO Auto-generated method
												// stub
												ck.setChecked(false);
											}
										});
						builder.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int whichButton) {
										// 这里添加点击确定后的逻辑
										// isChecked=false;
										ck.setChecked(true);
									}
								});
						builder.create().show();

					}

				}
			});
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (ItemIDs != null) {

		}

		return convertView;
	}

	// 打开提示框

}
