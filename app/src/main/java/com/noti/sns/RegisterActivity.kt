package com.noti.sns

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_confirm.setOnClickListener{

        }
        register_confirm.setOnTouchListener({ view, motionEvent ->
            Btn_press().bigBTN(motionEvent,register_confirm)
        })


    }

}