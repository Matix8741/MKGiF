package com.metody.mkgif.data.tools;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.metody.mkgif.MainActivity;
import com.metody.mkgif.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<DataItem> mDataset;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mainText;
        TextView date;
        TextView rating;
        ViewHolder(TextView mainText) {
            super(mainText);
            this.mainText = mainText;
        }

        ViewHolder(final MyAdapter myAdapter, LinearLayout inflate) {
            super(inflate);
            mainText = inflate.findViewById(R.id.itemText);
            date = inflate.findViewById(R.id.itemDate);
            rating = inflate.findViewById(R.id.itemRating);
            inflate.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(myAdapter.mainActivity);
                    builder.setMessage(R.string.dialog_remove_item_message);
                    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            myAdapter.removeAt(getAdapterPosition());
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }
            });
        }
    }
    public MyAdapter(List<DataItem> myDataset, MainActivity mainActivity) {
        mDataset = myDataset; this.mainActivity = mainActivity;
    }
    public MainActivity mainActivity;
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
                        .inflate(R.layout.item_layout, parent, false);
                vh = new ViewHolder(this, inflate);
                break;
        }
        return vh;
    }
    public void removeAt(int position) {
        mDataset.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        DataItem item = mDataset.get(position);
        holder.mainText.setText(item.getContent());
        if(item.getDataTyp().equals(DataType.item)) {
            holder.rating.setText(String.valueOf(mDataset.get(position).getRating()));
            holder.date.setText(mDataset.get(position).getDate());
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
