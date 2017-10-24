package com.noti.sns;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;

public class MenuActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationMenu;
    Boolean touch_ch;
    public static boolean popup_on;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        bottomNavigationMenu = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        touch_ch = true;
        popup_on= false;

        switch_Fragment(0);
        bottomNavigationMenu.setOnNavigationItemSelectedListener(item -> {
            if(touch_ch) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        switch_Fragment(0);
                        break;
                    case R.id.action_calender:
                        switch_Fragment(1);
                        break;
                    case R.id.action_lunch:
                        switch_Fragment(2);
                        break;
                }
                return true;
            }
            return false;
        });
    }

    void switch_Fragment(int p0){

        for(int i = 0; i<3;i++){
            if(i == p0) {
                bottomNavigationMenu.getMenu().getItem(i).setEnabled(false);
            }
            else {
                bottomNavigationMenu.getMenu().getItem(i).setEnabled(true);
            }
        }
        switch (p0){
            case 0:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_menu, new HomeFragment())
                        .commit();
                break;
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_menu, new CalenderFragment())
                        .commit();
                break;
            case 2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_menu, new LunchFragment())
                        .commit();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1);
        RelativeLayout popup = (RelativeLayout) findViewById(R.id.timepick_popup);
        ImageView add_btn = (ImageView) findViewById(R.id.btnAlam);
        if(popup_on) {
            popup.setVisibility(View.GONE);
            popup_on = false;
            add_btn.setEnabled(true);
            tabHost1.getTabWidget().getChildTabViewAt(0).setEnabled(true);
            tabHost1.getTabWidget().getChildTabViewAt(1).setEnabled(true);
            tabHost1.getTabWidget().getChildTabViewAt(2).setEnabled(true);
        }
        else
            super.onBackPressed();
    }
}
