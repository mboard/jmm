package com.droiddnamk.sharedrive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class StartupActivity extends Activity {

	Handler handler = new Handler();
	String speed;
	int speed2 = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_startup);
		SharedPreferences shared = getSharedPreferences("shared_speed",
				MODE_PRIVATE);
		speed = shared.getString("shared_speed_key", "On");
		if (speed.equalsIgnoreCase("Off")) {
			/*Intent i;
			shared = StartupActivity.this.getSharedPreferences("shared_login",
					MODE_PRIVATE);
			String s = shared.getString("shared_login_key", "");
			if (s.length() > 0) {
				i = new Intent(this, LoginActivity.class);

			} else {
				i = new Intent(this, RegisterActivity.class);
			}
			startActivity(i);
			finish();*/
			speed2 = 0;
			//return;
		} else
			speed2 = 1;
		StartAnimations();
		startAppAfterActivity ac = new startAppAfterActivity(this);

		// ceke 5 sek da zavrsat 2te animacii \alpha\ i \translate\ pa ja
		// startuve animacijata \r_to_l\
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				StartToRightAnimation();

			}
		}, (4500 * speed2));

		// vo isto vreme nitkata spie 9 sekundi (5sek za prvite 2 animacii, i 4
		// za poslednata koga ode na levo pa sa startuve noavto aktiviti...
		ac.start();
	}

	private void StartAnimations() {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
		anim.reset();
		LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
		l.clearAnimation();
		l.startAnimation(anim);

		anim = AnimationUtils.loadAnimation(this, R.anim.translate);
		anim.reset();
		ImageView iv = (ImageView) findViewById(R.id.logo);
		iv.clearAnimation();
		iv.startAnimation(anim);

	}

	private void StartToRightAnimation() {

		Animation anim = AnimationUtils.loadAnimation(this, R.anim.r_to_l);
		anim.reset();
		ImageView iv = (ImageView) findViewById(R.id.logo);
		iv.clearAnimation();
		iv.startAnimation(anim);

	}

	class startAppAfterActivity extends Thread {
		int secounds = 9;
		Context mContext;

		startAppAfterActivity(Context m) {
			mContext = m;
		}

		@Override
		public void run() {
			super.run();
			try {
				sleep((secounds * 1000) * speed2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Registracija i logirajne za ponatamu
			// shared format - "username###password###auto(1/0)"
			Intent i;
			SharedPreferences shared = StartupActivity.this
					.getSharedPreferences("shared_login", MODE_PRIVATE);
			String s = shared.getString("shared_login_key", "");
			if (s.length() > 0) {
				i = new Intent(mContext, LoginActivity.class);

			} else {
				i = new Intent(mContext, RegisterActivity.class);
			}
			startActivity(i);
			finish();
		}

	}

}
