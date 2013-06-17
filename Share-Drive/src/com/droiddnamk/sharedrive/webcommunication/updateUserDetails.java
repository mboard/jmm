package com.droiddnamk.sharedrive.webcommunication;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.droiddnamk.sharedrive.AdvancedInfoActivity;
import com.droiddnamk.sharedrive.JSONParser;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class updateUserDetails extends AsyncTask<String, String, String> {

	public String url = "http://ristokalinikov.mk/sharedrive/updateUserDetails.php";
	JSONParser jParser = new JSONParser();
	JSONObject json;
	JSONArray products = null;
	ProgressDialog dialog;
	Context mContext;

	public updateUserDetails(Context context) {
		mContext = context;
		dialog = new ProgressDialog(mContext);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		dialog.setMessage("Updating details...");
		dialog.setCancelable(false);
		dialog.show();

	}

	@Override
	protected String doInBackground(String... p) {
		try {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("phone", p[0]));
			params.add(new BasicNameValuePair("city", p[1]));
			params.add(new BasicNameValuePair("state", p[2]));
			params.add(new BasicNameValuePair("country", p[3]));
			params.add(new BasicNameValuePair("image", p[4]));
			params.add(new BasicNameValuePair("username", p[5]));
			params.add(new BasicNameValuePair("city_id", p[6]));
			params.add(new BasicNameValuePair("country_id", p[7]));			
			json = jParser.makeHttpRequest(url, "POST", params);

			Log.d("Site: : ", json.toString());

		} catch (Exception err) {
			Log.d("updateDetails", err.getMessage());
		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		try {
			dialog.dismiss();
			int success = json.getInt("success");
			if (success == 1) {
				Toast.makeText(mContext, "Update successful!",
						Toast.LENGTH_LONG).show();
				((Activity) mContext).finish();
			} else {
				Toast.makeText(mContext, "Error, Try again later!",
						Toast.LENGTH_LONG).show();
			}	
			AdvancedInfoActivity.buttonUpdate.setEnabled(true);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
