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

import com.droiddnamk.sharedrive.JSONParser;

public class updatePersonInfo extends AsyncTask<String, String, String> {
	String name, password, email;
	int year_of_birth;
	public String url = "http://ristokalinikov.mk/sharedrive/updatePersonInfo.php";
	JSONParser jParser = new JSONParser();
	JSONObject json;
	JSONArray products = null;
	ProgressDialog pDialog;
	Context mContext;

	public updatePersonInfo(Context context, String name, String password,
			String email, int year_of_birth) {
		mContext = context;
		this.name = name;
		this.password = password;
		this.email = email;
		this.year_of_birth = year_of_birth;
		pDialog = new ProgressDialog(context);
	}

	private boolean checkInputs() {
		int etEmail = email.length();
		int etPasswordSize = password.length();
		int etName = name.length();
		if (etEmail > 0 && etPasswordSize > 0 && etName > 0
				&& year_of_birth > 0)
			return true;
		else
			return false;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		if (!checkInputs()) {
			Toast.makeText(mContext, "Fill all fields!", Toast.LENGTH_LONG)
					.show();
			cancel(true);
			return;
		}

		pDialog.setMessage("Sending data...");
		pDialog.setCancelable(false);
		pDialog.show();

	}

	@Override
	protected String doInBackground(String... arg0) {
		try {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name_surname", "" + arg0[0]));
			params.add(new BasicNameValuePair("username", "" + arg0[1]));
			params.add(new BasicNameValuePair("password", "" + arg0[2]));
			params.add(new BasicNameValuePair("year", "" + arg0[3]));
			params.add(new BasicNameValuePair("email", "" + arg0[4]));
			json = jParser.makeHttpRequest(url, "POST", params);

			Log.d("Site: : ", json.toString());

		} catch (Exception err) {
			Log.d("updatePersonalInfo", err.getMessage());

		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		try {
			pDialog.dismiss();
			int success = json.getInt("success");
			if (success == 1) {
				Toast.makeText(mContext, "Update successful!",
						Toast.LENGTH_LONG).show();
				((Activity) mContext).finish();
			} else {
				Toast.makeText(mContext, "Error, Try again later!",
						Toast.LENGTH_LONG).show();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}