package com.noti.sns;

import java.util.ArrayList;

import static com.noti.sns.MainActivity.edit;
import static com.noti.sns.MainActivity.pref;

public class HomeCardList {

    public static void add(String p0,String p1,String p2){
        int add_num = pref.getInt("Home_List_num",0);
        remove(add_num);
        edit.putString("Home_List_title"+add_num,p0);
        edit.putString("Home_List_subtitle"+add_num,p1);
        edit.putString("Home_List_inside"+add_num,p2);
        edit.putInt("Home_List_num",add_num+1);
        edit.commit();
    }

    public static void remove(int p0){
        edit.remove("Home_List_title"+p0);
        edit.remove("Home_List_subtitle"+p0);
        edit.remove("Home_List_inside"+p0);
        edit.commit();
    }

    public static void remove_all(){
        ArrayList<card_main> list = new ArrayList<>();
        put_Home_List(list);
    }

    public static ArrayList<card_main> get_Home_List(){
        ArrayList<card_main> retun = new ArrayList<>();
        int add_num = pref.getInt("Home_List_num",0);
        for(int i = 0;i<add_num;i++) {
            retun.add(new card_main(pref.getString("Home_List_title" + i, ""), pref.getString("Home_List_subtitle" + i, ""), pref.getString("Home_List_inside" + i, "")));
        }
        return retun;
    }

    public static void put_Home_List(ArrayList<card_main> p0){
        edit.putInt("Home_List_num",p0.size());
        for(int i = 0;i<p0.size();i++) {
            remove(i);
            edit.putString("Home_List_title"+i,p0.get(i).title);
            edit.putString("Home_List_subtitle"+i,p0.get(i).subtitle);
            edit.putString("Home_List_inside"+i,p0.get(i).inside);
        }
        edit.commit();
    }
}
