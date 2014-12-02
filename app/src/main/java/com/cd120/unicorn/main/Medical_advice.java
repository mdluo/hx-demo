package com.cd120.unicorn.main;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cd120.unicorn.R;

public class Medical_advice extends ListFragment {
	private ListView mlist;

	public Medical_advice() {
	}

	private ArrayList<HashMap<String, String>> getData() {
		String titles[] = getResources().getStringArray(R.array.title);
		String content[] = getResources().getStringArray(R.array.content);
		ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < titles.length; i++) {
			HashMap<String, String> tempHashMap = new HashMap<String, String>();
			tempHashMap.put("title", titles[i]);
			tempHashMap.put("content", content[i]);
			arrayList.add(tempHashMap);
		}

		return arrayList;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mlist = getListView();
		setListAdapter(new SimpleAdapter(getActivity(), getData(),
				R.layout.listcell, new String[] { "title", "content" },
				new int[] { R.id.title, R.id.content }));
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
		View layout = myInflater.inflate(R.layout.frag_2, container, false);

		return layout;
	}

}