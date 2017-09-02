package com.noti.sns

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu.*


class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        var navigation_num : Int = 0

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    navigation_num = 0
                    bottom_navigation.menu.getItem(0).setEnabled(false)
                    bottom_navigation.menu.getItem(1).setEnabled(true)
                    bottom_navigation.menu.getItem(2).setEnabled(true)

                }
                R.id.action_calender -> {
                    navigation_num = 1
                    bottom_navigation.menu.getItem(0).setEnabled(true)
                    bottom_navigation.menu.getItem(1).setEnabled(false)
                    bottom_navigation.menu.getItem(2).setEnabled(true)
                }
                R.id.action_lunch -> {
                    navigation_num = 1
                    bottom_navigation.menu.getItem(0).setEnabled(true)
                    bottom_navigation.menu.getItem(1).setEnabled(true)
                    bottom_navigation.menu.getItem(2).setEnabled(false)
                }
            }
            true
        }

    }

    override fun onBackPressed() {
        Toast.makeText(this,"뒤로가기로 종료 불가",Toast.LENGTH_SHORT).show()
        super.onBackPressed()
    }

}