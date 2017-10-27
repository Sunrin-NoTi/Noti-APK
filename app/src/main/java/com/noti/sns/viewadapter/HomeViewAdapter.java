package com.noti.sns.viewadapter;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noti.sns.R;
import com.noti.sns.activity.MainActivity;
import com.noti.sns.listitem.HomeCardListItem;
import com.noti.sns.utility.Listsave;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class HomeViewAdapter extends RecyclerView.Adapter<HomeViewAdapter.Holder> implements HomeItemTouchHelperView.OnItemMoveListener {

    public static NotificationManager notimanager;
    public static NotificationCompat.Builder builder;
    private Context context;
    private ArrayList<HomeCardListItem> list = new ArrayList<>();
    public OnStartDragListener mStartDragListener;

    public interface OnStartDragListener {
        void onStartDrag(Holder holder);
    }

    public HomeViewAdapter(Context context, ArrayList<HomeCardListItem> list, OnStartDragListener startDragListener) {
        mStartDragListener = startDragListener;
        this.context = context;
        this.list = list;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        Date today = new Date();

        holder.titleText.setText(list.get(itemposition).title);

        holder.subTitleText.setText(list.get(itemposition).subtitle);

        holder.insideText.setText(list.get(itemposition).inside);

//        if (Integer.parseInt(list.get(itemposition).subtitle.split("-")[1]) <= 10 && Integer.parseInt(list.get(itemposition).subtitle.split("-")[1]) >= 0)
//            holder.bar_noti.setBackgroundColor(Color.rgb(255, 85, 85));
//        else if (Integer.parseInt(list.get(itemposition).subtitle.split("-")[1]) <= 20 && Integer.parseInt(list.get(itemposition).subtitle.split("-")[1]) > 10)
//            holder.bar_noti.setBackgroundColor(Color.rgb(253, 216, 53));
//        else
//            holder.bar_noti.setBackgroundColor(Color.rgb(54, 175, 255));

        holder.icon.setOnTouchListener((v, event) -> {
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                mStartDragListener.onStartDrag(holder);
            }
            return false;
        });


        Intent go_main = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, go_main, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        holder.btn_pin.setOnClickListener(view -> {
            Log.e("adfa","asdfasdfads");
            builder.setContentTitle(list.get(itemposition).title)
                    .setContentText(list.get(itemposition).subtitle+"까지")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setOngoing(true);

            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(position, builder.build());
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
        list = Listsave.HomeCardList.get_Home_List();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView titleText;
        public TextView subTitleText;
        public TextView insideText;
        public TextView btn_pin;
        public TextView btn_see_more;
        public LinearLayout bar_noti;
        public ImageView icon;

        public Holder(View view) {
            super(view);
            titleText = view.findViewById(R.id.cd_title);
            subTitleText = view.findViewById(R.id.cd_subtitle);
            insideText = view.findViewById(R.id.cd_inside);
            btn_pin = view.findViewById(R.id.cd_pin);
            btn_see_more = view.findViewById(R.id.cd_see_more);
            bar_noti = view.findViewById(R.id.cd_bar);
            icon = view.findViewById(R.id.cd_icon);
        }
    }
}
