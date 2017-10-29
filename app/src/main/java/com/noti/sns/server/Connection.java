package com.noti.sns.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Connection {
	public static String[] sendJSON(String url, String data) {
		final String[] s = {};
		Thread t = new Thread(() -> {
			try {
				URL url1 = new URL(url);
				HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
				osw.write(data);
				osw.flush();
				s[0] = conn.getResponseMessage();
			} catch (ProtocolException e) {
				s[0] ="ProtocolException";
			} catch (MalformedURLException e) {
				s[0] ="MalformedURLException";
			} catch (IOException e) {
				s[0] ="IOException";
			}
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return s[0].split(",");
	}

}
