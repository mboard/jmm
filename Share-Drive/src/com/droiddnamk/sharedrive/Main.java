package com.droiddnamk.sharedrive;

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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/*
 * 			LOGIN PAGE NOT MAIN!
 * */

public class Main extends Activity implements OnClickListener {
	
	EditText etUsername,etPassword;
	ImageView btnLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initialize();
		
		
	}

	private boolean checkInputs() {
		int etUsernameSize = etUsername.length();
		int etPasswordSize = etPassword.length();
		if(etUsernameSize > 0 && etPasswordSize>0)
			return true;
		else return false;
		
	}

	private void initialize() {
		etUsername = (EditText)findViewById(R.id.main_etUsername);
		etPassword = (EditText)findViewById(R.id.main_etPassword);
		btnLogin = (ImageView)findViewById(R.id.main_btnLogin);
		btnLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
			case R.id.main_btnLogin:
			String[] params = new String[2];
			params[0] = etUsername.getText().toString();
			params[1] = etPassword.getText().toString();
			new DoLogin(this).execute(params);
			break;

		}

		
	}
	
	/*
	 * ASyncTask za Login
	 * */
	class DoLogin extends AsyncTask<String, String, String> {

		public String url = "http://50.116.84.63/~risto/sharedrive/login.php";
		JSONParser jParser = new JSONParser();
		JSONObject json;
		JSONArray products = null;
		ProgressDialog pDialog;
		Context mContext;
		
		DoLogin(Context context){
			mContext = context;
			pDialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			if(!checkInputs()){ 
				Toast.makeText(mContext, "Fill all fields!", Toast.LENGTH_LONG).show();
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
				params.add(new BasicNameValuePair("username", "" + arg0[0]));
				params.add(new BasicNameValuePair("password", "" + arg0[1]));

				json = jParser.makeHttpRequest(url, "GET", params);

				Log.d("Site: : ", json.toString());

			} catch (Exception err) {
				Log.d("DoRegister", err.getMessage());

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
					Toast.makeText(mContext, "Successful login", Toast.LENGTH_LONG).show();
					mContext.startActivity(new Intent(mContext,ListView.class));
					overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right );
					finish();
				}else{
					Toast.makeText(mContext, "Error, inccorect username or password!", Toast.LENGTH_LONG).show();
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}
	



}
