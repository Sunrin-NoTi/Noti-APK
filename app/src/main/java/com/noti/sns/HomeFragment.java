package com.noti.sns;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements MainViewAdapter.OnStartDragListener {

    public static SharedPreferences pref;
    public static SharedPreferences.Editor edit;
    public static ItemTouchHelper mItemTouchHelper;


    public static HomeFragment newInstance() {
        // Required empty public constructor
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }
    private RecyclerView recyclerView;
    private MainViewAdapter adapter;

    ArrayList<card_main> contacts = new ArrayList<card_main>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.frg_nemu_home, container, false);
        pref = getActivity().getSharedPreferences("save", 0);
        edit = pref.edit();
        recyclerView = rootView.findViewById(R.id.recyclerView);
        adapter = new MainViewAdapter(getActivity(), contacts, this);

        contacts.add(new card_main("a","b","c",1));
        contacts.add(new card_main("a","b","c",2));
        contacts.add(new card_main("a","b","c",3));
        contacts.add(new card_main("a","b","c",3));



        HomeItemTouchHelperCallback mCallback = new HomeItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(mCallback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        Log.e("Frag", "MainFragment");
        return rootView;
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStartDrag(MainViewAdapter.Holder holder) {
        mItemTouchHelper.startDrag(holder);
    }
}
