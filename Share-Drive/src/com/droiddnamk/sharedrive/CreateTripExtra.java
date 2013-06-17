package com.droiddnamk.sharedrive;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.net.ParseException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.droiddnamk.sharedrive.RegisterActivity.DoRegister;
import com.droiddnamk.sharedrive.customClasses.Countries;
import com.droiddnamk.sharedrive.webcommunication.createTrip;

public class CreateTripExtra extends Activity {
	ImageView cancel, confirm, extra_prev;
	EditText no_pass;
	public static Spinner spin_vehicle_type, spin_payment_type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_trip_extra);
		spin_vehicle_type = (Spinner) findViewById(R.id.vehicleType);
		spin_payment_type = (Spinner) findViewById(R.id.paymentType);
		cancel = (ImageView) findViewById(R.id.btnCancel);
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
		extra_prev = (ImageView) findViewById(R.id.btnExtra_Prev);
		extra_prev.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CreateTrip.tabHost.setCurrentTab(1);
			}
		});
		confirm = (ImageView) findViewById(R.id.btnConfirm);
		confirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				CreateTrip.from_country_id = Countries.countries.get(
						CreateTripFrom.from_country.getSelectedItemPosition())
						.getId();
				CreateTrip.from_country = CreateTripFrom.from_country
						.getSelectedItem().toString();

				CreateTrip.from_city_id = CreateTripFrom.cities_id[CreateTripFrom.from_city
						.getSelectedItemPosition()];
				CreateTrip.from_city = CreateTripFrom.from_city
						.getSelectedItem().toString();

				CreateTrip.to_country_id = Countries.countries.get(
						CreateTripTo.to_country.getSelectedItemPosition())
						.getId();
				CreateTrip.to_country = CreateTripTo.to_country
						.getSelectedItem().toString();

				CreateTrip.to_city_id = CreateTripTo.cities_id[CreateTripTo.to_city
						.getSelectedItemPosition()];
				CreateTrip.to_city = CreateTripTo.to_city.getSelectedItem()
						.toString();

				StringBuilder sb = new StringBuilder();
				sb.append(CreateTripFrom.from_date.getYear()).append("-")
						.append(CreateTripFrom.from_date.getMonth()+1)
						.append("-")
						.append(CreateTripFrom.from_date.getDayOfMonth());
				CreateTrip.from_date = sb.toString();

				sb = new StringBuilder();

				sb.append(CreateTripFrom.from_time.getCurrentHour())
						.append(":")
						.append(CreateTripFrom.from_time.getCurrentMinute())
						.append(":00");
				CreateTrip.from_time = sb.toString();

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

				CreateTrip.vehicle_type = spin_vehicle_type.getSelectedItem()
						.toString();
				CreateTrip.payment_type = spin_payment_type.getSelectedItem()
						.toString();
				CreateTrip.no_passangers = no_pass.getText().toString();

				CreateTrip.from_address = CreateTripFrom.from_address.getText()
						.toString();

				CreateTrip.to_address = CreateTripTo.to_address.getText()
						.toString();
				/*
				 * Toast.makeText( CreateTripExtra.this, CreateTrip.from_city +
				 * ";" + CreateTrip.from_city_id + ";" + CreateTrip.from_country
				 * + ";" + CreateTrip.from_country_id + ";" +
				 * CreateTrip.from_address + ";" + CreateTrip.from_date + ";" +
				 * CreateTrip.from_time + ";" + CreateTrip.to_city + ";" +
				 * CreateTrip.to_city_id + ";" + CreateTrip.to_country + ";" +
				 * CreateTrip.to_country_id + ";" + CreateTrip.to_address + ";"
				 * + CreateTrip.to_date + ";" + CreateTrip.to_time + ";" +
				 * CreateTrip.no_passangers + ";" + CreateTrip.vehicle_type +
				 * ";" + CreateTrip.payment_type, Toast.LENGTH_LONG) .show();
				 */
				String tmp_from_time = CreateTrip.from_date + " "
						+ CreateTrip.from_time;
				String tmp_to_time = CreateTrip.to_date + " "
						+ CreateTrip.to_time;

				String[] params = new String[16];
				params[0] = MainActivity.logged_id;
				params[1] = CreateTrip.from_city;
				params[2] = CreateTrip.from_city_id;
				params[3] = CreateTrip.from_country;
				params[4] = CreateTrip.from_country_id;
				params[5] = CreateTrip.from_address;
				params[6] = tmp_from_time;
				params[7] = CreateTrip.to_city;
				params[8] = CreateTrip.to_city_id;
				params[9] = CreateTrip.to_country;
				params[10] = CreateTrip.to_country_id;
				params[11] = CreateTrip.to_address;
				params[12] = tmp_to_time;
				params[13] = CreateTrip.no_passangers;
				params[14] = CreateTrip.vehicle_type;
				params[15] = CreateTrip.payment_type;

				new createTrip(CreateTripExtra.this).execute(params);

				/*
				 * php script params $user_id = $_POST['user_id']; $status =
				 * 'Active'; $active = 1; $from_city = $_POST['from_city'];
				 * $from_city_id = $_POST['from_city_id']; $from_country =
				 * $_POST['from_country']; $from_country_id =
				 * $_POST['from_country_id']; $from_address =
				 * $_POST['from_address']; $from_time = $_POST['from_time'];
				 * $to_city = $_POST['to_city']; $to_city_id =
				 * $_POST['to_city_id']; $to_country = $_POST['to_country'];
				 * $to_country_id = $_POST['to_country_id']; $to_address =
				 * $_POST['to_address']; $to_time = $_POST['to_time'];
				 * $no_passangers = $_POST['no_passangers']; $vehicle_type =
				 * $_POST['vehicle_type']; $payment_type =
				 * $_POST['payment_type'];
				 */
			}
		});
		no_pass = (EditText) findViewById(R.id.no_passangers);
		no_pass.setText("2");
	}

}
