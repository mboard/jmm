package com.droiddnamk.sharedrive;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.droiddnamk.sharedrive.customClasses.Trip;
import com.droiddnamk.sharedrive.webcommunication.attendReject;

public class CustomAdapter extends BaseAdapter {
	ArrayList<Trip> items;
	ArrayList<Integer> clicked;
	Context mContext;
	LayoutInflater inflater = null;
	static float x = 0, y = 0;
	long startTime = System.currentTimeMillis();

	CustomAdapter(Context context, ArrayList<Trip> i) {
		items = i;
		clicked = new ArrayList<Integer>();
		mContext = context;
		inflater = ((Activity) context).getLayoutInflater();

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
	public static ImageView buttonAttend;
	TextView txtStatus2;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ImageView imgStrelka;
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.custom_row, null);
		final LinearLayout lyly = (LinearLayout) vi
				.findViewById(R.id.skrienLayout);
		TextView txtUser = (TextView) vi.findViewById(R.id.custom_txtUsername);
		TextView fromCountry = (TextView) vi
				.findViewById(R.id.custom_row_fromCountry);
		TextView toCountry = (TextView) vi
				.findViewById(R.id.custom_row_toCountry);
		TextView fromCity = (TextView) vi
				.findViewById(R.id.custom_row_fromCity);
		TextView toCity = (TextView) vi.findViewById(R.id.custom_row_toCity);
		TextView availible_passangers = (TextView) vi
				.findViewById(R.id.custom_txtAvailable);
		TextView total_passangers = (TextView) vi
				.findViewById(R.id.custom_txtTotal);
		TextView no_likes = (TextView) vi.findViewById(R.id.txtLike);
		TextView no_dislikes = (TextView) vi.findViewById(R.id.txtDislike);
		txtStatus2 = (TextView) vi.findViewById(R.id.custom_Status2);
		imgStrelka = (ImageView) vi.findViewById(R.id.imageView3);
		if (clicked.contains(position)) {
			lyly.setVisibility(View.VISIBLE);
		} else {
			lyly.setVisibility(View.GONE);
		}
		x = imgStrelka.getWidth() / 2;
		y = imgStrelka.getHeight() / 2;

		imgStrelka.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (lyly.getVisibility() == View.GONE) {

					RotateAnimation anim = new RotateAnimation(0f, 180f, x, y);
					anim.setInterpolator(new LinearInterpolator());
					anim.setFillAfter(true);
					anim.setFillEnabled(true);
					anim.setDuration(700);
					imgStrelka.startAnimation(anim);

					lyly.setVisibility(View.VISIBLE);
					clicked.add(position);
				} else {
					RotateAnimation anim = new RotateAnimation(180f, 360f, x, y);
					anim.setInterpolator(new LinearInterpolator());
					anim.setFillAfter(true);
					anim.setFillEnabled(true);
					anim.setDuration(700);

					imgStrelka.startAnimation(anim);
					lyly.setVisibility(View.GONE);
					try {
						clicked.remove(position);
					} catch (Exception e) {
					}

				}
			}
		});
		vehicleType = (ImageView) vi.findViewById(R.id.custom_img_vehicleType);
		buttonAttend = (ImageView) vi.findViewById(R.id.custom_btnAttend);
		txtUser.setText(items.get(position).getUser());
		fromCountry.setText(items.get(position).getFrom_country());
		toCountry.setText(items.get(position).getTo_country());
		fromCity.setText(items.get(position).getFrom_city());
		toCity.setText(items.get(position).getTo_city());
		no_dislikes.setText(items.get(position).getNo_dislikes());
		no_likes.setText(items.get(position).getNo_likes());
		setVehicle(position);
		setStatus(position);
		total_passangers.setText(items.get(position).getNo_passangers() + "");
		availible_passangers.setText(items.get(position)
				.getAvailible_passangers() + "");
		TextView date = (TextView) vi.findViewById(R.id.custom_date);
		date.setText("From Date: " + items.get(position).getFrom_time());
		((TextView) vi.findViewById(R.id.custom_date2)).setText("To Date: "
				+ items.get(position).getTo_time());
		((TextView) vi.findViewById(R.id.custom_address))
				.setText("From address: "
						+ items.get(position).getFrom_address());
		((TextView) vi.findViewById(R.id.custom_address2))
				.setText("To address: " + items.get(position).getTo_address());
		TextView a = (TextView) vi.findViewById(R.id.custom_paymentType);
		a.setText("Payment Type: " + items.get(position).getPayment_type());
		buttonAttend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if ((startTime + 1000) > System.currentTimeMillis())
					return;
				else
					startTime = System.currentTimeMillis();
				if (items.get(position).getStatus().equals("Active")) {
					String tmp = items.get(position).getStatus2();
					Log.e("TMPSTATUS STARO = ", tmp);
					if (tmp.equals("Attend"))
						tmp = "Cancel";
					else if (tmp.equals("Cancel") || tmp.equals("null"))
						tmp = "Attend";
					Log.e("TMPSTATUS FINAL = ", tmp);

					(new attendReject(mContext, items.get(position).getId(),
							tmp, items.get(position).getNo_passangers()))
							.execute();
				} else
					Toast.makeText(mContext, "The trip is not Active anymore!",
							Toast.LENGTH_SHORT).show();

			}

		});
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

	private void setStatus(int position) {
		String type = items.get(position).getStatus2();
		buttonAttend.setVisibility(View.VISIBLE);
		txtStatus2.setVisibility(View.GONE);
		if (type.equals("Attend")) {
			buttonAttend.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.btn_cancel));
		} else if (type.equals("Cancel"))
			buttonAttend.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.btn_attend));
		else if (type.equals("null"))
			buttonAttend.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.btn_attend));
		else if (type.equals("Reject")) {
			buttonAttend.setVisibility(View.GONE);
			txtStatus2.setText("Rejected");
			txtStatus2.setTextColor(Color.RED);
			txtStatus2.setVisibility(View.VISIBLE);
		} else if (type.equals("Confirm")) {
			buttonAttend.setVisibility(View.GONE);
			txtStatus2.setText("Confirmed");
			txtStatus2.setTextColor(Color.GREEN);
			txtStatus2.setVisibility(View.VISIBLE);
		}

	}
}
