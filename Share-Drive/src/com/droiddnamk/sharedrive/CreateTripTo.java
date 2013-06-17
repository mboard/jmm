package com.droiddnamk.sharedrive;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.droiddnamk.sharedrive.customClasses.Countries;
import com.droiddnamk.sharedrive.webcommunication.GetMessageSender;
import com.droiddnamk.sharedrive.webcommunication.createTrip;

public class CreateTripTo extends Activity implements OnClickListener,
		OnItemSelectedListener {
	ImageView next, to_prev;
	public static Spinner to_country, to_city;
	public static String[] cities_desc, cities_id;
	public static String city_temp;
	public static DatePicker to_date;
	public static TimePicker to_time;
	public static EditText to_address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_trip_to);
		next = (ImageView) findViewById(R.id.btnNext);
		next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				StringBuilder sb = new StringBuilder();
				sb = new StringBuilder();
				sb.append(CreateTripTo.to_date.getYear()).append("-")
						.append(CreateTripTo.to_date.getMonth()+1).append("-")
						.append(CreateTripTo.to_date.getDayOfMonth());
				CreateTrip.to_date = sb.toString();

				sb = new StringBuilder();

				sb.append(CreateTripTo.to_time.getCurrentHour()).append(":")
						.append(CreateTripTo.to_time.getCurrentMinute())
						.append(":00");
				CreateTrip.to_time = sb.toString();
				//Toast.makeText(CreateTripTo.this, CreateTrip.to_date + " " + CreateTrip.to_time, Toast.LENGTH_LONG).show();
				DateFormat writeFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
				CreateTrip.to_date_date = null;
		    	try {
					CreateTrip.to_date_date = writeFormat.parse( CreateTrip.to_date + " " + CreateTrip.to_time );
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	if(CreateTrip.from_date_date.before(CreateTrip.to_date_date)){
				CreateTrip.tabHost.setCurrentTab(2);
		    	}
		    	else{
		    		Toast.makeText(CreateTripTo.this, "From Date is after TO Date! Please correct your informations!", Toast.LENGTH_SHORT).show();
		    	}
			}
		});
		to_prev = (ImageView) findViewById(R.id.btnToPrev);
		to_prev.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CreateTrip.tabHost.setCurrentTab(0);
			}
		});
		to_city = (Spinner) findViewById(R.id.to_city);
		to_country = (Spinner) findViewById(R.id.to_country);
		to_country.setOnItemSelectedListener(this);
		ArrayAdapter<String> adapterCountries = new ArrayAdapter<String>(this,
				R.layout.spinner_item, Countries.countries_list);
		to_country.setAdapter(adapterCountries);
		for (int i1 = 0; i1 < to_country.getCount(); i1++) {
			if ((MainActivity.cur_country != "")
					&& (MainActivity.cur_country != "null")
					&& (MainActivity.cur_country != null)) {
				if (MainActivity.cur_country.equals(to_country
						.getItemAtPosition(i1).toString())) {
					to_country.setSelection(i1);
				}
			} else
				to_country.setSelection(0);
		}
		to_date = (DatePicker) findViewById(R.id.to_date);
		to_time = (TimePicker) findViewById(R.id.to_time);
		to_address = (EditText) findViewById(R.id.to_address);
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		switch (arg0.getId()) {
		case R.id.to_country:
			loadCities(arg2);
			break;

		default:
			break;
		}

	}

	private void loadCities(final int arg2) {
		new setCities().execute(arg2 + "", city_temp);

	}

	private void ParseCities(String data) {
		String[] tmp = data.split("###");
		cities_desc = new String[tmp.length];
		cities_id = new String[tmp.length];
		int i = 0;
		String[] tmp2;
		while (i < tmp.length) {
			tmp2 = tmp[i].split("~~~");
			cities_desc[i] = tmp2[1];
			cities_id[i] = tmp2[0];
			i++;
		}
	}

	public void onNothingSelected(AdapterView<?> arg0) {

	}

	class setCities extends AsyncTask<String, Integer, Void> {
		@Override
		protected void onPreExecute() {

		}

		@Override
		protected Void doInBackground(String... params) {
			String code_country = Countries.countries.get(
					Integer.parseInt(params[0])).getCode();
			GetMessageSender gms = new GetMessageSender();
			String data = gms
					.sendMessage("http://www.ristokalinikov.mk/sharedrive/getCities.php?code="
							+ code_country);
			Log.e("data Cities->", data);
			ParseCities(data);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			ArrayAdapter<String> adapterCities = new ArrayAdapter<String>(
					CreateTripTo.this, R.layout.spinner_item, cities_desc);
			to_city.setAdapter(adapterCities);
			for (int i1 = 0; i1 < to_city.getCount(); i1++) {
				if ((MainActivity.cur_city != "")
						&& (MainActivity.cur_city != "null")
						&& (MainActivity.cur_city != null)) {
					if (MainActivity.cur_city.equals(to_city.getItemAtPosition(
							i1).toString())) {
						to_city.setSelection(i1);
					}
				}
			}
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

}
