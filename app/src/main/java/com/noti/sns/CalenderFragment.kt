package com.noti.sns

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CalenderFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        HomeCardList.add("1번공지","D-20","아이고")
        HomeCardList.add("2번공지","D-10","아이고")
        HomeCardList.add("3번공지","D-30","아이고")
        return inflater!!.inflate(R.layout.frg_menu_calender,container,false)
    }


}