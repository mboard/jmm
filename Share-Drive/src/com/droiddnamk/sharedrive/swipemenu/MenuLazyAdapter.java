package com.droiddnamk.sharedrive.swipemenu;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.droiddnamk.sharedrive.Enkripcija;
import com.droiddnamk.sharedrive.LoginActivity;
import com.droiddnamk.sharedrive.MainActivity;
import com.droiddnamk.sharedrive.PersonalInfoActivity;
import com.droiddnamk.sharedrive.R;
import com.droiddnamk.sharedrive.webcommunication.getPersonInfo;

/**
 * Class to control of elements in the list menu
 * 
 * @author Leonardo Salles
 */
public class MenuLazyAdapter extends BaseAdapter {

	private Activity activity;
	HashMap<String, Object> menuConfig;
	private ArrayList<HashMap<String, Object>> data;
	private static LayoutInflater inflater = null;

	public MenuLazyAdapter(Activity a, ArrayList<HashMap<String, Object>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public void updateLoggedInTitle(int id, String new_title) {
		HashMap<String, Object> menuConfig2 = new HashMap<String, Object>();
		int i = 0;
		for (HashMap<String, Object> hashMap : data) {
			if (hashMap.get("id").toString().equals("8")) {
				menuConfig2 = data.get(i);
				menuConfig2.put("title", new_title);
				data.set(i, menuConfig2);
			}
			i++;
		}
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View vi = convertView;

		if (convertView == null) {
			vi = inflater.inflate(R.layout.menu_list_row, null);
		}

		TextView menuTitle = (TextView) vi.findViewById(R.id.menuTitle);
		ImageView menuIcon = (ImageView) vi.findViewById(R.id.menuIcon);
		TextView menuGroup = (TextView) vi.findViewById(R.id.txtGRUPA);
		LinearLayout menuItemLayout = (LinearLayout) vi
				.findViewById(R.id.menuItem);
		menuConfig = new HashMap<String, Object>();
		menuConfig = data.get(position);

		menuTitle.setText((String) menuConfig.get("title"));
		menuGroup.setText((String) menuConfig.get("group"));
		if (((String) menuConfig.get("group")).equalsIgnoreCase("")) {
			menuGroup.setVisibility(View.GONE);
			LayoutParams params = (LayoutParams) menuItemLayout
					.getLayoutParams();
			// params.setMargins(left, top, right, bottom);
			params.setMargins(15, 5, 5, 0);
			menuItemLayout.setLayoutParams(params);
		} else {
			menuGroup.setVisibility(View.VISIBLE);
			LayoutParams params = (LayoutParams) menuItemLayout
					.getLayoutParams();
			// params.setMargins(left, top, right, bottom);
			params.setMargins(15, 35, 5, 0);
			menuItemLayout.setLayoutParams(params);
		}
		menuIcon.setImageResource((Integer) menuConfig.get("icon"));
		vi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (data.get(position).get("id").toString()
						.equalsIgnoreCase("9")) {

					AlertDialog.Builder adb = new AlertDialog.Builder(activity);
					adb.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									SharedPreferences shared = activity
											.getSharedPreferences(
													"shared_login",
													Context.MODE_PRIVATE);
									String s = shared.getString(
											"shared_login_key", "");
									try {
										String yyy = Enkripcija.decrypt(
												"shdrais", s);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									s = s.substring(0, s.length() - 1) + '0';
									Log.e(" Credentials -> ", s);
									LoginActivity.credentials = s;
									try {
										s = Enkripcija.encrypt("shdrais", s);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									Editor editor = shared.edit();
									editor.putString("shared_login_key", s);
									editor.commit();
									Intent i = new Intent(activity,
											LoginActivity.class);
									i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									activity.startActivity(i);
									activity.finish();
									dialog.cancel();
								}
							});

					adb.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});

					AlertDialog dialog = adb.create();
					dialog.setTitle("Logging out !");
					dialog.setMessage("Are you shure?");
					dialog.show();
				}
				if (data.get(position).get("id").toString()
						.equalsIgnoreCase("8")) {
					if (isNetworkAvailable()) {
						Intent i = new Intent(activity,
								PersonalInfoActivity.class);
						activity.startActivity(i);
					} else {
						Toast.makeText(activity,
								"You have no internet connection",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});
		return vi;
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}
}