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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.droiddnamk.sharedrive.JSONParser;
import com.droiddnamk.sharedrive.MainActivity;
import com.droiddnamk.sharedrive.R;

public class getPersonInfo extends AsyncTask<String, String, String> {
	public String url = "http://ristokalinikov.mk/sharedrive/getPersonInfo.php";
	JSONParser jParser = new JSONParser();
	JSONObject json;
	JSONArray products = null;
	ProgressDialog pDialog;
	Context mContext;

	public getPersonInfo(Context context) {
		mContext = context;
		pDialog = new ProgressDialog(context);
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		pDialog.setMessage("Collecting data...");
		pDialog.setCancelable(false);
		pDialog.show();

	}

	@Override
	protected String doInBackground(String... arg0) {
		try {

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", "" + arg0[0]));
			params.add(new BasicNameValuePair("password", "" + arg0[1]));
			json = jParser.makeHttpRequest(url, "GET", params);

			Log.d("Site: : ", json.toString());

		} catch (Exception err) {
			Log.d("getPersonInfo", err.getMessage());

		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		try {
			pDialog.dismiss();
			Object data = json.get("data");
			if (data.toString().length()>30) {
				//Toast.makeText(mContext, data.toString(), Toast.LENGTH_LONG).show();
				//polni EditText polinja 
				//   $final_data["data"] = $row['name_surname'] . "###" . $row['pass'] . "###" . $row['email'] . "###" . 
				//$row['year_of_birth'] . "###" . $row['no_likes'] . "###" . $row['no_dislikes'] . "###" . $row['user_photo'] . "###" .
				//$row['phone_number'] . "###" . $row['city'] . "###" . $row['country'] . "###" . $row['state'];
				// finish();
				String[] podatoci = data.toString().split("###");
				Activity act = (Activity)mContext;
				EditText etName = (EditText)act.findViewById(R.id.personal_etNameSurname);
				EditText etUsername = (EditText)act.findViewById(R.id.personal_etUsername);
				EditText etEmail = (EditText)act.findViewById(R.id.personal_etEmail);
				EditText etPass = (EditText)act.findViewById(R.id.personal_etPassword);
				Spinner spinnerYears = (Spinner)act.findViewById(R.id.personal_ddlYears);
				TextView txtLike = (TextView)act.findViewById(R.id.txtLike);
				TextView txtDislike = (TextView)act.findViewById(R.id.txtDislike);				
				etName.setText(podatoci[0].trim());
				etUsername.setText(MainActivity.logged_username);
				etEmail.setText(podatoci[2].trim());
				etPass.setText(podatoci[1].trim());
				ArrayAdapter<String> tmp = (ArrayAdapter<String>) spinnerYears.getAdapter();
				spinnerYears.setSelection(tmp.getPosition(podatoci[3].trim()));
				txtLike.setText(podatoci[4].trim());
				txtDislike.setText(podatoci[5].trim());
			} else {
				Toast.makeText(mContext,
						"Error, Try again later!",
						Toast.LENGTH_LONG).show();
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
