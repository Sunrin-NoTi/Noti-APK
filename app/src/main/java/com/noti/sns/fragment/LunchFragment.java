package com.noti.sns.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.noti.sns.listitem.AlarmListItem;
import com.noti.sns.R;
import com.noti.sns.schoolparsing.SchoolMenu;
import com.noti.sns.utility.BtnPress;
import com.noti.sns.utility.Listsave;
import com.noti.sns.viewadapter.AlarmViewAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
import static com.noti.sns.activity.MenuActivity.popup_on_alarm;

public class LunchFragment extends Fragment {

	public static ArrayList<AlarmListItem> contacts;
	public static Context context;
	static RecyclerView recyclerView;
	AlarmViewAdapter adapter;
	View rootView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {

		Listsave.HomeCardList.remove_all();
		rootView = inflater.inflate(R.layout.frg_menu_lunch, container, false);
		TabHost tabHost1 = rootView.findViewById(R.id.tabHost1);
		RelativeLayout popup = rootView.findViewById(R.id.timepick_popup);
		ImageView add_btn = rootView.findViewById(R.id.btnAlam);
		TextView cancel = rootView.findViewById(R.id.timepick_cancel);
		TextView ok = rootView.findViewById(R.id.timepick_ok);
		RadioGroup radioGroup = rootView.findViewById(R.id.select_meal_time);
		TimePicker timePicker = rootView.findViewById(R.id.timepicker);
		Date today = new Date();
		popup_on_alarm = false;
		context = getActivity();

		popup.setVisibility(View.VISIBLE);
		popup.setVisibility(View.GONE);
		List<SchoolMenu> ex = Listsave.SaveSchool.get_meal_month(today.getMonth() + 1);

		TextView c1_d = rootView.findViewById(R.id.c1_d);
		TextView c1_i = rootView.findViewById(R.id.c1_i);
		TextView c2_d = rootView.findViewById(R.id.c2_d);
		TextView c2_i = rootView.findViewById(R.id.c2_i);
		TextView c3_d = rootView.findViewById(R.id.c3_d);
		TextView c3_i = rootView.findViewById(R.id.c3_i);

		c1_d.setText(today.getMonth() + 1 + "월 " + today.getDate() + "일");
		c2_d.setText(today.getMonth() + 1 + "월 " + today.getDate() + "일");
		c3_d.setText(today.getMonth() + 1 + "월 " + today.getDate() + "일");
		c1_i.setText(ex.get(today.getDate() - 1).breakfast);
		c2_i.setText(ex.get(today.getDate() - 1).lunch);
		c3_i.setText(ex.get(today.getDate() - 1).dinner);


		tabHost1.setup();
		TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
		ts1.setContent(R.id.content1);
		ts1.setIndicator("조식");
		tabHost1.addTab(ts1);
		TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
		ts2.setContent(R.id.content2);
		ts2.setIndicator("중식");
		tabHost1.addTab(ts2);
		TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3");
		ts3.setContent(R.id.content3);
		ts3.setIndicator("석식");
		tabHost1.addTab(ts3);
		tabHost1.setCurrentTab(1);


		recyclerView = rootView.findViewById(R.id.recyclerView_m);
		contacts = Listsave.MealAlamList.get_Alam_List();
		adapter = new AlarmViewAdapter(getActivity(), contacts);
		recyclerView.setHasFixedSize(true);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		recyclerView.setAdapter(adapter);

		final String[] mealw = {""};


		add_btn.setOnClickListener(view -> {
			if (!popup_on_alarm) {
				popup_on_alarm = true;
				popup.setVisibility(View.VISIBLE);
				add_btn.setEnabled(false);
				tabHost1.getTabWidget().getChildTabViewAt(0).setEnabled(false);
				tabHost1.getTabWidget().getChildTabViewAt(1).setEnabled(false);
				tabHost1.getTabWidget().getChildTabViewAt(2).setEnabled(false);
				radioGroup.check(R.id.rad2);
			}
		});
		add_btn.setOnTouchListener((view, motionEvent) -> BtnPress.addBTN(motionEvent, add_btn));

		ok.setOnClickListener((View view) -> {
			if (popup_on_alarm) {
				mealw[0] = "";
				Log.e("12", String.valueOf(radioGroup.getCheckedRadioButtonId() % 3));

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					switch (radioGroup.getCheckedRadioButtonId() % 3) {
						case 0:

							mealw[0] = "조식";
							break;
						case 1:

							mealw[0] = "중식";
							break;
						case 2:

							mealw[0] = "석식";
							break;
						default:
					}
					if(Alarm_isNaN(radioGroup.getCheckedRadioButtonId() % 3)) {
						Listsave.MealAlamList.add(mealw[0], timePicker.getHour(), timePicker.getMinute());
						refresh();
					}
					else
						Toast.makeText(getActivity(),"이미 " + mealw[0] + " 알람이 있습니다.",Toast.LENGTH_SHORT).show();

				} else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1) {
					switch (radioGroup.getCheckedRadioButtonId() % 3) {
						case 0:
							mealw[0] = "조식";
							break;
						case 1:
							mealw[0] = "중식";
							break;
						case 2:
							mealw[0] = "석식";

							break;
						default:
					}
					if(Alarm_isNaN(radioGroup.getCheckedRadioButtonId() % 3)) {
						Listsave.MealAlamList.add(mealw[0], timePicker.getCurrentHour(), timePicker.getCurrentMinute());
						refresh();
					}else
					Toast.makeText(getActivity(),"이미 " + mealw[0] + " 알람이 있습니다.",Toast.LENGTH_SHORT).show();
				}
				popup.setVisibility(View.GONE);
				popup_on_alarm = false;

				add_btn.setEnabled(true);
				tabHost1.getTabWidget().getChildTabViewAt(0).setEnabled(true);
				tabHost1.getTabWidget().getChildTabViewAt(1).setEnabled(true);
				tabHost1.getTabWidget().getChildTabViewAt(2).setEnabled(true);
			}

		});


		cancel.setOnClickListener(view -> {
			if (popup_on_alarm) {
				popup.setVisibility(View.GONE);
				popup_on_alarm = false;
				add_btn.setEnabled(true);
				tabHost1.getTabWidget().getChildTabViewAt(0).setEnabled(true);
				tabHost1.getTabWidget().getChildTabViewAt(1).setEnabled(true);
				tabHost1.getTabWidget().getChildTabViewAt(2).setEnabled(true);
			}

		});

		final RadioGroup rg = rootView.findViewById(R.id.select_meal_time);

		int id = rg.getCheckedRadioButtonId();
		RadioButton rb = rootView.findViewById(id);


		return rootView;
	}

	public static void refresh() {
		AlarmViewAdapter adapter;
		ArrayList<AlarmListItem> contacts;
		contacts = Listsave.MealAlamList.get_Alam_List();
		adapter = new AlarmViewAdapter(context, contacts);
		recyclerView.setAdapter(adapter);
	}

	@Override
	public void onPause() {
		contacts = Listsave.MealAlamList.get_Alam_List();
		Listsave.MealAlamList.put_Alam_List(contacts);
		super.onPause();
	}

	public static boolean Alarm_isNaN(int p0){
		ArrayList<AlarmListItem> alarmListItems = Listsave.MealAlamList.get_Alam_List();
		String meal = null;
		if(p0==0)
			meal = "조식";

		if(p0==1)
			meal = "중식";

		if(p0==2)
			meal = "석식";
		for (int i = 0;i<alarmListItems.size();i++) {
			if (alarmListItems.get(i).wmeal.equals(meal))
				return false;
		}
		return true;
	}


}
