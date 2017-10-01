package com.noti.sns;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.Holder> implements HomeItemTouchHelperCallback.OnItemMoveListener {

    public static NotificationManager notimanager;
    public static NotificationCompat.Builder builder;
    private Context context;
    private List<card_main> list = new ArrayList<>();
    public OnStartDragListener mStartDragListener;

    public interface OnStartDragListener{
        void onStartDrag(Holder holder);
    }

    public MainViewAdapter(Context context, List<card_main> list,OnStartDragListener startDragListener) {
        mStartDragListener = startDragListener;
        this.context = context;
        this.list = list;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_design, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        holder.titleText.setText(list.get(itemposition).title);

        holder.subTitleText.setText(list.get(itemposition).subtitle);

        holder.insideText.setText(list.get(itemposition).inside);

        switch (list.get(itemposition).bar_color){
            case 0:
                holder.bar_noti.setBackgroundColor(Color.rgb(255,85,85));
                break;
            case 1:
                holder.bar_noti.setBackgroundColor(Color.rgb(253, 216, 53));
                break;
            case 2:
                holder.bar_noti.setBackgroundColor(Color.rgb(54, 175, 255));
                break;
        }

        holder.btn_see_more.setOnTouchListener((v, event)->{
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
                mStartDragListener.onStartDrag(holder);
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(list, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    public class Holder extends RecyclerView.ViewHolder{
        public TextView titleText;
        public TextView subTitleText;
        public TextView insideText;
        public TextView btn_pin;
        public TextView btn_see_more;
        public LinearLayout bar_noti;
        public ImageView icon;

        public Holder(View view){
            super(view);
            titleText = (TextView) view.findViewById(R.id.cd_title);
            subTitleText = (TextView) view.findViewById(R.id.cd_subtitle);
            insideText = (TextView) view.findViewById(R.id.cd_inside);
            btn_pin = (TextView) view.findViewById(R.id.cd_pin);
            btn_see_more = (TextView) view.findViewById(R.id.cd_see_more);
            bar_noti = (LinearLayout) view.findViewById(R.id.cd_bar);
            icon = (ImageView) view.findViewById(R.id.cardicon);
        }
    }
}
