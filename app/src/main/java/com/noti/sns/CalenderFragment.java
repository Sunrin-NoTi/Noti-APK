package com.noti.sns;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.noti.sns.HomeFragment.pref;
import static com.noti.sns.MainActivity.schedule_ForCalender;

public class CalenderFragment extends Fragment {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private RecyclerView recyclerView_t;
    private TimeLineViewAdapter adapter_t;
    ArrayList<timeline_main> contacts_t;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.frg_menu_calender, container, false);
        final CompactCalendarView compactCalendarView = rootView.findViewById(R.id.compactcalendar_view);
        TextView cal_month = rootView.findViewById(R.id.cal_month);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setFirstDayOfWeek(Calendar.SUNDAY);
        cal_month.setText(Date_change.intToStirng_Month(new Date(System.currentTimeMillis()).getMonth()));

        contacts_t = new ArrayList<>();
        Date today = new Date();

        contacts_t = make_contact(today.getYear(),today.getMonth(),today.getDate());


        recyclerView_t = (RecyclerView) rootView.findViewById(R.id.recyclerView_t);
        recyclerView_t.setHasFixedSize(true);




        adapter_t = new TimeLineViewAdapter(getActivity(), contacts_t);
        recyclerView_t.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView_t.setAdapter(adapter_t);




        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                contacts_t = new ArrayList<>();
                contacts_t = make_contact(dateClicked.getYear(),dateClicked.getMonth(),dateClicked.getDate());

                adapter_t = new TimeLineViewAdapter(getActivity(), contacts_t);
                recyclerView_t.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView_t.setAdapter(adapter_t);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                cal_month.setText(Date_change.intToStirng_Month(firstDayOfNewMonth.getMonth()));

            }
        });

        for(int i=0;i<pref.getInt("scnum",0)+1;i++){
            try {
                long long1 = 0;
                if(pref.getInt("eventm"+i,0)>=3)
                    long1 = Long.parseLong(String.valueOf(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse((pref.getInt("eventm"+i,0))+ "/"+(pref.getInt("eventd"+i,0)+1)+"/2017 01:00:00").getTime()),10);
                else
                    long1 = Long.parseLong(String.valueOf(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse((pref.getInt("eventm"+i,0))+ "/"+(pref.getInt("eventd"+i,0)+1)+"/2018 01:00:00").getTime()),10);

                compactCalendarView.addEvent(new Event(Color.parseColor("#36afff"),long1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Intent intent_settitng = new Intent(getActivity(),SettingActivity.class);

        ImageView goToSetting_Home = (ImageView) rootView.findViewById(R.id.goToSetting_Calender);
        goToSetting_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent_settitng);
            }
        });
        return rootView;

    }
    ArrayList<timeline_main> make_contact(int year,int month,int dayOfMonth){
        ArrayList<timeline_main> contacts_t = new ArrayList<>();
        if(schedule_ForCalender.get(month).get(dayOfMonth-1).schedule.equals("")||((month>=2&&year!=new Date().getYear())||(month<2&&year!=new Date().getYear()+1)))
            contacts_t.add(new timeline_main(year+1900+"년 "+(month+1)+"월 "+dayOfMonth+"일","이 날은 학사일정이 없습니다."));
        if(year<new Date().getYear()){
            year = new Date().getYear();
            month = 0;
            dayOfMonth = 1;
        }
        for(int i=0;i<pref.getInt("scnum",0)+1;i++){
            if (new Date().getYear()==year) {
                if (pref.getInt("eventm"+i,0)>2&&(pref.getInt("eventm" + i, 0) > month+1 || (pref.getInt("eventm" + i, 0) == month+1 && pref.getInt("eventd" + i, 0) >= dayOfMonth-1))) {
                    contacts_t.add(new timeline_main(year + 1900 + "년 " + pref.getInt("eventm" + i, 0) + "월 " + (pref.getInt("eventd" + i, 0)+1) + "일", schedule_ForCalender.get(pref.getInt("eventm" + i, 0)-1).get(pref.getInt("eventd" + i, 0)).schedule));
                }
                else if (pref.getInt("eventm" + i, 0) !=0&&pref.getInt("eventm" + i, 0) <= 2) {
                    contacts_t.add(new timeline_main(year + 1901 + "년 " + pref.getInt("eventm" + i, 0) + "월 " + (pref.getInt("eventd" + i, 0)+1) + "일", schedule_ForCalender.get(pref.getInt("eventm" + i, 0)-1).get(pref.getInt("eventd" + i, 0)).schedule));
                }
            }
            else if(new Date().getYear()+1==year){
                if (pref.getInt("eventm" + i, 0)<=2&&(pref.getInt("eventm" + i, 0) > month+1 || (pref.getInt("eventm" + i, 0) == month+1 && pref.getInt("eventd" + i, 0) >= dayOfMonth-1))) {
                    contacts_t.add(new timeline_main(year + 1901 + "년 " + pref.getInt("eventm" + i, 0) + "월 " + (pref.getInt("eventd" + i, 0)+1) + "일", schedule_ForCalender.get(pref.getInt("eventm" + i, 0)-1).get(pref.getInt("eventd" + i, 0)).schedule));
                }
            }

        }

        return contacts_t;
    }


}
