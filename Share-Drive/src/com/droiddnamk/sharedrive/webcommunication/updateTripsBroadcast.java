package com.droiddnamk.sharedrive.webcommunication;

import com.droiddnamk.sharedrive.StaticData.Singelton;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class updateTripsBroadcast extends BroadcastReceiver{
	SharedPreferences prefs;
	int now,previous;
	@Override
	public void onReceive(Context context, Intent intent) {
		prefs = context.getSharedPreferences(updateTripService.PREF_UPDATE, Context.MODE_PRIVATE);
		now = prefs.getInt(updateTripService.PREF_LAST_ID, -1);
		previous = prefs.getInt(updateTripService.PREF_PREVIOUS_ID, 0);
		
		new addNew(context, now,previous).execute();

	}

}
