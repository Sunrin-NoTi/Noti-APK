package com.noti.sns;

import java.util.ArrayList;

import static com.noti.sns.HomeFragment.edit;
import static com.noti.sns.HomeFragment.pref;

public class MealAlamList {

    public static void add(String p0,int p1,int p2){
        int add_num = pref.getInt("Alam_List_num",0);
        remove(add_num);
        edit.putString("Alam_List_title"+add_num,p0);
        edit.putInt("Alam_List_hour"+add_num,p1);
        edit.putInt("Alam_List_minute"+add_num,p2);
        edit.putInt("Alam_List_num",add_num+1);
        edit.commit();
    }

    public static void remove(int p0){
        edit.remove("Alam_List_title"+p0);
        edit.remove("Alam_List_hour"+p0);
        edit.remove("Alam_List_minute"+p0);
        edit.commit();
    }

    public static void remove_all(){
        ArrayList<Alam_main> list = new ArrayList<>();
        put_Alam_List(list);
    }

    public static ArrayList<Alam_main> get_Alam_List(){
        ArrayList<Alam_main> retun = new ArrayList<>();
        int add_num = pref.getInt("Alam_List_num",0);
        for(int i = 0;i<add_num;i++) {
            retun.add(new Alam_main(pref.getString("Alam_List_title" + i, ""), pref.getInt("Alam_List_hour" + i, 0), pref.getInt("Alam_List_minute" + i, 0)));
        }
        return retun;
    }

    public static void put_Alam_List(ArrayList<Alam_main> p0){
        edit.putInt("Alam_List_num",p0.size());
        for(int i = 0;i<p0.size();i++) {
            remove(i);
            edit.putString("Alam_List_title"+i,p0.get(i).wmeal);
            edit.putInt("Alam_List_hour"+i,p0.get(i).hour);
            edit.putInt("Alam_List_minute"+i,p0.get(i).minute);
        }
        edit.commit();
    }
}
