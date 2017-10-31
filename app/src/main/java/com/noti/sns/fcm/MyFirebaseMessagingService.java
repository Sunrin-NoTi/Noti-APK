package com.noti.sns.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.noti.sns.R;
import com.noti.sns.activity.MainActivity;
import com.noti.sns.schoolparsing.SchoolMenu;
import com.noti.sns.utility.Listsave;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
	@Override
	public void onMessageReceived(RemoteMessage remoteMessage) {
		Date today = new Date();
		Map<String, String> data = remoteMessage.getData();

		Log.e("e", remoteMessage.getNotification().getBody());
		if (data.size() > 0) {
			if (data.containsKey("type")) {
				Log.e("e", data.get("type"));
				NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				Intent intent = new Intent(this, MainActivity.class);
				PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				Notification.Builder builder;
				String meal[] = new String[0];
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					NotificationChannel notificationChannel = new NotificationChannel("my_notification_channel", "My Notifications", NotificationManager.IMPORTANCE_DEFAULT);
					notificationChannel.setDescription("Channel description");
					notificationChannel.enableLights(true);
					notificationChannel.setLightColor(Color.RED);
					notificationManager.createNotificationChannel(notificationChannel);
					builder = new Notification.Builder(this, "my_notification_channel");
				} else {
					builder = new Notification.Builder(this);
				}
				List<SchoolMenu> ex = Listsave.SaveSchool.get_meal_month(today.getMonth() + 1);

				builder.setSmallIcon(android.R.drawable.star_on);
				builder.setContentText("식단");
				builder.setWhen(System.currentTimeMillis());
				builder.setSmallIcon(R.mipmap.ic_launcher);
				builder.setContentIntent(pendingIntent);
				builder.setAutoCancel(true);
				if (data.get("type").equals("조식")) { //아침 알람

					builder.setContentTitle("조식 알림");
					meal = ex.get(today.getDate() - 1).lunch.split("dses\n");

				} else if (data.get("type").equals("중식")) { //점심 알람

					builder.setContentTitle("중식 알림");
					meal = ex.get(today.getDate() - 1).lunch.split("\n");

				} else if (data.get("type").equals("석식")) { //저녁 알람
					builder.setContentTitle("석식 알림");
					meal = ex.get(today.getDate() - 1).lunch.split("\n");

				}
				Notification.InboxStyle inboxStyle = null;
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
					inboxStyle = new Notification.InboxStyle(builder);

					for (int i = 0; i < meal.length; i++)
						inboxStyle.addLine(meal[i]);
					inboxStyle.setSummaryText("전체 보기");
					builder.setStyle(inboxStyle);
					notificationManager.notify(0, builder.build());
				}


			} else if (data.containsKey("month")) {// 일정알람
				String year = data.get("year"); // 년
				String month = data.get("month").length() < 10 ? "0" + data.get("month") : data.get("month"); // 월
				String date = data.get("date").length() < 10 ? "0" + data.get("date") : data.get("date"); // 월 // 일
				String title = remoteMessage.getNotification().getTitle(); //제목
				String msg = remoteMessage.getNotification().getBody(); //메세지
				Listsave.HomeCardList.add(title, year + "." + month + "." + date + ".", msg);
			}
		}
	}
	private void sendNotification(){

	}
}
