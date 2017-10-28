package com.noti.sns.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.noti.sns.R
import com.noti.sns.activity.MainActivity.api
import com.noti.sns.activity.MainActivity.edit
import com.noti.sns.schoolparsing.SchoolSchedule
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*
import kotlin.collections.ArrayList


class SettingActivity : AppCompatActivity() {

    var check_down: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val schoolSchedule: ArrayList<List<SchoolSchedule>> = ArrayList()//학사일정 받아오기

        check_down = true//초기상태는 다운로드 되어있음

        //동기화 버튼 클릭
        setting_GetSchool.setOnClickListener {
            check_down = false//다운로드 시작
            Toast.makeText(this, "학사일정을 불러옵니다.", Toast.LENGTH_SHORT).show()//토스트로 다운로드 알림
            //다운로드
            Thread {
                if (MainActivity.u.getSchedule(Date(), edit, schoolSchedule)) {
                    check_down = true
                    Toast.makeText(this, "다운로드가 완료되었습니다.", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(this, "다운로드 오류입니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
            }.start()
        }


    }

    //뒤로가기 설정
    override fun onBackPressed() {
        if (check_down)
            super.onBackPressed()
        else
        //다운로드 완료 전 뒤로가기 불가능
            Toast.makeText(this, "아직 불러오는 중입니다. 잠시만 기다려주세요.", Toast.LENGTH_SHORT).show()
    }

}