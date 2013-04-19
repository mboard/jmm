package com.droiddnamk.sharedrive;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AdvancedInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.advanced_info_layout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.advanced_info, menu);
		return true;
	}

}
