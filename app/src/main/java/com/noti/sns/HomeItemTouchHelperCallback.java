package com.noti.sns;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class HomeItemTouchHelperCallback extends ItemTouchHelper.Callback {

    public interface OnItemMoveListener {
        void onItemMove(int fromPosition, int toPosition);
    }
    private final OnItemMoveListener mItemMoveListener;

    public HomeItemTouchHelperCallback(OnItemMoveListener listener) {
        mItemMoveListener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mItemMoveListener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }



}

