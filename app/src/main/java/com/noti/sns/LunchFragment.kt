package com.noti.sns

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class LunchFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        HomeCardList.remove_all()
        return inflater!!.inflate(R.layout.frg_menu_lunch,container,false)
    }


}