package com.noti.sns.server;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Connection {
	public static String[] sendJSON(String url, String data) {
		final String[] s = new String[1];
		Thread t = new Thread(() -> {
			try {
				Log.e("1","1");
				URL url1 = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
				conn.setConnectTimeout(3000);
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
				osw.write(data);
				osw.flush();
				s[0] = conn.getContent().toString();
			} catch (ProtocolException e) {
				Log.e("1","2");
				s[0] ="ProtocolException";
			} catch (MalformedURLException e) {
				Log.e("1","3");
				s[0] ="MalformedURLException";
			} catch (IOException e) {
				Log.e("1","4");
				s[0] ="IOException";
			}catch (Exception e){}
		});
		t.start();
		try {
			Log.e("1","5");
			t.join();
		} catch (Exception e) {
			Log.e("1","6");
			e.printStackTrace();
		}
		Log.e("as",s[0]);
		return s[0].split(",");
	}

}
