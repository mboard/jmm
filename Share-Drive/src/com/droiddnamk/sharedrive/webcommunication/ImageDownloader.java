package com.droiddnamk.sharedrive.webcommunication;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

	private String url;
	private final WeakReference<ImageView> imageViewReference;

	public ImageDownloader(ImageView imageView) {
		imageViewReference = new WeakReference<ImageView>(imageView);
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		url = params[0];
		try {
			return getBitmapFromURL(url);
		} catch (Exception err) {
		}

		return null;

	}

	@Override
	protected void onPostExecute(Bitmap result) {
		ImageView imageView = imageViewReference.get();
		if (imageView != null) {
			imageView.setImageBitmap(result);
		}
		imageViewReference.clear();
		imageView.refreshDrawableState();
	}
	// /// Metod za vragajne na sliki od net
	public Bitmap getBitmapFromURL(String src) {
		try {
			Log.e("src", src);
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			// /tuka decode na slika vo pomalecuk kvalitet!
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 3;
			Bitmap myBitmap = BitmapFactory.decodeStream(
					new FlushedInputStream(input), null, options);
			Log.e("Bitmap", "returned");
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("getBitmapFromURL", e.getMessage());
			return null;
		}
	}

	static class FlushedInputStream extends FilterInputStream {
		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
		}

		public long skip(long n) throws IOException {
			long totalBytesSkipped = 0L;
			while (totalBytesSkipped < n) {
				long bytesSkipped = in.skip(n - totalBytesSkipped);
				if (bytesSkipped == 0L) {
					int byteValue = read();
					if (byteValue < 0) {
						break;
					} else {
						bytesSkipped = 1;
					}
				}
				totalBytesSkipped += bytesSkipped;
			}
			return totalBytesSkipped;
		}
	}

}