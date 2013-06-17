package com.droiddnamk.sharedrive.webcommunication;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.droiddnamk.sharedrive.AdvancedInfoActivity;
import com.droiddnamk.sharedrive.JSONParser;
import com.droiddnamk.sharedrive.MainActivity;

public class getPersonLocation extends AsyncTask<String, String, String> {

	public String url = "http://ristokalinikov.mk/sharedrive/getPersonLocation.php";
	JSONParser jParser = new JSONParser();
	JSONObject json;
	JSONArray products = null;
	ProgressDialog dialog;
	Context mContext;

	public getPersonLocation(Context context) {
		mContext = context;
		dialog = new ProgressDialog(context);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		/*dialog.setMessage("Collecting data...");
		dialog.setCancelable(false);
		dialog.show();*/

	}

	@Override
	protected String doInBackground(String... p) {
		try {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", ""
					+ MainActivity.logged_username));
			
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
		//dialog.dismiss();

		try {
			int success = 0;
			success = json.getInt("success");
			
			if (success == 1) {
				// gi zimme kuti objekti
				products = json.getJSONArray("details");
				Log.e("uspesno= ", success+"");
				// sekoj objekt go plnime vo items - ArrayList<Trip>
				for (int i = 0; i < products.length(); i++) {
					JSONObject c = products.getJSONObject(i);					
					MainActivity.cur_city = c.getString("city");
					MainActivity.cur_city_id = c.getString("city_id");					
					MainActivity.cur_country = c.getString("country");
					MainActivity.cur_country_id = c.getString("country_id");

				}
			} else {
				Log.e("uspesno= ", success+"");
				MainActivity.cur_city = "";
				MainActivity.cur_city_id = "0";				
				MainActivity.cur_country = "";
				MainActivity.cur_country_id = "0";
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
