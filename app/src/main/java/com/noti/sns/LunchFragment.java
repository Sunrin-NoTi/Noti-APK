package com.noti.sns;


import android.annotation.SuppressLint;
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

import java.util.ArrayList;

import static com.noti.sns.MenuActivity.popup_on;

public class LunchFragment extends Fragment {

    private static RecyclerView recyclerView;
    public static Context context;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private AlamViewAdapter adapter;

    public static ArrayList<Alam_main> contacts;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.frg_menu_lunch, container, false);

        TabHost tabHost1 = (TabHost) rootView.findViewById(R.id.tabHost1);
        RelativeLayout popup = (RelativeLayout) rootView.findViewById(R.id.timepick_popup);
        ImageView add_btn = (ImageView) rootView.findViewById(R.id.btnAlam);
        TextView cancel = (TextView) rootView.findViewById(R.id.timepick_cancel);
        TextView ok = (TextView) rootView.findViewById(R.id.timepick_ok);
        RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.select_meal_time);
        TimePicker timePicker = (TimePicker) rootView.findViewById(R.id.timepicker);
        popup_on = false;
        context = getActivity();

        popup.setVisibility(View.VISIBLE);
        popup.setVisibility(View.GONE);

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


        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView_m);
        contacts = MealAlamList.get_Alam_List();
        adapter = new AlamViewAdapter(getActivity(), contacts);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        final String[] mealw = {""};



        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!popup_on) {
                    popup_on = true;
                    popup.setVisibility(View.VISIBLE);
                    add_btn.setEnabled(false);
                    tabHost1.getTabWidget().getChildTabViewAt(0).setEnabled(false);
                    tabHost1.getTabWidget().getChildTabViewAt(1).setEnabled(false);
                    tabHost1.getTabWidget().getChildTabViewAt(2).setEnabled(false);
                    radioGroup.check(R.id.rad2);
                }

            }
        });
        add_btn.setOnTouchListener((view, motionEvent) -> Btn_press.addBTN(motionEvent, add_btn));

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popup_on) {
                    mealw[0] = "";
                    Log.e("12", String.valueOf(radioGroup.getCheckedRadioButtonId()%3));
                    switch (radioGroup.getCheckedRadioButtonId()%3){
                        case 2:
                            mealw[0] = "조식";
                            break;
                        case 0:
                            mealw[0] = "중식";
                            break;
                        case 1:
                            mealw[0] = "석식";
                            break;
                        default:
                    }
                    Log.e("1", mealw[0]);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        MealAlamList.add(mealw[0],timePicker.getHour(),timePicker.getMinute());
                    }
                    else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP_MR1){
                        MealAlamList.add(mealw[0],timePicker.getCurrentHour(),timePicker.getCurrentMinute());
                    }
                    popup.setVisibility(View.GONE);
                    popup_on = false;




                    add_btn.setEnabled(true);
                    tabHost1.getTabWidget().getChildTabViewAt(0).setEnabled(true);
                    tabHost1.getTabWidget().getChildTabViewAt(1).setEnabled(true);
                    tabHost1.getTabWidget().getChildTabViewAt(2).setEnabled(true);
                    refresh();
                }

            }
        });
        cancel.setOnClickListener(view -> {
            if (popup_on) {
                popup.setVisibility(View.GONE);
                popup_on = false;
                add_btn.setEnabled(true);
                tabHost1.getTabWidget().getChildTabViewAt(0).setEnabled(true);
                tabHost1.getTabWidget().getChildTabViewAt(1).setEnabled(true);
                tabHost1.getTabWidget().getChildTabViewAt(2).setEnabled(true);
            }

        });

        final RadioGroup rg = (RadioGroup) rootView.findViewById(R.id.select_meal_time);

        int id = rg.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) rootView.findViewById(id);


        return rootView;
    }

    public static void refresh(){
        AlamViewAdapter adapter;
        ArrayList<Alam_main> contacts;
        contacts = MealAlamList.get_Alam_List();
        adapter = new AlamViewAdapter(context, contacts);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onPause() {
        MealAlamList.put_Alam_List(contacts);
        super.onPause();
    }
}