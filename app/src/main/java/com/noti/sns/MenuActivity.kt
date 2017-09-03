package com.noti.sns

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu.*
import android.R.id.button2
import android.app.Fragment
import android.os.Build
import android.support.annotation.RequiresApi


class MenuActivity : AppCompatActivity() {


    fun switch_Fragment(num : Int){

        var fragmentTransaction = fragmentManager.beginTransaction()

        bottom_navigation.menu.getItem(0).setEnabled(true)
        bottom_navigation.menu.getItem(1).setEnabled(true)
        bottom_navigation.menu.getItem(2).setEnabled(true)
        bottom_navigation.menu.getItem(num).setEnabled(false)

        fragmentTransaction =getFragmentManager().beginTransaction()

        fragmentTransaction.addToBackStack(null)
        when (num){
            0 -> {
                fragmentTransaction.replace(R.id.fragment_menu, HomeFragment())
            }
            1 -> {
                fragmentTransaction.replace(R.id.fragment_menu, CalenderFragment())
            }
            2 -> {
                fragmentTransaction.replace(R.id.fragment_menu, LunchFragment())
            }
        }
        fragmentTransaction.commit()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        switch_Fragment(0)


        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    switch_Fragment(0)
                }
                R.id.action_calender -> {
                    switch_Fragment(1)
                }
                R.id.action_lunch -> {
                    switch_Fragment(2)
                }
            }
            true
        }

    }

    override fun onBackPressed() {
        finish()
    }

}