package com.metody.mkgif.data.tools;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.metody.mkgif.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private String[] mDataset;
//    private final View.OnClickListener mOnClickListener = new MyOnClickListener(this);
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTextView;
        ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }
    static class ViewHolder2 extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView mTextView;
        ViewHolder2(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        if(position < 3)
            return 0;
        else
            return  1;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        TextView v = null;
        switch (viewType){
            case 0:
                v = (TextView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.thema_layout, parent, false);
                break;
            case 1:
                v = (TextView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.my_layout, parent, false);
                break;
        }
        // set the view's size, margins, paddings and layout parameters
        //...
        final ViewHolder vh = new ViewHolder(v);
        assert v != null;
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int itemPosition = mRecyclerView.getChildLayoutPosition(view);
                onBindViewHolder(vh, 0);
                //                String item = mList.get(itemPosition);
//                Toast.makeText(mContent, item, Toast.LENGTH_LONG).show();
            }
        });
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset[position]);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
