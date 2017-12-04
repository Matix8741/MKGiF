package com.metody.mkgif.data.tools;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.metody.mkgif.DetailActivity;
import com.metody.mkgif.MainActivity;
import com.metody.mkgif.R;
import com.metody.mkgif.data.tools.intefaces.ItemTouchHelperAdapter;
import com.metody.mkgif.data.tools.intefaces.ItemTouchHelperViewHolder;
import com.metody.mkgif.data.tools.intefaces.OnStartDragListener;

import java.util.Collections;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    private List<Data> mDataSet;
    private OnItemClickListener mItemClickListener;
    private final OnStartDragListener mDragStartListener;

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < mDataSet.size() && toPosition < mDataSet.size()) {
            if (fromPosition < toPosition && (mDataSet.get(toPosition).getDataTyp() != DataType.Theme && mDataSet.get(toPosition - 1).getDataTyp() != DataType.Theme)) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mDataSet, i, i + 1);
                }
            } else if (mDataSet.get(toPosition).getDataTyp() != DataType.Theme && mDataSet.get(toPosition - 1).getDataTyp() != DataType.Theme) {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mDataSet, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
        }
    }

    @Override
    public void onItemDismiss(int position) {
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ItemTouchHelperViewHolder {
        TextView mainText;
        TextView date;
        TextView rating;
        LinearLayout inflate;

        ViewHolder(TextView mainText) {
            super(mainText);
            this.mainText = mainText;
        }

        ViewHolder(final MyAdapter myAdapter, LinearLayout inflate) {
            super(inflate);
            mainText = inflate.findViewById(R.id.itemText);
            date = inflate.findViewById(R.id.itemDate);
            rating = inflate.findViewById(R.id.itemRating);
            this.inflate = inflate;
            this.inflate.setOnClickListener(this);
            this.inflate.setOnLongClickListener(new View.OnLongClickListener() {
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
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return true;
                }
            });
            this.inflate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(myAdapter.mainActivity, DetailActivity.class);
                    intent.putExtra("text", mainText.getText());
                    intent.putExtra("rating", rating.getText());
                    intent.putExtra("date", date.getText());
                    myAdapter.mainActivity.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public MyAdapter(List<Data> myDataSet, MainActivity mainActivity, OnStartDragListener dragListner) {
        mDataSet = myDataSet;
        this.mainActivity = mainActivity;
        this.mDragStartListener = dragListner;
    }

    private MainActivity mainActivity;

    @Override
    public int getItemViewType(int position) {
        DataType itemType = mDataSet.get(position).getDataTyp();
        if (itemType.equals(DataType.Theme))
            return 0;
        else if (itemType.equals(DataType.status))
            return 1;
        else
            return 2;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        TextView v;
        ViewHolder vh = null;
        switch (viewType) {
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

    private void removeAt(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Data item = mDataSet.get(position);
        holder.mainText.setText(item.getContent());
        if (item.getDataTyp().equals(DataType.item)) {
            holder.rating.setText(String.valueOf(((DataItem) mDataSet.get(position)).getRating()));
            holder.date.setText(((DataItem) mDataSet.get(position)).getDate());

            holder.inflate.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        mDragStartListener.onStartDrag(holder);
                    }
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
