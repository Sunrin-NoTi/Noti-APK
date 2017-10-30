package com.noti.sns.fcm;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.noti.sns.R;
import com.noti.sns.server.Connection;

import org.json.JSONObject;

public class FirebaseInstanceIdServiceListener extends com.google.firebase.iid.FirebaseInstanceIdService {
	@Override
	public void onTokenRefresh() {
		// Get updated InstanceID token.
		String refreshedToken = FirebaseInstanceId.getInstance().getToken();
		Log.d("e", "Refreshed token: " + refreshedToken);
		SharedPreferences pref;
		pref = getSharedPreferences("save", 0);
		JSONObject jo = new JSONObject();
		try {
			jo.put("id", pref.getString("save_id", ""));
			jo.put("pw", pref.getString("save_pw", ""));
			jo.put("fcm", refreshedToken);
		} catch (Exception ignore) {

		}
		Connection.sendJSON(getString(R.string.url) + "fcm/", jo.toString());
	}

}
