package com.noti.sns;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import org.hyunjun.school.School;
import org.hyunjun.school.SchoolException;
import org.hyunjun.school.SchoolSchedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ngdb on 2017. 10. 6..
 */

public class MainActivity extends AppCompatActivity {

    public static ArrayList<List<SchoolSchedule>> schedule_ForCalender;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Date today = new Date();
        schedule_ForCalender = new ArrayList<>();
        final int[] download_Check = {1};
        new Thread(() -> {
            School api = new School(School.Type.HIGH, School.Region.SEOUL, "B100000658");
            try {
                for (int i = 1; i <= 12;i++){
                    schedule_ForCalender.add(api.getMonthlySchedule(today.getYear()+1900,i));
                }

                download_Check[0] = 2;
            } catch (SchoolException e) {
                download_Check[0] = 3;
            }
        }).start();
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        Intent intent_login = new Intent(this, MenuActivity.class);
        Intent intent_register = new Intent(this, RegisterActivity.class);

        ImageView login_btn = findViewById(R.id.login_btn);
        ImageView register_btn = findViewById(R.id.register_btn);
        ImageView passwd_btn = findViewById(R.id.passwd_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (download_Check[0]==1)
                    Toast.makeText(getApplicationContext(),"다운로드 중입니다!!!",Toast.LENGTH_SHORT).show();
                else if (download_Check[0]==2)
                    startActivity(intent_login);
                else if (download_Check[0]==3)
                    Toast.makeText(getApplicationContext(),"다운로드 오류입니다 앱을 재시작해주세요.",Toast.LENGTH_SHORT).show();
            }
        });
        login_btn.setOnTouchListener((view, motionEvent) -> Btn_press.bigBTN(motionEvent, login_btn));

        register_btn.setOnClickListener(view -> startActivity(intent_register));
        register_btn.setOnTouchListener((view, motionEvent) -> Btn_press.smallBTN(motionEvent, register_btn));

        passwd_btn.setOnClickListener(view -> Log.e("미구현","ㅇㄹㅇ"));
        passwd_btn.setOnTouchListener((view, motionEvent) -> Btn_press.smallBTN(motionEvent, passwd_btn));
    }
}
