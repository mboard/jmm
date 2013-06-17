package com.droiddnamk.sharedrive.webcommunication;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.droiddnamk.sharedrive.JSONParser;
import com.droiddnamk.sharedrive.StaticData.Singelton;
import com.droiddnamk.sharedrive.customClasses.Trip;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class addNew extends AsyncTask<String, String, Void> {

	public static final String url = "http://ristokalinikov.mk/sharedrive/addNew.php";
	JSONParser jParser = new JSONParser();
	JSONObject json;
	JSONArray products = null;
	int previous,now;

	public addNew(int n,int p) {
		now = n;
		previous = p;
	}

	@Override
	protected Void doInBackground(String... p) {
		try {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			Log.e("TAG","fromID is: "+previous+" toID IS: "+now);
			params.add(new BasicNameValuePair("fromId", ""+previous));
			params.add(new BasicNameValuePair("toId", ""+now));
			json = jParser.makeHttpRequest(url, "POST", params);
			Log.d("vo addNew -> doInBackground ", json.toString());

		} catch (Exception err) {
			Log.d("Greska addNew doInBackground() ", err.getMessage());
		}

		return null;

	}

	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		try {
			int success = json.getInt("success");

			if (success == 1) {
				// gi zimme kuti objekti
				products = json.getJSONArray("trips");
				// sekoj objekt go plnime vo items - ArrayList<Trip>
				for (int i = 0; i < products.length(); i++) {
					JSONObject c = products.getJSONObject(i);

					String id = c.getString("id");
					String user_id = c.getString("user_id");
					String user = c.getString("user");
					String from_city_id = c.getString("from_city_id");
					String from_country_id = c.getString("from_country_id");					
					String from_city = c.getString("from_city");
					String from_country = c.getString("from_country");
					String from = c.getString("from");
					String to = c.getString("to");
					String vehicle_type = c.getString("vehicle_type");
					String from_time = c.getString("from_time");
					String to_time = c.getString("to_time");
					String no_passangers = c.getString("no_passangers");
					String cur_passangers = c.getString("cur_passangers");
					String payment_type = c.getString("payment_type");
					String status = c.getString("status");
					String to_country = c.getString("to_country");
					String to_city = c.getString("to_city");
					String to_country_id = c.getString("to_country_id");
					String to_city_id = c.getString("to_city_id");		
					String availible_passangers = c.getString("availible_passangers");
					String no_likes = c.getString("no_likes");
					String no_dislikes = c.getString("no_dislikes");		
					String phone_number = c.getString("phone_number");
					//Log.e("TAG","CITY: "+city);
					//Log.e("TAG","CITY: "+city);
					if(user.equals("null")) return;
					Trip temp = new Trip(id, user_id, from_city, from_country, from, to, vehicle_type, from_time, to_time, no_passangers, cur_passangers, payment_type, status, to_country,to_city,
							from_city_id,from_country_id,to_country_id,to_city_id,user,availible_passangers,no_likes,no_dislikes,phone_number,"");
					if(Singelton.getInstance().getTrips().isDuplicate(temp))
					Singelton.getInstance().getTrips().addTrip(temp);
				}
			} else {

				

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
	
	
}
