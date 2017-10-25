package com.noti.sns;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.noti.sns.MainActivity.edit;
import static com.noti.sns.MainActivity.pref;

public class save_School {
    public static ArrayList<List<SchoolSchedule>> get_Hac(){

        ArrayList<List<SchoolSchedule>> rtn = new ArrayList<>();
        List line = new ArrayList();

        for(int j = 0;j<31;j++){
            line.add(new SchoolSchedule(pref.getString("event_s"+0+"/"+j,"")));
        }
        rtn.add(line);
        line = new ArrayList();

        int feb_Days = 28;
        if(checkYunYear(new Date().getYear()+1901))
            feb_Days = 29;
        for(int j = 0;j<feb_Days;j++){
            line.add(new SchoolSchedule(pref.getString("event_s"+1+"/"+j,"")));
        }
        rtn.add(line);

        for(int i = 2;i<12;i++){
            line = new ArrayList();
            switch (i+1){
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    for(int j = 0;j<31;j++){
                        line.add(new SchoolSchedule(pref.getString("event_s"+i+"/"+j,"")));
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    for(int j = 0;j<30;j++){
                        line.add(new SchoolSchedule(pref.getString("event_s"+i+"/"+j,"")));
                    }
                    break;
            }
            rtn.add(line);
        }
        return rtn;
    }



    public static void push_Hac(ArrayList<List<SchoolSchedule>> p0){

        for(int i = 2;i<12;i++){
            switch (i+1){
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    for(int j = 0;j<31;j++){
                        edit.putString("event_s"+i+"/"+j,p0.get(i).get(j).schedule);
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    for(int j = 0;j<30;j++){
                        edit.putString("event_s"+i+"/"+j,p0.get(i).get(j).schedule);
                    }
                    break;
            }
        }
        for(int j = 0;j<31;j++){
            edit.putString("event_s"+0+"/"+j,p0.get(0).get(j).schedule);
        }
        int feb_Days = 28;
        if(checkYunYear(new Date().getYear()+1901))
            feb_Days = 29;
        for(int j = 0;j<feb_Days;j++){
            edit.putString("event_s"+1+"/"+j,p0.get(1).get(j).schedule);
        }
        edit.commit();
    }

    public static boolean checkYunYear(int p0) {
        if (((p0%4)==0 && !((p0%100) ==0)) || (p0%400==0)){
            return true;
        }
        return false;
    }

}

