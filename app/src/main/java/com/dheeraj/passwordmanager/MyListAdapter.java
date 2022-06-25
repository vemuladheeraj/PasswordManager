package com.dheeraj.passwordmanager;

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

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private ArrayList<Model> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyListAdapter(Context context, ArrayList<Model> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       // String app = mData.;
        final Model myListData = mData.get(position);

        holder.application.setText(myListData.application);
        holder.domain.setText(myListData.domain);
        holder.pass.setText(myListData.password);
        holder.email.setText(myListData.userName);
        holder.url.setText(myListData.url);
        holder.sImage.setImageResource(R.drawable.ic_baseline_enhanced_encryption_24);
        holder.sImage.setBackgroundColor(45);

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView domain;
        TextView email;
        TextView pass;
        TextView application;
        TextView url;
        ImageView sImage;

        ViewHolder(View itemView) {
            super(itemView);
            domain = itemView.findViewById(R.id.domain);
            email = itemView.findViewById(R.id.emailID);
            pass = itemView.findViewById(R.id.password);
            application = itemView.findViewById(R.id.appName);
            url = itemView.findViewById(R.id.url);
            sImage=itemView.findViewById(R.id.sImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        //return mData.get(id);
        return "explicit null";
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
