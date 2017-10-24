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

/**
 * Created by ngdb on 2017. 10. 6..
 */

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {



        
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
                startActivity(intent_login);
                finish();
            }
        });
        login_btn.setOnTouchListener((view, motionEvent) -> Btn_press.bigBTN(motionEvent, login_btn));

        register_btn.setOnClickListener(view -> startActivity(intent_register));
        register_btn.setOnTouchListener((view, motionEvent) -> Btn_press.smallBTN(motionEvent, register_btn));

        passwd_btn.setOnClickListener(view -> Log.e("미구현","ㅇㄹㅇ"));
        passwd_btn.setOnTouchListener((view, motionEvent) -> Btn_press.smallBTN(motionEvent, passwd_btn));
    }
}
