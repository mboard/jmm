package com.droiddnamk.sharedrive;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.droiddnamk.sharedrive.StaticData.Singelton;
import com.droiddnamk.sharedrive.swipemenu.MenuEventController;
import com.droiddnamk.sharedrive.swipemenu.MenuLazyAdapter;
import com.droiddnamk.sharedrive.swipemenu.OnSwipeTouchListener;
import com.droiddnamk.sharedrive.webcommunication.getTrips;
import com.droiddnamk.sharedrive.webcommunication.updateTripService;
import com.droiddnamk.sharedrive.webcommunication.updateTripsBroadcast;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class MainActivity extends Activity implements OnItemClickListener {

	// Ova e test lista.. ke bide promeneta..
	public static final String ID = "id";
	public static final String ICON = "icon";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";
	public static String logged_username, logged_password;
	private RelativeLayout layout;
	private LinearLayout layout2;
	private MenuLazyAdapter menuAdapter;
	private boolean open = false;

	private final Context context = this;

	private ListView listMenu;
	private TextView appName;

	public PullToRefreshListView listView;
	public static CustomAdapter adapter;
	
	Intent service;
	updateTripsBroadcast receiver;
	IntentFilter broadcastIntent;
	public static final String BROADCAST_ACTION = "B_A";

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		menuAdapter.updateLoggedInTitle(8, "Your Profile ("
				+ MainActivity.logged_username + ")");
		menuAdapter.notifyDataSetChanged();
		
		if(receiver == null){
			broadcastIntent = new IntentFilter(BROADCAST_ACTION);
			receiver = new updateTripsBroadcast();
		}
		
		registerReceiver(receiver, broadcastIntent);
		
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		service = new Intent(this,updateTripService.class);
		Toast.makeText(this, "Pull down the list to Refresh", Toast.LENGTH_LONG)
				.show();
		listView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);

		listView.setLockScrollWhileRefreshing(true);

		listView.setShowLastUpdatedText(true);

		listView.setLastUpdatedDateFormat(new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss"));

		// listView.setTextPullToRefresh("Pull to Refresh");
		// listView.setTextReleaseToRefresh("Release to Refresh");
		// listView.setTextRefreshing("Refreshing");
		
		new getTrips(this).execute();
		startService(service);

		listView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {

				listView.postDelayed(new Runnable() {

					@Override
					public void run() {
						new getTrips(context).execute();
						listView.onRefreshComplete();
					}
				}, 2000);
			}
		});

		adapter = new CustomAdapter(this, Singelton.getInstance().getTrips().getTrips());
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(this);

		initializeSwipe_Menu();
	}

	private void initializeSwipe_Menu() {
		// TODO Auto-generated method stub
		listMenu = (ListView) findViewById(R.id.listMenu);
		layout = (RelativeLayout) findViewById(R.id.layoutToMove);
		layout2 = (LinearLayout) findViewById(R.id.layoutToHide);
		appName = (TextView) findViewById(R.id.appName);
		menuAdapter = new MenuLazyAdapter(this,
				MenuEventController.menuArray.size() == 0 ? MenuEventController
						.getMenuDefault(this) : MenuEventController.menuArray);
		listMenu.setAdapter(menuAdapter);
		layout.setOnTouchListener(new OnSwipeTouchListener() {
			public void onSwipeRight() {
				if (!open) {
					open = true;
					MenuEventController.open(context, layout, layout2, appName);
					MenuEventController.closeKeyboard(context,
							getCurrentFocus());
				}
			}

			public void onSwipeLeft() {
				if (open) {
					open = false;
					MenuEventController
							.close(context, layout, layout2, appName);
					MenuEventController.closeKeyboard(context,
							getCurrentFocus());
				}
			}
		});

		listMenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/*
				 * HashMap<String, Object> menuConfig;// = new HashMap<String,
				 * Object>(); menuConfig = (HashMap<String, Object>)
				 * menuAdapter.getItem(position);
				 * Toast.makeText(MainActivity.this
				 * ,menuConfig.get("id").toString(), Toast.LENGTH_SHORT).show();
				 * Intent intent = null; if(position == 0){ //action intent =
				 * new Intent(MainActivity.this,TestActivity.class); } else
				 * if(position == 1){ //action Intent i = new
				 * Intent(MainActivity.this,LoginActivity.class);
				 * i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); startActivity(i);
				 * overridePendingTransition(R.anim.slide_in_right,
				 * R.anim.slide_out_right); } else if(position == 2){ //if
				 * activity is this just close menu before verify if menu is
				 * open if(open){ open = false;
				 * MenuEventController.close(context, layout,layout2, appName);
				 * MenuEventController.closeKeyboard(context, view); } } else
				 * if(position == 3){ //action } else if(position == 4){
				 * //action } else if(position == 5){ //action } else
				 * if(position == 6){ //action } else if(position == 7){
				 * //action }
				 * 
				 * //Check the position if different of current a intent are
				 * started else menu just closed if(position != 2){
				 * startActivity(intent); MainActivity.this.finish();
				 * overridePendingTransition(R.anim.slide_left,
				 * R.anim.slide_left); }
				 */
			}
		});
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		//TextView temp = (TextView) view.findViewById(R.id.custom_text);
		// Toast.makeText(this,temp.getText().toString(),
		// Toast.LENGTH_SHORT).show();

	}

	public void openCloseMenu(View view) {
		if (!this.open) {
			this.open = true;
			MenuEventController.open(this.context, this.layout, layout2,
					this.appName);
			MenuEventController.closeKeyboard(this.context, view);
		} else {
			this.open = false;
			MenuEventController.close(this.context, this.layout, layout2,
					this.appName);
			MenuEventController.closeKeyboard(this.context, view);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(service);
		unregisterReceiver(receiver);
	}

}
