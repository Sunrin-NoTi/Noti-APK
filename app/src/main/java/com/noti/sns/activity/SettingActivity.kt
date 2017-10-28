package com.noti.sns.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.noti.sns.R
import com.noti.sns.activity.MainActivity.api
import com.noti.sns.activity.MainActivity.edit
import com.noti.sns.schoolparsing.SchoolException
import com.noti.sns.schoolparsing.SchoolSchedule
import com.noti.sns.utility.DateChange.checkYunYear
import com.noti.sns.utility.Listsave
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*
import kotlin.collections.ArrayList


class SettingActivity : AppCompatActivity() {

    var check_down: Boolean = false
    var check_downfail: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        //기본선언
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val today = Date()//오늘 날짜 객체
        var school_Schedule: ArrayList<List<SchoolSchedule>> = ArrayList()//학사일정 받아오기

        check_down = true//초기상태는 다운로드 되어있음

        //동기화 버튼 클릭
        setting_GetSchool.setOnClickListener {
            check_down = false//다운로드 시작
            Toast.makeText(this, "학사일정을 불러옵니다.", Toast.LENGTH_SHORT).show()//토스트로 다운로드 알림
            //다운로드
            Thread {
                try {
                    for (i in 1..12) {
                        school_Schedule.add(api.getMonthlySchedule(today.year + 1900, i))
                        Log.e("ada", i.toString() + "번쨰가 불러와졌어용")
                    }
                    Log.e("ada", "전체가 불러와졌어용")

                    var c = 0
                    for (i in 0..11) {  //0 :1월 11:12월
                        for (j in 0..if (i == 2 || i == 4 || i == 6 || i == 7 || i == 9 || i == 11) {
                            30
                        } else {
                            if (i == 1)
                                if (checkYunYear(today.year + 1901))
                                    28
                                else
                                    27
                            else
                                29
                        })
                            if (school_Schedule[i][j].schedule != "") {
                                edit.putInt("eventm" + c, i)
                                edit.putInt("eventd" + c, j)
                                c++
                            }
                    }
                    edit.putInt("scnum", c)
                    edit.commit()
                    Listsave.SaveSchool.push_Hac(school_Schedule)
                    Log.e("e", "불러와짐")
                    Toast.makeText(this, "다운로드가 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    check_down = true
                } catch (e: SchoolException) {
                    Toast.makeText(this, "다운로드 오류입니다. 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
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

