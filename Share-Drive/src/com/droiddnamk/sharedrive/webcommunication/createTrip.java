package com.droiddnamk.sharedrive.webcommunication;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.droiddnamk.sharedrive.AdvancedInfoActivity;
import com.droiddnamk.sharedrive.CreateTrip;
import com.droiddnamk.sharedrive.CreateTripExtra;
import com.droiddnamk.sharedrive.JSONParser;
import com.droiddnamk.sharedrive.MainActivity;
import com.droiddnamk.sharedrive.StaticData.Singelton;
import com.droiddnamk.sharedrive.customClasses.Trip;

public class createTrip extends AsyncTask<String, String, String> {

	public String url = "http://ristokalinikov.mk/sharedrive/createTrip.php";
	JSONParser jParser = new JSONParser();
	JSONObject json;
	JSONArray products = null;
	ProgressDialog dialog;
	Context mContext;

	public createTrip(Context context) {
		mContext = context;
		dialog = new ProgressDialog(context);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.setMessage("Creating trip...");
		dialog.setCancelable(false);
		dialog.show();

	}

	@Override
	protected String doInBackground(String... p) {
		try {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id",p[0]));
			params.add(new BasicNameValuePair("from_city", p[1]));
			params.add(new BasicNameValuePair("from_city_id", p[2]));
			params.add(new BasicNameValuePair("from_country", p[3]));
			params.add(new BasicNameValuePair("from_country_id", p[4]));
			params.add(new BasicNameValuePair("from_address", p[5]));
			params.add(new BasicNameValuePair("from_time", p[6]));
			params.add(new BasicNameValuePair("to_city", p[7]));
			params.add(new BasicNameValuePair("to_city_id", p[8]));
			params.add(new BasicNameValuePair("to_country", p[9]));
			params.add(new BasicNameValuePair("to_country_id", p[10]));
			params.add(new BasicNameValuePair("to_address", p[11]));
			params.add(new BasicNameValuePair("to_time", p[12]));
			params.add(new BasicNameValuePair("no_passangers", p[13]));
			params.add(new BasicNameValuePair("vehicle_type", p[14]));
			params.add(new BasicNameValuePair("payment_type", p[15]));
			
			Log.e("user_id", p[0]);
			Log.e("from_city", p[1]);
			Log.e("from_city_id", p[2]);
			Log.e("from_country", p[3]);
			Log.e("from_country_id", p[4]);
			Log.e("from_address", p[5]);
			Log.e("from_time", p[6]);
			Log.e("to_city", p[7]);
			Log.e("to_city_id", p[8]);
			Log.e("to_country", p[9]);
			Log.e("to_country_id", p[10]);
			Log.e("to_address", p[11]);
			Log.e("to_time", p[12]);
			Log.e("no_passangers", p[13]);
			Log.e("vehicle_type", p[14]);
			Log.e("payment_type", p[15]);
			/*
			 	params[0] = MainActivity.logged_id;
				params[1] = CreateTrip.from_city;
				params[2] = CreateTrip.from_city_id;
				params[3] = CreateTrip.from_country;
				params[4] = CreateTrip.from_country_id;
				params[5] = CreateTrip.from_address;
				params[6] = tmp_from_time;
				params[7] = CreateTrip.to_city;
				params[8] = CreateTrip.to_city_id;
				params[9] = CreateTrip.to_country;
				params[10] = CreateTrip.to_country_id;
				params[11] = CreateTrip.to_address;
				params[12] = tmp_to_time;
				params[13] = CreateTrip.no_passangers;
				params[14] = CreateTrip.vehicle_type;
				params[15] = CreateTrip.payment_type;	
			 */
			json = jParser.makeHttpRequest(url, "POST", params);
			Log.d("Site: : ", json.toString());

		} catch (Exception err) {
			Log.d("getPersonDetails", err.getMessage());
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		dialog.dismiss();

		try {
			int success = 0;
			success = json.getInt("success");
			
			if (success == 1) {
				Toast.makeText(mContext, "Your trip is created successfully", Toast.LENGTH_SHORT).show();
				// gi zimme kuti objekti
			 ((Activity) mContext).finish();
			} else {
				Toast.makeText(mContext, "Error, missing fields?!", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
