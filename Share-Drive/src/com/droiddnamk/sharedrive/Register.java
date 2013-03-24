package com.droiddnamk.sharedrive;

import java.util.ArrayList;
import java.util.Calendar;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class Register extends Activity implements OnClickListener {

	// spinner
	Spinner ddlYears;
	ArrayList<String> listYears = new ArrayList<String>();
	// rest
	EditText etNameSurname, etUsername, etPassword, etRePassword;
	ImageView btnRegister;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		setUpYears();
		initialize();
	
	}

	private void setUpYears() {
		int yearNow = Calendar.getInstance().get(Calendar.YEAR);
		Log.e("Register.class","Year: "+yearNow);
		int start = 1940;
		while(start < yearNow){
			listYears.add(""+start);
			start++;
		}
	}

	private void initialize() {
		//spinner
		ddlYears = (Spinner)findViewById(R.id.register_ddlYears);
		ArrayAdapter<String> arrayAD = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, listYears);
		ddlYears.setAdapter(arrayAD);
		// rest
		etNameSurname = (EditText)findViewById(R.id.register_etNameSurname);
		etUsername = (EditText)findViewById(R.id.register_etUsername);
		etPassword = (EditText)findViewById(R.id.register_etPassword);
		etRePassword = (EditText)findViewById(R.id.register_etRePassword);
		
		btnRegister = (ImageView)findViewById(R.id.register_btnRegister);
		btnRegister.setOnClickListener(this);
	}
	
	private boolean checkInputs() {
		int etNameSurnameSize = etNameSurname.length();
		int etUsernameSize = etUsername.length();
		int etPasswordSize = etPassword.length();
		int etRePasswordSize = etRePassword.length();
		
		if(etNameSurnameSize > 0 && etUsernameSize > 0 && etPasswordSize > 0 && etRePasswordSize > 0)
			return true;
		else return false;
	}
	
	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		
		case R.id.register_btnRegister:
			String[] params = new String[4];
			params[0]=etNameSurname.getText().toString();
			params[1]=etUsername.getText().toString();
			params[2]=etPassword.getText().toString();
			params[3]=ddlYears.getSelectedItem().toString();
			
			new DoRegister(this).execute(params);
			break;
			
			
		}
		
	}

	/*
	 * ASyncTask za Register
	 * */
	class DoRegister extends AsyncTask<String, String, String> {

		public String url = "http://50.116.84.63/~risto/sharedrive/register.php";
		JSONParser jParser = new JSONParser();
		JSONObject json;
		JSONArray products = null;
		ProgressDialog pDialog;
		Context mContext;
		
		DoRegister(Context context){
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
				params.add(new BasicNameValuePair("name_surname", "" + arg0[0]));
				params.add(new BasicNameValuePair("username", "" + arg0[1]));
				params.add(new BasicNameValuePair("password", "" + arg0[2]));
				params.add(new BasicNameValuePair("year", "" + arg0[3]));

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
					Toast.makeText(mContext, "Successful registered", Toast.LENGTH_LONG).show();
					mContext.startActivity(new Intent(mContext,Main.class));
					overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right );
					finish();
				}else if(success == 2){
					Toast.makeText(mContext, "Error, choose other username!", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(mContext, "Error, try again!", Toast.LENGTH_LONG).show();
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}
	

}
