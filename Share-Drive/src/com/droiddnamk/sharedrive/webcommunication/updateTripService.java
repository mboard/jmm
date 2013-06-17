package com.droiddnamk.sharedrive.webcommunication;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import com.droiddnamk.sharedrive.AdvancedInfoActivity;
import com.droiddnamk.sharedrive.JSONParser;
import com.droiddnamk.sharedrive.MainActivity;

public class updateTripService extends Service {
	public static final String PREF_UPDATE = "UpdatePrefs";
	public static final String PREF_LAST_ID = "LastPrefs";
	public static final String PREF_PREVIOUS_ID = "PreviousIDPrefs";
	SharedPreferences prefs;
	int previous, now;
	Nitka loop;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		prefs = getSharedPreferences(PREF_UPDATE, Context.MODE_PRIVATE);
		previous = prefs.getInt(PREF_PREVIOUS_ID, -1);
		now = prefs.getInt(PREF_LAST_ID, -1);
		loop = new Nitka();
		Log.e("TAG", "previous is: "+previous);
		loop.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		loop.setRunning(false);
	}

	// async so vrake id

	class CheckNew extends AsyncTask<String, String, String> {
		JSONParser jParser = new JSONParser();
		JSONObject json;
		JSONArray products = null;
		Context mContext;

		public static final String url = "http://ristokalinikov.mk/sharedrive/checkUpdate.php";

		public CheckNew(Context baseContext) {
			mContext = baseContext;
		}

		@Override
		protected String doInBackground(String... p) {
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				json = jParser.makeHttpRequest(url, "POST", params);
			} catch (Exception err) {
				Log.d("updateTRIP", err.getMessage());

			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				int success = json.getInt("success");
				if (success == 1) {
					now = json.getInt("max");

					
					Log.e("Checking in Servive"," now = "+now + " previous = "+previous);
					if (now > previous) {
						Editor editor = prefs.edit();
						editor.putInt(PREF_LAST_ID, now);
						editor.putInt(PREF_PREVIOUS_ID, previous);
						previous = now;
						editor.commit();
						Log.e("TAG","Now Calling BROADCAST RECEIVER");
						sendBroadcast(new Intent(mContext,updateTripsBroadcast.class));
					}

				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class Nitka extends Thread {
		boolean isRunning = true;
		int minutes = 1;

		@Override
		public void run() {
			super.run();

			while (isRunning) {
				if(MainActivity.isActivityRunning == true)
				new CheckNew(getBaseContext()).execute();
				
				try {
					//Thread.sleep(minutes * 60 *1000);
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void setRunning(boolean b) {
			isRunning = b;
		}
	}
}
