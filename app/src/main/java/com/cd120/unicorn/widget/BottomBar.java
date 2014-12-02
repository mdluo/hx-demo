package com.cd120.unicorn.widget;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cd120.unicorn.R;

/**
 * ��״̬��������װ����
 * 
 * ���ַ�װ��ʽ�ͷ�װ����iPhone��SegmentBar��̫һ�����ڴ��������Button��
 * �����벼���ļ����ϡ�ͨ��inflater�����ļ������õ�ÿ��Item��
 * 
 * @author MichaelYe
 * @since 2012-9-5
 * */
public class BottomBar extends LinearLayout implements OnClickListener {

	public interface OnItemChangedListener {
		public void onItemChanged(int index);
	}

	private static final int TAG_0 = 0;
	private static final int TAG_1 = 1;
	private static final int TAG_2 = 2;
	private static final int TAG_3 = 3;
	// private static final int TAG_4 = 4;
	private List<View> itemList;

	private int lastButton = -1;

	private Context mContext;

	private OnItemChangedListener onItemChangedListener;

	private TextView tvOne;

	public BottomBar(Context context) {
		super(context);
		mContext = context;
		init();
	}

	public BottomBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init();
	}

	/**
	 * make the red indicate GONE
	 * 
	 * ���� ��ִ�а�ť���Ͻǵĺ�ɫСͼ��
	 * 
	 * */
	public void hideIndicate() {
		tvOne.setVisibility(View.GONE);
	}

	/**
	 * get the buttons from layout
	 * 
	 * �õ������ļ��еİ�ť
	 * 
	 * */
	private void init() {
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.bottom_bar, null);
		layout.setLayoutParams(new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
		tvOne = (TextView) layout.findViewById(R.id.tv_warming);
		Button btnOne = (Button) layout.findViewById(R.id.btn_item_one);
		Button btnTwo = (Button) layout.findViewById(R.id.btn_item_two);
		Button btnThree = (Button) layout.findViewById(R.id.btn_item_three);
		// Button btnFour = (Button) layout.findViewById(R.id.btn_item_four);
		Button btnFour = (Button) layout.findViewById(R.id.btn_item_four);
		btnOne.setOnClickListener(this);
		btnTwo.setOnClickListener(this);
		btnThree.setOnClickListener(this);
		btnFour.setOnClickListener(this);
		btnOne.setTag(TAG_0);
		btnTwo.setTag(TAG_1);
		btnThree.setTag(TAG_2);
		btnFour.setTag(TAG_3);
		itemList = new ArrayList<View>();
		itemList.add(btnOne);
		itemList.add(btnTwo);
		itemList.add(btnThree);
		itemList.add(btnFour);
		this.addView(layout);
	}

	@Override
	public void onClick(View v) {
		int tag = (Integer) v.getTag();
		switch (tag) {
		case TAG_0:
			setNormalState(lastButton);
			setSelectedState(tag);
			break;
		case TAG_1:
			setNormalState(lastButton);
			setSelectedState(tag);
			break;
		case TAG_2:
			setNormalState(lastButton);
			setSelectedState(tag);
			break;
		case TAG_3:
			setNormalState(lastButton);
			setSelectedState(tag);
			break;
		// case TAG_4:
		// setNormalState(lastButton);
		// setSelectedState(tag);
		// break;
		}
	}

	/**
	 * set the normal state of the button by given index
	 * 
	 * �ָ�δѡ��״̬
	 * 
	 * */
	private void setNormalState(int index) {
		if (index != -1) {
			if (index > itemList.size()) {
				throw new RuntimeException(
						"the value of default bar item can not bigger than string array's length");
			}
			itemList.get(index).setSelected(false);
		}
	}

	public void setOnItemChangedListener(
			OnItemChangedListener onItemChangedListener) {
		this.onItemChangedListener = onItemChangedListener;
	}

	/**
	 * set the default bar item of selected
	 * 
	 * ����Ĭ��ѡ�е�Item
	 * 
	 * */
	public void setSelectedState(int index) {
		if (index != -1 && onItemChangedListener != null) {
			if (index > itemList.size()) {
				throw new RuntimeException(
						"the value of default bar item can not bigger than string array's length");
			}
			itemList.get(index).setSelected(true);
			onItemChangedListener.onItemChanged(index);
			lastButton = index;
		}
	}

	/**
	 * make the red indicate VISIBLE
	 * 
	 * ������ִ�а�ť���Ͻǵĺ�ɫСͼ��Ŀɼ�
	 * 
	 * */
	public void showIndicate(int value) {
		tvOne.setText(value + "");
		tvOne.setVisibility(View.VISIBLE);
	}
}
