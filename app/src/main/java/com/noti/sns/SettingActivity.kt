package com.noti.sns

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.noti.sns.HomeFragment.edit
import com.noti.sns.MainActivity.schedule_ForCalender
import kotlinx.android.synthetic.main.activity_setting.*
import org.hyunjun.school.SchoolException
import java.util.*


class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        val today = Date()
        setting_GetSchool.setOnClickListener {
            Thread(Runnable {
                try {
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
                    Log.e("e", "불러와짐")
                } catch (e: SchoolException) {
                    Log.e("e", "안불러와짐")
                }
            }).start()
        }




    }
    fun checkYunYear(p0:Int): Boolean {
        if (((p0%4).equals(0) and !(p0%100).equals(0)) or (p0%400).equals(0)){
            return true
        }
        return false
    }
}

