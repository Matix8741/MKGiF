package com.metody.mkgif.data.tools;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MyOnClickListener implements OnClickListener  {

    private RecyclerView mRecyclerView;

    public MyOnClickListener(RecyclerView mRecyclerView){
        this.mRecyclerView = mRecyclerView;
    }

    @Override
    public void onClick(View view) {
        int itemPosition = mRecyclerView.getChildLayoutPosition(view);
        }
}
