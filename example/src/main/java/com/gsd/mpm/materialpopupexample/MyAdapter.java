package com.gsd.mpm.materialpopupexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<DataItems> itemList;
    private Context mContext;

    private CustomClickListener listener;

    public MyAdapter(Context mContext, ArrayList<DataItems> cardList, CustomClickListener listener) {
        this.mContext = mContext;
        this.itemList = cardList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item_list, parent, false);
        final ViewHolder mViewHolder = new ViewHolder(mView);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getAdapterPosition());
            }
        });
        mView.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View view) {
                listener.onItemLongClick(view, mViewHolder.getAdapterPosition());
                return false;
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(itemList.get(position).getTitle());
        holder.desc.setText(itemList.get(position).getDesc());
        holder.icon.setImageDrawable(itemList.get(position).getIcon());
        holder.icon.setBackgroundColor(itemList.get(position).getColor());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, desc;
        ImageView icon;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.menu_item_text_view);
            desc = view.findViewById(R.id.menu_item_summary_text_view);
            icon = view.findViewById(R.id.menu_item_view);

        }
    }
}