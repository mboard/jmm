package com.droiddnamk.sharedrive.webcommunication;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class GetMessageSender {
	// public static final String
	public String sendMessage(String sync) {
		String result = "";
		HttpGet get = new HttpGet(sync);
		HttpClient client = new DefaultHttpClient();
		try {
			HttpResponse response = client.execute(get);
			Scanner scanner = new Scanner(response.getEntity().getContent());
			while (scanner.hasNextLine()) {
				result += scanner.nextLine();
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("A", result);
		return result;
	}
}
