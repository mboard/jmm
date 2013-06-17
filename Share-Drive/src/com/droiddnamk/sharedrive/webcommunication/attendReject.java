package com.droiddnamk.sharedrive.webcommunication;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.droiddnamk.sharedrive.CustomAdapter;
import com.droiddnamk.sharedrive.JSONParser;
import com.droiddnamk.sharedrive.MainActivity;
import com.droiddnamk.sharedrive.StaticData.Singelton;

public class attendReject extends AsyncTask<Void, String, Void> {

	public static final String url = "http://ristokalinikov.mk/sharedrive/attendCancel.php";
	JSONParser jParser = new JSONParser();
	JSONObject json;
	JSONArray products = null;
	String trip_id, status, no_passangers;
	Context mContext;
	ProgressDialog dialog;

	public attendReject(Context c, String trip_id, String status,
			String no_passangers) {
		this.trip_id = trip_id;
		this.status = status;
		this.no_passangers = no_passangers;
		mContext = c;
		dialog = new ProgressDialog(c);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.setMessage("Updating...");
		dialog.setCancelable(false);
		dialog.show();
		
	}

	@Override
	protected Void doInBackground(Void... m) {
		try {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("user_id", MainActivity.logged_id));
			params.add(new BasicNameValuePair("trip_id", trip_id));
			params.add(new BasicNameValuePair("status", status));
			params.add(new BasicNameValuePair("no_passangers", no_passangers));
			Log.e("user_id =  ", MainActivity.logged_id);
			Log.e("trip_id =  ", trip_id);
			Log.e("status =  ", status);
			Log.e("no_passangers =  ", no_passangers);
			json = jParser.makeHttpRequest(url, "POST", params);
		} catch (Exception err) {
			Log.d("Greska addNew doInBackground() ", err.getMessage());
		}

		return null;

	}

	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		try {
			String success = json.getString("success");
			if (success.equals("0")) {
				Toast.makeText(mContext,
						"Error while communicating with server...",
						Toast.LENGTH_SHORT).show();
			}
			if (success.equals("1")) {
				Toast.makeText(mContext, "Sorry, all the seats are busy!",
						Toast.LENGTH_SHORT).show();
			}
			if (success.equals("2") || success.equals("3")) {
				int i = 0;
				while (i < Singelton.getInstance().getTrips().getTrips().size()) {
					if (Singelton.getInstance().getTrips().getTrips().get(i)
							.getId().equals(trip_id)) {
						Singelton.getInstance().getTrips().getTrips().get(i)
								.setStatus2(status);
						String tmpType = "!";
						if (success.equals("2")) {
							tmpType = "reserved!";
							Singelton
									.getInstance()
									.getTrips()
									.getTrips()
									.get(i)
									.setCur_passangers(
											""
													+ (Integer
															.parseInt(Singelton
																	.getInstance()
																	.getTrips()
																	.getTrips()
																	.get(i)
																	.getCur_passangers()) + 1));
							Singelton
									.getInstance()
									.getTrips()
									.getTrips()
									.get(i)
									.setAvailible_passangers(
											""
													+ (Integer
															.parseInt(Singelton
																	.getInstance()
																	.getTrips()
																	.getTrips()
																	.get(i)
																	.getAvailible_passangers()) - 1));
						}
						if (success.equals("3")) {
							tmpType = "cancelled!";
							Singelton
									.getInstance()
									.getTrips()
									.getTrips()
									.get(i)
									.setCur_passangers(
											""
													+ (Integer
															.parseInt(Singelton
																	.getInstance()
																	.getTrips()
																	.getTrips()
																	.get(i)
																	.getCur_passangers()) - 1));
							Singelton
									.getInstance()
									.getTrips()
									.getTrips()
									.get(i)
									.setAvailible_passangers(
											""
													+ (Integer
															.parseInt(Singelton
																	.getInstance()
																	.getTrips()
																	.getTrips()
																	.get(i)
																	.getAvailible_passangers()) + 1));
						}
						MainActivity.adapter.notifyDataSetChanged();

						Toast.makeText(mContext,
								"Congratulations, your seat is " + tmpType,
								Toast.LENGTH_SHORT).show();
					}
					i++;
				}
			}
			dialog.dismiss();
			
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		

	}

}
