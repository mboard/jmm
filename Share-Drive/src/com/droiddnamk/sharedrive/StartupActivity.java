package com.droiddnamk.sharedrive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class StartupActivity extends Activity {

	Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startup);
		StartAnimations();
		startAppAfterActivity ac = new startAppAfterActivity(this);

		// ceke 5 sek da zavrsat 2te animacii \alpha\ i \translate\ pa ja
		// startuve animacijata \r_to_l\
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				StartToRightAnimation();

			}
		}, 4500);

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
				sleep(secounds * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Registracija i logirajne za ponatamu
			Intent i = new Intent(mContext, ListView.class);
			startActivity(i);
			overridePendingTransition( R.anim.slide_in_right, R.anim.slide_out_right );
			finish();
		}

	}

}
