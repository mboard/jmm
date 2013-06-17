package com.droiddnamk.sharedrive;

import java.text.DateFormat;
import java.util.Date;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class CreateTrip extends TabActivity {
	public static TabHost tabHost;
	public static Date from_date_date,to_date_date;
	public static String from_city,from_city_id,from_country,from_country_id,from_address,from_date,from_time,
	to_city,to_city_id,to_country,to_country_id,to_address,to_date,to_time,no_passangers,vehicle_type,payment_type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 tabHost = getTabHost();
		// Tab for Photos
		TabSpec CreateTripFrom = tabHost.newTabSpec("From");
		CreateTripFrom.setIndicator("From", getResources()
				.getDrawable(R.drawable.icon_tab_from));
		Intent photosIntent = new Intent(this, CreateTripFrom.class);
		CreateTripFrom.setContent(photosIntent);

		// Tab for Songs
		TabSpec CreateTripTo = tabHost.newTabSpec("To");
		CreateTripTo.setIndicator("To",
				getResources().getDrawable(R.drawable.icon_tab_to));
		Intent songsIntent = new Intent(this, CreateTripTo.class);
		CreateTripTo.setContent(songsIntent);

		// Tab for Videos
		TabSpec CreateTripExtra = tabHost.newTabSpec("Extra");
		CreateTripExtra.setIndicator("Extra", getResources()
				.getDrawable(R.drawable.icon_tab_extra));
		Intent videosIntent = new Intent(this, CreateTripExtra.class);
		CreateTripExtra.setContent(videosIntent);

		// Adding all TabSpec to TabHost
		tabHost.addTab(CreateTripFrom); //
		tabHost.addTab(CreateTripTo); //
		tabHost.addTab(CreateTripExtra);
		tabHost.getTabWidget().getChildTabViewAt(0).setEnabled(false);
		tabHost.getTabWidget().getChildTabViewAt(1).setEnabled(false);
		tabHost.getTabWidget().getChildTabViewAt(2).setEnabled(false);
	}

}
