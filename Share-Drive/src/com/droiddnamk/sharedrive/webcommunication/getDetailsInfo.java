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

public class getDetailsInfo extends AsyncTask<String, String, String> {

	public String url = "http://ristokalinikov.mk/sharedrive/getDetailsInfo.php";
	JSONParser jParser = new JSONParser();
	JSONObject json;
	JSONArray products = null;
	ProgressDialog dialog;
	Context mContext;

	public getDetailsInfo(Context context) {
		mContext = context;
		dialog = new ProgressDialog(context);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.setMessage("Collecting data...");
		dialog.setCancelable(false);
		dialog.show();

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
		dialog.dismiss();

		try {
			int success = json.getInt("success");

			if (success == 1) {
				// gi zimme kuti objekti
				products = json.getJSONArray("details");

				// sekoj objekt go plnime vo items - ArrayList<Trip>
				for (int i = 0; i < products.length(); i++) {
					JSONObject c = products.getJSONObject(i);

					String city = c.getString("city");
					String user_photo = c.getString("user_photo");
					String phone_number = c.getString("phone_number");
					String country = c.getString("country");
					String country_id = c.getString("country_id");
					String city_id = c.getString("city_id");
					AdvancedInfoActivity.city_temp = city;
					// String state = c.getString("state");

					Log.e("TAG", "CITY: " + city);
					if (!city.equals("null"))
						// AdvancedInfoActivity.list_city.setText(city);
						if (!phone_number.equals("null"))
							AdvancedInfoActivity.phoneNumber
									.setText(phone_number);
					for (int i1 = 0; i1 < AdvancedInfoActivity.listCountries
							.getCount(); i1++) {
						if (country.equals(AdvancedInfoActivity.listCountries
								.getItemAtPosition(i1).toString())) {
							AdvancedInfoActivity.listCountries.setSelection(i1);
						}
					}

					// if(!state.equals("null"))
					// AdvancedInfoActivity.state.setText(state);



					if (user_photo.length() > 0) {
						new ImageDownloader(AdvancedInfoActivity.userPhoto)
								.execute("http://ristokalinikov.mk/sharedrive/user_photos/"
										+ user_photo);
					}
					
					Log.e("koj grad->",city);

				}
			} else {
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
