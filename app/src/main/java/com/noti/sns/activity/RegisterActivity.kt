package com.noti.sns.activity

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.noti.sns.R
import com.noti.sns.activity.MainActivity.edit
import com.noti.sns.server.Connection
import com.noti.sns.utility.BtnPress
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //초기선언
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //일정 버전에서 전체화면 지원
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            w.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        }

        //확인 버튼 클릭시 팝업 뒤쪽 못건드리게
        register_confirm.setOnClickListener {
            pin_popup.visibility = View.VISIBLE
            reg_email.isEnabled = false
            reg_name.isEnabled = false
            reg_password.isEnabled = false
            reg_school.isEnabled = false
            sign_up_back.isEnabled = false
            register_confirm.isEnabled = false
        }

        //버튼 클릭 애니메이션
        register_confirm.setOnTouchListener({ _, motionEvent ->
            BtnPress.bigBTN(motionEvent, register_confirm)
        })

        //방 이름 확인 누를때
        pin_btn.setOnClickListener {

        }

        //버튼 클릭 애니메이션
        pin_btn.setOnTouchListener({ _, motionEvent ->
            BtnPress.pinBTN(motionEvent, pin_btn)
        })


    }

    //뒤로가기 키 설정
    override fun onBackPressed() {
        if (pin_popup.visibility == View.VISIBLE) {
            //팝업이 있을때 처리
            pin_popup.visibility = View.GONE
            reg_email.isEnabled = true
            reg_name.isEnabled = true
            reg_password.isEnabled = true
            reg_school.isEnabled = true
            register_confirm.isEnabled = true
        } else {
            super.onBackPressed()
        }
    }

    fun register(email: String, password: String, stnumber: String, name: String, room: String, school: String) {
        var response = arrayOf<String>()
        /*
         * 반환값 실제 값은 각각 response[1] / response[3]으로 접근할 수 있음
         * response,register_failed:nonexistent_room 방 없음
         * response,register_failed:existent_account 이미 있음
         * response,register_success 회원가입 성공 ==> 이메일 인증하라고 하고, 로그인창 띄어줘야함
         */
        val js = JSONObject()
        js.put("email", email)
        js.put("password", password)
        js.put("stnumber", stnumber)
        js.put("name", name)
        js.put("room", room)
        js.put("school", school)
        response = Connection.sendJSON(getString(R.string.url) + "/login/", js.toString())
        if (response[1] == "register_success") {
        } else if (response[1] == "") {

        }
    }
}