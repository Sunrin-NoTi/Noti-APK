package com.noti.sns.utility

import android.content.SharedPreferences
import com.noti.sns.activity.MainActivity
import com.noti.sns.schoolparsing.SchoolException
import com.noti.sns.schoolparsing.SchoolSchedule
import com.noti.sns.activity.MainActivity.api
import java.util.*

class Util {
    fun getSchedule(today: Date, edit: SharedPreferences.Editor, schedule_ForCalender: ArrayList<List<SchoolSchedule>>): Boolean {
        try {
            var c = 0
            for (i in 0..11) {  //0 :1월 11:12월
                schedule_ForCalender.add(api.getMonthlySchedule(today.year + 1900, i+1))
                for (j in 0..if (i == 2 || i == 4 || i == 6 || i == 7 || i == 9 || i == 11) {
                    30
                } else {
                    if (i == 1)
                    /* 윤년 확인 */
                        if (DateChange.checkYunYear(today.year + 1901))
                            28
                        else
                            27
                    else
                        29
                })
                    if (schedule_ForCalender[i][j].schedule != "") {
                        MainActivity.edit.putInt("eventm" + c, i)
                        MainActivity.edit.putInt("eventd" + c, j)
                        c++
                    }
            }
            //학사일정 갯수 저장
            edit.putInt("scnum", c)
            edit.commit()
            Listsave.SaveSchool.push_Hac(schedule_ForCalender)//학사일정 객체를 저장
            return true
        } catch (e: SchoolException) {
            return false
        }
    }
}