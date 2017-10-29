package com.noti.sns.fcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		Map<String,String> data = remoteMessage.getData();
		if (data.size() > 0) {
			if(data.containsKey("type")) {
				if (data.get("type").equals("B")) { //아침 알람

				} else if (data.get("type").equals("L")) { //점심 알람

				} else if (data.get("type").equals("D")) { //저녁 알람

				}
			}else if(data.containsKey("month")){
				int month =  Integer.valueOf(data.get("month"));
				int date =  Integer.valueOf(data.get("date"));
				String title = remoteMessage.getNotification().getTitle();
				String msg = remoteMessage.getNotification().getBody();
			}
		}

	}
}
