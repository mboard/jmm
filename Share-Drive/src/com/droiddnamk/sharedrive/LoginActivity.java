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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*
 * 			LOGIN PAGE NOT MAIN!
 * */

public class LoginActivity extends Activity implements OnClickListener {

	EditText etUsername, etPassword;
	CheckBox chkRememberPass;
	TextView txtGoToRegister;
	ImageView btnLogin;
	public static String credentials = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		initialize();
		chkRememberPass.setChecked(true);

	}

	private boolean checkInputs() {
		int etUsernameSize = etUsername.length();
		int etPasswordSize = etPassword.length();
		if (etUsernameSize > 0 && etPasswordSize > 0)
			return true;
		else
			return false;

	}

	private void initialize() {
		etUsername = (EditText) findViewById(R.id.main_etUsername);
		etPassword = (EditText) findViewById(R.id.main_etPassword);
		btnLogin = (ImageView) findViewById(R.id.main_btnLogin);
		txtGoToRegister = (TextView) findViewById(R.id.txtGoToRegister);

		btnLogin.setOnClickListener(this);
		txtGoToRegister.setOnClickListener(this);

		chkRememberPass = (CheckBox) findViewById(R.id.chkRememberPass);
		credentialsCheck();

	}

	private void credentialsCheck() {
			SharedPreferences shared = getSharedPreferences("shared_login",
					MODE_PRIVATE);
			String s = shared.getString("shared_login_key", "");

			try {
				credentials = Enkripcija.decrypt("shdrais", s);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (credentials.length() > 0) {
			Log.e("credintials login ->", credentials);
			String[] tmp = credentials.split("###");
			if (tmp.length == 3) {
				if (tmp[2].equalsIgnoreCase("1")) {
					chkRememberPass.setChecked(true);
					etUsername.setText(tmp[0]);
					etPassword.setText(tmp[1]);
				} else
					chkRememberPass.setChecked(false);
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		if (isNetworkAvailable()) {
			switch (arg0.getId()) {
			case R.id.main_btnLogin:
				String[] params = new String[2];
				params[0] = etUsername.getText().toString();
				params[1] = etPassword.getText().toString();
				new DoLogin(this).execute(params);
				break;
			case R.id.txtGoToRegister:
				Intent i = new Intent(this, RegisterActivity.class);
				startActivity(i);
				overridePendingTransition(R.anim.slide_in_right,
						R.anim.slide_out_right);

			}
		} else
			Toast.makeText(LoginActivity.this,
					"There is no internet connection!", Toast.LENGTH_LONG)
					.show();
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	/*
	 * ASyncTask za Login
	 */
	class DoLogin extends AsyncTask<String, String, String> {

		public String url = "http://ristokalinikov.mk/sharedrive/login.php";
		JSONParser jParser = new JSONParser();
		JSONObject json;
		JSONArray products = null;
		ProgressDialog pDialog;
		Context mContext;

		DoLogin(Context context) {
			mContext = context;
			pDialog = new ProgressDialog(context);
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
				params.add(new BasicNameValuePair("username", "" + arg0[0]));
				params.add(new BasicNameValuePair("password", "" + arg0[1]));

				json = jParser.makeHttpRequest(url, "POST", params);

				Log.d("Site: : ", json.toString());

			} catch (Exception err) {
				Log.d("LoginAsync", err.getMessage());

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
					Toast.makeText(mContext, "Successful login",
							Toast.LENGTH_SHORT).show();
					int tmp_auto = 0;
					if (chkRememberPass.isChecked() == true)
						tmp_auto = 1;
					String s = etUsername.getText().toString() + "###"
							+ etPassword.getText().toString() + "###"
							+ tmp_auto;
					try {
						s = Enkripcija.encrypt("shdrais", s);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SharedPreferences shared = getSharedPreferences(
							"shared_login", Context.MODE_PRIVATE);
					Editor editor = shared.edit();
					editor.putString("shared_login_key", s);
					editor.commit();
					MainActivity.logged_username = etUsername.getText()
							.toString();
					MainActivity.logged_password = etPassword.getText()
							.toString();
					Intent i = new Intent(mContext, MainActivity.class);
					mContext.startActivity(i);
					overridePendingTransition(R.anim.slide_in_right,
							R.anim.slide_out_right);
					LoginActivity.this.finish();
					// finish();
				} else {
					Toast.makeText(mContext,
							"Error, inccorect username or password!",
							Toast.LENGTH_LONG).show();
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}

}
