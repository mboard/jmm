package com.droiddnamk.sharedrive;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
	ArrayList<String> items;
	Context mContext;
	private LayoutInflater inflater = null;

	CustomAdapter(Activity context, ArrayList<String> i) {
		items = i;
		mContext = context.getBaseContext();
		inflater = context.getLayoutInflater();

	}

	@Override
	public int getCount() {

		return items.size();
	}

	@Override
	public Object getItem(int arg0) {

		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;

		if (convertView == null)
			vi = inflater.inflate(R.layout.custom_row, null);
		
		ImageView img = (ImageView)vi.findViewById(R.id.custom_img);
		TextView txt = (TextView)vi.findViewById(R.id.custom_text);
		
		txt.setText(items.get(position));
		
		return vi;
	}

}
