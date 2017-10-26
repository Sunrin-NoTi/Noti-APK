package com.noti.sns.ViewAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.noti.sns.Fragment.FragmentLunch;
import com.noti.sns.ListItem.ListItemAlarm;
import com.noti.sns.R;
import com.noti.sns.Utility.UListsave;

import java.util.ArrayList;
import java.util.List;


public class ViewAdapterAlarm extends RecyclerView.Adapter<ViewAdapterAlarm.Holder> {

    private Context context;
    private List<ListItemAlarm> list = new ArrayList<>();

    public ViewAdapterAlarm(Context context, List<ListItemAlarm> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        //여기에 세부설정

        Log.e(String.valueOf(position), list.get(position).wmeal);
        holder.titleText_a.setText(list.get(position).wmeal);
        String min = list.get(position).minute<10 ? "0"+list.get(position).minute : String.valueOf(list.get(position).minute);
        holder.timeText_a.setText(list.get(position).hour + ":" + min);

        holder.close_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                UListsave.MealAlamList.put_Alam_List((ArrayList<ListItemAlarm>) list);
                FragmentLunch.refresh();
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView titleText_a;
        public TextView timeText_a;
        public ImageView close_a;
        public RelativeLayout bac;


        public Holder(View view) {
            super(view);
            titleText_a = (TextView) view.findViewById(R.id.title_meal);
            timeText_a = (TextView) view.findViewById(R.id.time_meal);
            close_a = (ImageView) view.findViewById(R.id.close_al);
            bac = (RelativeLayout) view.findViewById(R.id.cv_alam);
        }
    }
}
