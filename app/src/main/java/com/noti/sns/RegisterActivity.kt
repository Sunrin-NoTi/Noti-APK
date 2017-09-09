package com.noti.sns

import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager



        register_confirm.setOnClickListener{

            pin_popup.visibility = View.VISIBLE
            reg_class.isEnabled = false
            reg_email.isEnabled = false
            reg_grade.isEnabled = false
            reg_name.isEnabled = false
            reg_password.isEnabled = false
            reg_school.isEnabled = false
            sign_up_back.isEnabled = false
            register_confirm.isEnabled = false

            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

        }

        register_confirm.setOnTouchListener({ view, motionEvent ->
            Btn_press().bigBTN(motionEvent,register_confirm)
        })

        pin_btn.setOnClickListener{

        }

        pin_btn.setOnTouchListener({ view, motionEvent ->
            Btn_press().pinBTN(motionEvent,pin_btn)
        })



        pinEdit1.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (pinEdit1.length() === 0) {
                    clean_text()
                }
                if (pinEdit1.length() === 1) {
                    pinEdit2.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })
        pinEdit2.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (pinEdit2.length() === 0) {
                    pinEdit1.requestFocus()
                    clean_text()
                }
                if (pinEdit2.length() === 1) {
                    pinEdit3.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })
        pinEdit3.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (pinEdit3.length() === 0) {
                    pinEdit1.requestFocus()
                    clean_text()
                }
                if (pinEdit3.length() === 1) {
                    pinEdit4.requestFocus()
                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })
        pinEdit4.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (pinEdit4.length() === 0) {
                    pinEdit1.requestFocus()
                    clean_text()
                }
                if (pinEdit4.length() === 1) {
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)

                }
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        })


    }

    override fun onBackPressed() {
        if(pin_popup.visibility==View.VISIBLE){
            pin_popup.visibility = View.INVISIBLE
            reg_class.isEnabled = true
            reg_email.isEnabled = true
            reg_grade.isEnabled = true
            reg_name.isEnabled = true
            reg_password.isEnabled = true
            reg_school.isEnabled = true
            register_confirm.isEnabled = true
        }
        else {
            super.onBackPressed()
        }
    }

    fun clean_text() {
        if(pinEdit1.length() !== 0) {
            pinEdit1.setText("")
        }
        if(pinEdit2.length() !== 0) {
            pinEdit2.setText("")
        }
        if(pinEdit3.length() !== 0) {
            pinEdit3.setText("")
        }
        if(pinEdit4.length() !== 0) {
            pinEdit4.setText("")
        }
    }

}