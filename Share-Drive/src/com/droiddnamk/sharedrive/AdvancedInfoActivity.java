package com.droiddnamk.sharedrive;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.droiddnamk.sharedrive.customClasses.Countries;
import com.droiddnamk.sharedrive.webcommunication.GetMessageSender;
import com.droiddnamk.sharedrive.webcommunication.getDetailsInfo;
import com.droiddnamk.sharedrive.webcommunication.getPersonInfo;
import com.droiddnamk.sharedrive.webcommunication.getPersonLocation;
import com.droiddnamk.sharedrive.webcommunication.updateUserDetails;
import com.droiddnamk.sharedrive.webcommunication.uploadPhotoToServer;

public class AdvancedInfoActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {

	protected static final int TAKE_PHOTO_CODE = 0;
	Thread t;
	public static String city_temp;
	public static ImageView userPhoto, buttonUpdate;
	public static EditText phoneNumber, state;
	public static Spinner listCountries, list_city;
	public static String[] cities_desc, cities_id;
	String patekaDoSlikaOdPhone;
	String FormatNaSlika = "jpg";
	Bitmap img;
	TextView goBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.advanced_info_layout);
		initialize();
		getDetails();

	}

	private void getDetails() {
		new getDetailsInfo(this).execute();
	}

	private void initialize() {
		userPhoto = (ImageView) findViewById(R.id.details_imgPhoto);
		userPhoto.setOnClickListener(this);
		phoneNumber = (EditText) findViewById(R.id.details_phoneNumber);
		list_city = (Spinner) findViewById(R.id.details_city);
		// state = (EditText) findViewById(R.id.details_state);
		buttonUpdate = (ImageView) findViewById(R.id.details_Update);
		buttonUpdate.setOnClickListener(this);
		goBack = (TextView) findViewById(R.id.txtGoBack);
		goBack.setOnClickListener(this);

		// spinner configure
		listCountries = (Spinner) findViewById(R.id.details_countries);
		listCountries.setOnItemSelectedListener(this);
		ArrayAdapter<String> adapterCountries = new ArrayAdapter<String>(this,
				R.layout.spinner_item, Countries.countries_list);
		listCountries.setAdapter(adapterCountries);
		listCountries.setSelection(0); // tuka moze avtomacki da detektire
		// end spinner configure
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.details_imgPhoto:
			AlertDialog dialog = new AlertDialog.Builder(this).create();

			dialog.setButton("From Camera",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// KAMERA

							final Intent intent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							intent.putExtra(
									MediaStore.EXTRA_OUTPUT,
									Uri.fromFile(getTempFile(AdvancedInfoActivity.this)));
							startActivityForResult(intent, TAKE_PHOTO_CODE);

						}

					});
			dialog.setButton2("From Tel.", new Dialog.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					// OD TEL
					try {
						/*
						 * Intent intent = new Intent();
						 * intent.setType("image/*");
						 * intent.setAction(Intent.ACTION_GET_CONTENT);
						 * startActivityForResult( Intent.createChooser(intent,
						 * "Choose picture"), 2);
						 */
						Intent intent = new Intent(Intent.ACTION_PICK);
						intent.setType("image/*");
						startActivityForResult(intent, 2);
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), e.getMessage(),
								Toast.LENGTH_LONG).show();
						Log.e(e.getClass().getName(), e.getMessage(), e);
					}

				}
			});

			dialog.show();
			break;

		case R.id.txtGoBack:
			Intent i = new Intent(this, PersonalInfoActivity.class);
			startActivity(i);
			overridePendingTransition(R.anim.slide_in_right,
					R.anim.slide_out_right);
			AdvancedInfoActivity.this.finish();
			break;
		case R.id.details_Update:
			buttonUpdate.setEnabled(false);
			// nema proverki se e optional
			String[] lista = new String[8];
			lista[0] = phoneNumber.getText().toString();
			lista[1] = list_city.getSelectedItem().toString();
			lista[2] = "";// state.getText().toString();
			lista[3] = listCountries.getSelectedItem().toString();
			lista[4] = patekaDoSlikaOdPhone;
			lista[5] = MainActivity.logged_username;
			lista[6] = cities_id[list_city.getSelectedItemPosition()];
			lista[7] = Countries.countries.get(
					listCountries.getSelectedItemPosition()).getId();
			new updateUserDetails(this).execute(lista);
			new getPersonLocation(this).execute();
			break;

		default:
			break;
		}
	}

	private File getTempFile(AdvancedInfoActivity advancedInfoActivity) {

		// ke vrne /sdcard/image.tmp
		final File path = new File(Environment.getExternalStorageDirectory(),
				advancedInfoActivity.getPackageName());
		if (!path.exists()) {
			path.mkdir();
		}
		return new File(path, "image.jpg");

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK && requestCode == 2) {
			Uri selectedImageUri = data.getData();
			String filePath = null;
			try {
				// OD FILE Manager
				String filemanagerstring = selectedImageUri.getPath();

				// MEDIA GALLERY
				String selectedImagePath = getPath(selectedImageUri);

				if (selectedImagePath != null) {
					filePath = selectedImagePath;
				} else if (filemanagerstring != null) {
					filePath = filemanagerstring;
				} else {
					Toast.makeText(getApplicationContext(), "Непозната патека",
							Toast.LENGTH_LONG).show();
					Log.e("Bitmap", "Unknown path");
				}
				Log.e("PATEKA SEA", filePath);
				patekaDoSlikaOdPhone = generateStringForServer();
				int dotposition = filePath.lastIndexOf(".");
				FormatNaSlika = filePath.substring(dotposition + 1,
						filePath.length());
				patekaDoSlikaOdPhone = patekaDoSlikaOdPhone + "."
						+ FormatNaSlika;
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 3;
				img = BitmapFactory.decodeFile(filePath, options);

				new uploadPhotoToServer(this, patekaDoSlikaOdPhone, img)
						.execute();
				Log.e("After splited", patekaDoSlikaOdPhone);

			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Конекциска грешка",
						Toast.LENGTH_LONG).show();
				Log.e(e.getClass().getName(), e.getMessage(), e);
			}
		} else if (requestCode == 0) {
			if (resultCode == RESULT_OK) {

				final File file = getTempFile(this);
				try {
					img = null;
					Bitmap captureBmp = Media.getBitmap(getContentResolver(),
							Uri.fromFile(file));
					// tuka pravi sho sakis so bmp (resize,rename add galery)
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				String pathToImage = file.getAbsolutePath();
				// pathToImage += pictures_path;
				Log.e("DA PATH TO IMAGE ZA CAMERA", pathToImage);
				// pathToImage is a path you need.

				// Ako slikata ne e ovde,
				// mozes da ja zacuvas sam so..
				patekaDoSlikaOdPhone = generateStringForServer();
				int dotposition = pathToImage.lastIndexOf(".");
				FormatNaSlika = pathToImage.substring(dotposition + 1,
						pathToImage.length());
				patekaDoSlikaOdPhone = patekaDoSlikaOdPhone + "."
						+ FormatNaSlika;

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 3;
				img = BitmapFactory.decodeFile(pathToImage, options);

				new uploadPhotoToServer(this, patekaDoSlikaOdPhone, img)
						.execute();
				Log.e("After splited OD CAMERA", patekaDoSlikaOdPhone);

			}
		}

	}

	private String generateStringForServer() {

		Random rand = new Random();
		long r = rand.nextLong();
		r = Math.abs(r);
		return String.valueOf(r);

	}

	private String getPath(Uri selectedImageUri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
				null);
		if (cursor != null) {
			// TUKA KE SE DOBIE NULL AKO
			// AKO SE KORISTE FILE MANAGER
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} else
			return null;
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		switch (arg0.getId()) {
		case R.id.details_countries:
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

	@Override
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
					AdvancedInfoActivity.this, R.layout.spinner_item,
					cities_desc);
			list_city.setAdapter(adapterCities);
			for (int i1 = 0; i1 < AdvancedInfoActivity.list_city.getCount(); i1++) {
				if(city_temp!=null)
				if (city_temp.equals(AdvancedInfoActivity.list_city
						.getItemAtPosition(i1).toString())) {
					AdvancedInfoActivity.list_city.setSelection(i1);
				}
			}
		}

	}
}
