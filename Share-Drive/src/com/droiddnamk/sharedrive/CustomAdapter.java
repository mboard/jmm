package com.droiddnamk.sharedrive;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.droiddnamk.sharedrive.customClasses.Trip;

public class CustomAdapter extends BaseAdapter {
	ArrayList<Trip> items;
	Context mContext;
	private LayoutInflater inflater = null;

	CustomAdapter(Activity context, ArrayList<Trip> i) {
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

	ImageView vehicleType;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;

		if (convertView == null)
			vi = inflater.inflate(R.layout.custom_row, null);
		TextView fromCountry = (TextView) vi
				.findViewById(R.id.custom_row_fromCountry);
		TextView toCountry = (TextView) vi
				.findViewById(R.id.custom_row_toCountry);
		TextView fromAddress = (TextView) vi
				.findViewById(R.id.custom_row_fromAddress);
		TextView toAddress = (TextView) vi
				.findViewById(R.id.custom_row_toAddress);
		vehicleType = (ImageView) vi.findViewById(R.id.custom_img_vehicleType);

		fromCountry.setText(items.get(position).getFrom_country());
		toCountry.setText(items.get(position).getTo_country());
		fromAddress.setText(items.get(position).getFrom());
		toAddress.setText(items.get(position).getTo());
		setVehicle(position);

		return vi;
	}

	private void setVehicle(int position) {
		String type = items.get(position).getVehicle_type();

		if (type.equals("car")) {
			vehicleType.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.img_car));
		} else if (type.equals("motor"))
			vehicleType.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.img_motor));
		else if (type.equals("truck"))
			vehicleType.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.img_truck));
		else if (type.equals("other"))
			vehicleType.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.img_other));

	}

}
