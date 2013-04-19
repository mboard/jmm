package com.droiddnamk.sharedrive;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.droiddnamk.sharedrive.webcommunication.getPersonInfo;
import com.droiddnamk.sharedrive.webcommunication.updatePersonInfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PersonalInfoActivity extends Activity implements OnClickListener {

	// spinner
	Spinner ddlYears;
	ArrayList<String> listYears = new ArrayList<String>();
	// rest
	EditText etNameSurname, etUsername, etPassword,etEmail;
	TextView txtGoToAdvanced;
	ImageView btnUpdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personal_info_layout);
		setUpYears();
		initialize();
		String[] params = new String[2];
		params[0] = MainActivity.logged_username;
		params[1] = MainActivity.logged_password;
		new getPersonInfo(this).execute(params);
	}

	private void setUpYears() {
		int yearNow = Calendar.getInstance().get(Calendar.YEAR);
		// Log.e("Register.class", "Year: " + yearNow);
		int start = 1940;
		while (start < yearNow) {
			listYears.add("" + start);
			start++;
		}
	}

	private void initialize() {
		// spinner
		ddlYears = (Spinner) findViewById(R.id.personal_ddlYears);
		ArrayAdapter<String> arrayAD = new ArrayAdapter<String>(
				getApplicationContext(), R.layout.spinner_item, listYears);
		ddlYears.setAdapter(arrayAD);
		// rest
		etEmail = (EditText) findViewById(R.id.personal_etEmail);
		etNameSurname = (EditText) findViewById(R.id.personal_etNameSurname);
		etUsername = (EditText) findViewById(R.id.personal_etUsername);
		etPassword = (EditText) findViewById(R.id.personal_etPassword);
		txtGoToAdvanced = (TextView) findViewById(R.id.txtGoToAdvanced);
		btnUpdate = (ImageView) findViewById(R.id.personal_btnUpdate);
		btnUpdate.setOnClickListener(this);
		txtGoToAdvanced.setOnClickListener(this);
	}


	@Override
	public void onClick(View arg0) {
		if (isNetworkAvailable()) {
			switch (arg0.getId()) {

			case R.id.personal_btnUpdate:
				String[] params = new String[5];
				params[0] = etNameSurname.getText().toString();
				params[1] = etUsername.getText().toString();
				params[2] = etPassword.getText().toString();
				params[3] = ddlYears.getSelectedItem().toString();
				params[4] = etEmail.getText().toString();
				//updatePersonInfo(Context context,String name,String password,String email,int year_of_birth) {
				new updatePersonInfo(this,etNameSurname.getText().toString(),etPassword.getText().toString(),
						etEmail.getText().toString(),Integer.parseInt(ddlYears.getSelectedItem().toString())).execute(params);
				break;
			case R.id.txtGoToAdvanced:
				
				 Intent i = new Intent(this, AdvancedInfoActivity.class);
				 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivity(i);
				 overridePendingTransition(R.anim.slide_in_right,
				 R.anim.slide_out_right);
			}
		} else
			Toast.makeText(PersonalInfoActivity.this,
					"There is no internet connection!", Toast.LENGTH_LONG)
					.show();
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

}
