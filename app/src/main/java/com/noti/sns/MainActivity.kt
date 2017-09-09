package com.noti.sns

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val intent_login = Intent(this, MenuActivity::class.java)
        val intent_register = Intent(this, RegisterActivity::class.java)

        login_btn.setOnClickListener { startActivity(intent_login) }
        login_btn.setOnTouchListener({ view, motionEvent -> Btn_press().bigBTN(motionEvent, login_btn) })

        register_btn.setOnClickListener { startActivity(intent_register) }
        register_btn.setOnTouchListener({ view, motionEvent -> Btn_press().smallBTN(motionEvent, register_btn) })

        passwd_btn.setOnClickListener { }
        passwd_btn.setOnTouchListener({ view, motionEvent -> Btn_press().smallBTN(motionEvent, passwd_btn) })
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }

}