package com.noti.sns.fcm;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.noti.sns.R;
import com.noti.sns.server.Connection;

public class FirebaseInstanceIdServiceListener extends com.google.firebase.iid.FirebaseInstanceIdService {
	@Override
	public void onTokenRefresh() {
		// Get updated InstanceID token.
		String refreshedToken = FirebaseInstanceId.getInstance().getToken();
		Log.d("e", "Refreshed token: " + refreshedToken);
		SharedPreferences pref;
		pref = getSharedPreferences("save", 0);
		Connection.sendJSON(getString(R.string.url), "{\"token\":\"" + pref.getString("token", "") + "\"{\"fcm\":\"" + refreshedToken + "\" }");
	}

}
