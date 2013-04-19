package com.droiddnamk.sharedrive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


public class TestActivity extends Activity {
    /** Called when the activity is first created. */
	Intent i;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
    }
}