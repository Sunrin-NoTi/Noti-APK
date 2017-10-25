package com.noti.sns

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.noti.sns.MainActivity.edit
import kotlinx.android.synthetic.main.activity_setting.*
import java.util.*
import kotlin.collections.ArrayList


class SettingActivity : AppCompatActivity() {
    var check_down : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val today = Date()
        var schedule_ForCalender : ArrayList<List<SchoolSchedule>> = ArrayList()
        check_down = true
        setting_GetSchool.setOnClickListener {
            check_down = false
            Toast.makeText(this,"학사일정을 불러옵니다.",Toast.LENGTH_SHORT).show()
            Thread {

                val api = School(School.Type.HIGH, School.Region.SEOUL, "B100000658")
                try {
                    for (i in 1..12) {
                        schedule_ForCalender.add(api.getMonthlySchedule(today.year + 1900, i))
                        Log.e("ada", i.toString() + "번쨰가 불러와졌어용")
                    }
                    Log.e("ada", "전체가 불러와졌어용")

                    var c = 0
                    val m_31: List<Int> = listOf(3, 5, 7, 8, 10, 12)
                    val m_30: List<Int> = listOf(4, 6, 9, 11)
                    for (i in 3..12) {
                        if(m_31.contains(i))
                            for (j in 0..30) {
                                if (!schedule_ForCalender[i-1][j].schedule.equals("")) {
                                    edit.putInt("eventm" + c, i)
                                    edit.putInt("eventd" + c, j)
                                    c++
                                }
                            }
                        if(m_30.contains(i))
                            for (j in 0..29) {
                                if (!schedule_ForCalender[i-1][j].schedule.equals("")) {
                                    edit.putInt("eventm" + c, i)
                                    edit.putInt("eventd" + c, j)
                                    c++
                                }
                            }
                    }
                    for (j in 0..30) {
                        if (!schedule_ForCalender[0][j].schedule.equals("")) {
                            edit.putInt("eventm" + c, 1)
                            edit.putInt("eventd" + c, j)
                            c++
                        }
                    }

                    var feb_Days:Int
                    if (checkYunYear(today.year+1901))
                        feb_Days = 28
                    else
                        feb_Days = 27

                    for (j in 0..feb_Days) {
                        if (!schedule_ForCalender[1][j].schedule.equals("")) {
                            edit.putInt("eventm" + c, 2)
                            edit.putInt("eventd" + c, j)
                            c++
                        }
                    }
                    edit.putInt("scnum", c)
                    edit.commit()
                    save_School.push_Hac(schedule_ForCalender)
                    Log.e("e", "불러와짐")

                check_down = true
                } catch (e: SchoolException) {

                }
            }.start()
        }




    }

    override fun onBackPressed() {
        if(check_down){
            super.onBackPressed()
        }
        else{
            Toast.makeText(this,"아직 불러오는 중입니다. 잠시만 기다려주세요.",Toast.LENGTH_SHORT).show()
        }
    }
    fun checkYunYear(p0:Int): Boolean {
        if (((p0%4).equals(0) and !(p0%100).equals(0)) or (p0%400).equals(0)){
            return true
        }
        return false
    }
}

