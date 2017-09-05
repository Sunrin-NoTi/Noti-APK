package com.noti.sns

import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        login_btn.setOnClickListener {
            val intent_login = Intent(this, MenuActivity::class.java)
            startActivity(intent_login)
        }
        login_btn.setOnTouchListener({ view, motionEvent ->
            Btn_press().bigBTN(motionEvent,login_btn)
        })

        register_btn.setOnClickListener{
            val intent_register = Intent(this, RegisterActivity::class.java)
            startActivity(intent_register)
        }
        register_btn.setOnTouchListener({ view, motionEvent ->
            Btn_press().smallBTN(motionEvent,register_btn)
        })

        passwd_btn.setOnClickListener {

        }
        passwd_btn.setOnTouchListener({ view, motionEvent ->
            Btn_press().smallBTN(motionEvent,passwd_btn)
        })
    }



    override fun onBackPressed() {
        super.onBackPressed()
    }

}