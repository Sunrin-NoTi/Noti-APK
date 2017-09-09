package com.noti.sns

import android.app.Activity
import android.os.Build
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView


class Btn_press : Activity(){

    fun bigBTN(e : MotionEvent,btn : ImageView): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    btn.setBackgroundResource(R.drawable.btn_design_pressed)
                    btn.elevation = 0F
                    when (btn.id) {
                        R.id.login_btn ->
                            btn.setImageResource(R.drawable.ic_arrow_white_24dp)
                        R.id.register_confirm ->
                            btn.setImageResource(R.drawable.ic_check_black_24dp)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    btn.setBackgroundResource(R.drawable.btn_design)
                    btn.elevation = 5F
                    when (btn.id) {
                        R.id.login_btn ->
                            btn.setImageResource(R.drawable.ic_arrow_forward_24dp)
                        R.id.register_confirm ->
                            btn.setImageResource(R.drawable.ic_check_blue_24dp)
                    }

                }
            }
        }
        return false
    }
    fun smallBTN(e : MotionEvent,btn : ImageView): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    btn.setBackgroundResource(R.drawable.btn_design2_pressed)
                    when (btn.id) {
                        R.id.register_btn ->
                            btn.setImageResource(R.drawable.ic_person_add_blue_24dp)
                        R.id.passwd_btn ->
                            btn.setImageResource(R.drawable.ic_lock_blue_24dp)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    btn.setBackgroundResource(R.drawable.btn_design2)
                    when (btn.id) {
                        R.id.register_btn ->
                            btn.setImageResource(R.drawable.ic_person_add_black_24dp)
                        R.id.passwd_btn ->
                            btn.setImageResource(R.drawable.ic_lock_black_24dp)
                    }

                }
            }
        }
        return false
    }

    fun pinBTN(e : MotionEvent,btn : TextView): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    btn.setBackgroundResource(R.drawable.btn_pin_pressed)

                }
                MotionEvent.ACTION_UP -> {
                    btn.setBackgroundResource(R.drawable.btn_pin)


                }
            }
        }
        return false
    }

}
