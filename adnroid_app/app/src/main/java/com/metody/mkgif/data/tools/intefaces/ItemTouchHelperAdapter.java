package com.metody.mkgif.data.tools.intefaces;


import android.widget.TextView;

public interface ItemTouchHelperAdapter {

    void onItemMove(int fromPosition, int toPosition, TextView textView);

    void onItemDismiss(int position);
}
