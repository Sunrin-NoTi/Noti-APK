package com.noti.sns.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.noti.sns.activity.SettingActivity;
import com.noti.sns.listitem.HomeCardListItem;
import com.noti.sns.R;
import com.noti.sns.utility.Listsave;
import com.noti.sns.viewadapter.HomeViewAdapter;
import com.noti.sns.viewadapter.HomeItemTouchHelperView;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeViewAdapter.OnStartDragListener {

    public static ItemTouchHelper mItemTouchHelper;
    static RecyclerView recyclerView;
    static HomeViewAdapter adapter;
    static Context context;
    static ArrayList<HomeCardListItem> contacts;
    static HomeFragment homeFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.frg_nemu_home, container, false);
        Intent intent_settitng = new Intent(getActivity(), SettingActivity.class);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        contacts = Listsave.HomeCardList.get_Home_List();
        adapter = new HomeViewAdapter(getActivity(), contacts, this);
        HomeItemTouchHelperView mCallback = new HomeItemTouchHelperView(adapter);
        mItemTouchHelper = new ItemTouchHelper(mCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        homeFragment = this;

        context = getActivity();

        ImageView goToSetting_Home = rootView.findViewById(R.id.goToSetting_Home);
        goToSetting_Home.setOnClickListener(view -> startActivity(intent_settitng));

        return rootView;
    }

    public static void refresh(){
        adapter = new HomeViewAdapter(context, contacts, homeFragment);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onPause() {
        Listsave.HomeCardList.put_Home_List(contacts);
        super.onPause();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStartDrag(HomeViewAdapter.Holder holder) {
        mItemTouchHelper.startDrag(holder);
    }
}
