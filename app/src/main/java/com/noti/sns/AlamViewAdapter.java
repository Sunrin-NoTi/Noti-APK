package com.noti.sns;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class AlamViewAdapter extends RecyclerView.Adapter<AlamViewAdapter.Holder> {

    private Context context;
    private List<Alam_main> list = new ArrayList<>();

    public AlamViewAdapter(Context context, List<Alam_main> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alam_design, parent, false);
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
                MealAlamList.put_Alam_List((ArrayList<Alam_main>) list);
                LunchFragment.refresh();
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
