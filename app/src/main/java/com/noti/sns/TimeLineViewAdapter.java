package com.noti.sns;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ngdb on 2017. 10. 9..
 */

public class TimeLineViewAdapter extends RecyclerView.Adapter<TimeLineViewAdapter.Holder> {

    private Context context;
    private List<timeline_main> list = new ArrayList<>();

    public TimeLineViewAdapter(Context context, List<timeline_main> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_design, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
    //여기에 세부설정
        holder.titleText_t.setText(list.get(position).title);
        holder.subTitleText_t.setText(list.get(position).subtitle);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView titleText_t;
        public TextView subTitleText_t;
        public ImageView circle_t;

        public Holder(View view){
            super(view);
            titleText_t = (TextView) view.findViewById(R.id.td_title);
            subTitleText_t = (TextView) view.findViewById(R.id.td_subtitle);
            circle_t = (ImageView) view.findViewById(R.id.td_circle);
        }
    }
}
