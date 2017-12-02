package com.metody.mkgif.data.tools;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.metody.mkgif.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<DataItem> mDataset;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }

        ViewHolder(LinearLayout inflate) {
            super(inflate);
            mTextView = inflate.findViewById(R.id.itemText);
        }
    }
    static class ViewHolder2 extends RecyclerView.ViewHolder {
        TextView mTextView;
        ViewHolder2(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    public MyAdapter(List<DataItem> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public int getItemViewType(int position) {
        DataType itemType = mDataset.get(position).getDataTyp();
        if(itemType.equals(DataType.Thema))
            return 0;
        else if (itemType.equals(DataType.status))
            return  1;
        else
            return 2;
    }
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        TextView v;
        ViewHolder vh = null;
        switch (viewType){
            case 0:
                v = (TextView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.thema_layout, parent, false);
                vh = new ViewHolder(v);
                break;
            case 1:
                v = (TextView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.subsection_layout, parent, false);
                vh = new ViewHolder(v);
                break;
            case 2:
                LinearLayout inflate = (LinearLayout) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.my_layout, parent, false);
                vh = new ViewHolder(inflate);
        }
        //assert v != null;
        /*v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int itemPosition = mRecyclerView.getChildLayoutPosition(view);
                onBindViewHolder(vh, 0);
                //                String item = mList.get(itemPosition);
            }
        });*/
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).getContetn());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
