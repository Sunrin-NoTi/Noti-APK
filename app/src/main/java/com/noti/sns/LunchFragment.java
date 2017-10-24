package com.noti.sns;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import static com.noti.sns.MenuActivity.popup_on;

public class LunchFragment extends Fragment {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.frg_menu_lunch, container, false);

        TabHost tabHost1 = (TabHost) rootView.findViewById(R.id.tabHost1);
        RelativeLayout popup = (RelativeLayout) rootView.findViewById(R.id.timepick_popup);
        ImageView add_btn = (ImageView) rootView.findViewById(R.id.btnAlam);
        TextView cancel = (TextView) rootView.findViewById(R.id.timepick_cancel);
        popup_on = false;

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
                }

            }
        });
        add_btn.setOnTouchListener((view, motionEvent) -> Btn_press.addBTN(motionEvent, add_btn));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popup_on) {
                    popup.setVisibility(View.GONE);
                    popup_on = false;
                    add_btn.setEnabled(true);
                    tabHost1.getTabWidget().getChildTabViewAt(0).setEnabled(true);
                    tabHost1.getTabWidget().getChildTabViewAt(1).setEnabled(true);
                    tabHost1.getTabWidget().getChildTabViewAt(2).setEnabled(true);
                }

            }
        });

        final RadioGroup rg = (RadioGroup) rootView.findViewById(R.id.select_meal_time);

        int id = rg.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) rootView.findViewById(id);


        return rootView;
    }


}
