package com.noti.sns

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        login_btn.setOnClickListener {
            val intent_login = Intent(this, MenuActivity::class.java)
            startActivity(intent_login)
        }

        register_btn.setOnClickListener{
            val intent_register = Intent(this, RegisterActivity::class.java)
            startActivity(intent_register)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}