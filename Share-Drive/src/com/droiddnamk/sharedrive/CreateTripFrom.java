package com.droiddnamk.sharedrive;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.net.ParseException;
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

public class CreateTripFrom extends Activity implements OnClickListener,
		OnItemSelectedListener {

	ImageView next;
	public static Spinner from_country, from_city;
	public static String[] cities_desc, cities_id;
	public static String city_temp;
	public static DatePicker from_date;
	public static TimePicker from_time;
	public static EditText from_address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_trip_from);
		next = (ImageView) findViewById(R.id.btnNext);
		next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				StringBuilder sb = new StringBuilder();
				sb.append(from_date.getYear()).append("-")
						.append(from_date.getMonth()+1)
						.append("-")
						.append(from_date.getDayOfMonth());
				CreateTrip.from_date = sb.toString();

				sb = new StringBuilder();

				sb.append(CreateTripFrom.from_time.getCurrentHour())
						.append(":")
						.append(CreateTripFrom.from_time.getCurrentMinute())
						.append(":00");
				CreateTrip.from_time = sb.toString();
				//Toast.makeText(CreateTripFrom.this, CreateTrip.from_date + " " + CreateTrip.from_time, Toast.LENGTH_LONG).show();
				DateFormat writeFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
				CreateTrip.from_date_date = null;
			    	try {
						CreateTrip.from_date_date = writeFormat.parse( CreateTrip.from_date + " " + CreateTrip.from_time );
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				CreateTrip.tabHost.setCurrentTab(1);
			}
		});

		from_city = (Spinner) findViewById(R.id.from_city);
		from_country = (Spinner) findViewById(R.id.from_country);
		from_country.setOnItemSelectedListener(this);
		ArrayAdapter<String> adapterCountries = new ArrayAdapter<String>(this,
				R.layout.spinner_item, Countries.countries_list);
		from_country.setAdapter(adapterCountries);
		for (int i1 = 0; i1 < from_country.getCount(); i1++) {
			if ((MainActivity.cur_country != "")
					&& (MainActivity.cur_country != "null")
					&& (MainActivity.cur_country != null)) {
				if (MainActivity.cur_country.equals(from_country
						.getItemAtPosition(i1).toString())) {
					from_country.setSelection(i1);
				}
			} else
				from_country.setSelection(0);
		}
		from_date = (DatePicker) findViewById(R.id.from_date);
		from_time = (TimePicker) findViewById(R.id.from_time);
		from_address = (EditText) findViewById(R.id.from_address);
	}

	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		switch (arg0.getId()) {
		case R.id.from_country:
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
					CreateTripFrom.this, R.layout.spinner_item, cities_desc);
			from_city.setAdapter(adapterCities);
			for (int i1 = 0; i1 < from_city.getCount(); i1++) {
				if ((MainActivity.cur_city != "")
						&& (MainActivity.cur_city != "null")
						&& (MainActivity.cur_city != null)) {
					if (MainActivity.cur_city.equals(from_city
							.getItemAtPosition(i1).toString())) {
						from_city.setSelection(i1);
					}
				}
			}

		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
