package com.droiddnamk.sharedrive.webcommunication;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.droiddnamk.sharedrive.AdvancedInfoActivity;

public class uploadPhotoToServer extends AsyncTask<Void, Integer, String> {

	int serverResponseCode = -1;
	String serverResponseMessage = "";
	public byte[] dataToServer;
	Bitmap tmp;
	// Konstanti
	private final int UPLOADING_PHOTO_STATE = 0;
	private final int SERVER_PROC_STATE = 1;

	ProgressDialog dialog;
	Context mContext;
	String patekaDoSlikaOdPhone;


	public uploadPhotoToServer(Context context, String pateka,Bitmap m) {
		mContext = context;
		dialog = new ProgressDialog(mContext);
		patekaDoSlikaOdPhone = pateka;
		tmp = m;

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		return activeNetworkInfo != null;
	}

	protected void onPreExecute() {
		if (isNetworkAvailable()) {
			dialog.setMessage("Uploading picture...");
			dialog.setCancelable(false);
			dialog.show();
		} else {
			Toast.makeText(mContext,
					"Error! There is not internet connection.",
					Toast.LENGTH_LONG).show();
			cancel(true);
			return;
		}

	}



@Override
protected void onPostExecute(String result) {
	super.onPostExecute(result);
	try{
		dialog.dismiss();
		AdvancedInfoActivity.userPhoto.setImageBitmap(tmp);
		
	}catch(Exception err){}
}

	
	// UPLOADING BY BITMAP
	public void executeMultipartPost(Bitmap bm) throws Exception {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bm.compress(CompressFormat.JPEG, 75, bos);
			byte[] data = bos.toByteArray();
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost postRequest = new HttpPost(
					"http://ristokalinikov.mk/sharedrive/uploader.php");
			ByteArrayBody bab = new ByteArrayBody(data, patekaDoSlikaOdPhone);
			// File file= new File("/mnt/sdcard/forest.png");
			// FileBody bin = new FileBody(file);
			MultipartEntity reqEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			reqEntity.addPart("uploadedfile", bab);
			postRequest.setEntity(reqEntity);
			HttpResponse response = httpClient.execute(postRequest);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "UTF-8"));
			String sResponse;
			StringBuilder s = new StringBuilder();

			while ((sResponse = reader.readLine()) != null) {
				s = s.append(sResponse);
			}
			System.out.println("Response: " + s);
		} catch (Exception e) {
			// handle exception here
			Log.e(e.getClass().getName(), e.getMessage());
		}
	}

	@Override
	protected String doInBackground(Void... params) {
		
		try {
			executeMultipartPost(tmp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	// /// uploadirajne na slika!
	// Not used.. this is uploading picture but with path to image.. not wanted this time
	// this time need to send RESIZED (in size also) on server... so can be much faster with CAM photos.
	/*
	public void PostNet(String pateka) {

		HttpURLConnection connection = null;
		DataOutputStream outputStream = null;
		DataInputStream inputStream = null;

		String pathToOurFile = pateka;
		String urlServer = "http://ristokalinikov.mk/sharedrive/uploader.php";
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024;

		try {
			FileInputStream fileInputStream = new FileInputStream(new File(
					pathToOurFile));
			// BitmapFactory.Options o = new BitmapFactory.Options();
			// o.inJustDecodeBounds = true;

			// BitmapFactory.decodeStream(fileInputStream, null, o);

			URL url = new URL(urlServer);
			connection = (HttpURLConnection) url.openConnection();

			// Dozvoleno input i output
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);

			// POST method da
			connection.setRequestMethod("POST");

			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);

			outputStream = new DataOutputStream(connection.getOutputStream());
			outputStream.writeBytes(twoHyphens + boundary + lineEnd);
			outputStream
					.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
							+ patekaDoSlikaOdPhone + "\"" + lineEnd);
			// pathToFile
			Log.e(" FILENAME", pathToOurFile);
			outputStream.writeBytes(lineEnd);

			bytesAvailable = fileInputStream.available();
			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// Citajne file
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			Log.e("DEBUF","PRED WHILE");
			while (bytesRead > 0) {
				outputStream.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}
			Log.e("DEBUF","POSLE WHILE");
			outputStream.writeBytes(lineEnd);
			outputStream.writeBytes(twoHyphens + boundary + twoHyphens
					+ lineEnd);
			Log.e("DEBUF","POSLE outputStream.writeBytes");
			// Odgovor od server
			serverResponseCode = connection.getResponseCode();
			Log.e("DEBUF","POSLE connection.getResponseCode();");
			serverResponseMessage = connection.getResponseMessage();
			Log.e("RESPONSE", serverResponseMessage);

			fileInputStream.close();
			outputStream.flush();
			outputStream.close();
		} catch (Exception ex) {
			Log.e("ERR", serverResponseMessage);

		}

	}
	*/

}