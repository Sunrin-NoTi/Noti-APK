package com.noti.sns;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    public static SharedPreferences pref;
    public static SharedPreferences.Editor edit;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        pref = this.getSharedPreferences("save", 0);
        edit = pref.edit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        School api = new School(School.Type.HIGH, School.Region.SEOUL, "B100000658");
        Date today = new Date();
        new Thread(){
            @Override
            public void run() {
                if(pref.getInt("mealMonth",0) != today.getMonth()+1){

                    try {
                        Log.e("12", api.getMonthlyMenu(today.getYear()+1900,today.getMonth()+1).get(today.getDate()-1).lunch);
                        save_School.put_meal_month(api.getMonthlyMenu(today.getYear()+1900,today.getMonth()+1),today.getMonth()+1);


                    } catch (SchoolException e) {
                        e.printStackTrace();
                    }

                    edit.putInt("mealMonth",today.getMonth()+1);
                    edit.commit();
                }
            }
        }.start();



        Intent intent_login = new Intent(this, MenuActivity.class);
        Intent intent_register = new Intent(this, RegisterActivity.class);

        ImageView login_btn = findViewById(R.id.login_btn);
        ImageView register_btn = findViewById(R.id.register_btn);
        ImageView passwd_btn = findViewById(R.id.passwd_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent_login);
                finish();
            }
        });
        login_btn.setOnTouchListener((view, motionEvent) -> Btn_press.bigBTN(motionEvent, login_btn));



        ArrayList<List<SchoolSchedule>> schedule_ForCalender = new ArrayList();
        final Boolean[] check_down = {true};

        check_down[0] = false;

        Thread t = new Thread() {
            @Override
            public void run() {
                School api = new School(School.Type.HIGH, School.Region.SEOUL, "B100000658");
                for (int i = 1; i <= 12; i++) {
                    try {
                        schedule_ForCalender.add(api.getMonthlySchedule(today.getYear() + 1900, i));
                    } catch (SchoolException e1) {
                        e1.printStackTrace();
                    }
                    Log.e("ada", i + "번쨰가 불러와졌어용");
                }
                Log.e("ada", "전체가 불러와졌어용");

                int c = 0;
                ArrayList<Integer> m_31= new ArrayList<>();
                m_31.add(3);
                m_31.add(5);
                m_31.add(7);
                m_31.add(8);
                m_31.add(10);
                m_31.add(12);
                ArrayList<Integer> m_30 = new ArrayList<>();
                m_30.add(4);
                m_30.add(6);
                m_30.add(9);
                m_30.add(11);
                for (int i = 3; i <= 12; i++) {
                    if (m_31.contains(i))
                        for (int j = 0; j <= 30; j++) {
                            if (!schedule_ForCalender.get(i - 1).get(j).schedule.equals("")) {
                                edit.putInt("eventm" + c, i);
                                edit.putInt("eventd" + c, j);
                                c++;
                            }
                        }
                    if (m_30.contains(i))
                        for (int j = 0; j <= 29; j++) {
                            if (!schedule_ForCalender.get(i - 1).get(j).schedule.equals("")) {
                                edit.putInt("eventm" + c, i);
                                edit.putInt("eventd" + c, j);
                                c++;
                            }
                        }
                }
                for (int j = 0; j <= 30; j++) {
                    if (!schedule_ForCalender.get(0).get(j).schedule.equals("")) {
                        edit.putInt("eventm" + c, 1);
                        edit.putInt("eventd" + c, j);
                        c++;
                    }
                }
                int feb_Days;
                int p0 = today.getYear() + 1901;
                if (((p0 % 4 == 0) && !(p0 % 100 == 0)) || (p0 % 400 == 0))
                    feb_Days = 28;
                else
                    feb_Days = 27;

                for (int j = 0; j <= feb_Days; j++) {
                    if (!schedule_ForCalender.get(1).get(j).schedule.equals("")) {
                        edit.putInt("eventm" + c, 2);
                        edit.putInt("eventd" + c, j);
                        c++;
                    }
                }
                edit.putInt("scnum", c);
                edit.commit();
                save_School.push_Hac(schedule_ForCalender);
                Log.e("e", "불러와짐");

                check_down[0] = true;
            }


        };


        if (pref.getBoolean("first", true)) {
            Toast.makeText(this,"초기 다운로드를 진행하겠습니다.",Toast.LENGTH_SHORT).show();
            t.start();
            edit.putBoolean("first",false);
            edit.commit();

        }
        else{
            check_down[0] = true;
        }


        register_btn.setOnClickListener(view -> {
                if(check_down[0])
                    startActivity(intent_register);
                else
                    Toast.makeText(this,"초기 다운로드 중입니다!",Toast.LENGTH_SHORT).show();
        });
        register_btn.setOnTouchListener((view, motionEvent) -> Btn_press.smallBTN(motionEvent, register_btn));

        passwd_btn.setOnClickListener(view -> Log.e("미구현", "ㅇㄹㅇ"));
        passwd_btn.setOnTouchListener((view, motionEvent) -> Btn_press.smallBTN(motionEvent, passwd_btn));
    }
}
