package com.example.blogapp;

import android.content.ClipData;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<item> listData;
    private AppCompatActivity mContext;

    public ItemAdapter(List<item> listData, AppCompatActivity mContext) {
        this.listData = listData;
        this.mContext = mContext;//it holds the name of the activity from which it is called.
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        item i=listData.get(position);
        holder.name.setText(i.getName());
        holder.desc.setText(i.getDescription());
        Glide.with(mContext).load(i.getUrl()).into(holder.img);
        holder.l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final item i=listData.get(holder.getLayoutPosition());
                Log.d("clicked","clicked"+i.getId()+" "+i.getName()+" "+holder.getLayoutPosition());
//                Intent comm=new Intent(mContext,ItemInfo.class);
//                comm.putExtra("ItemName",i.getName());
//                comm.putExtra("itemid",i.getId());
//                comm.putExtra("Price",i.getPrice());
//                comm.putExtra("Sellerid",i.getSellerid());
//                comm.putExtra("Description",i.getDescription());
//                comm.putExtra("Image",i.getUrl());
//                mContext.startActivity(comm);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name;
        TextView desc;
        LinearLayout l;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=(ImageView) itemView.findViewById(R.id.imageview);
            name=(TextView) itemView.findViewById(R.id.textName);
            desc=(TextView) itemView.findViewById(R.id.textprice);
            l=(LinearLayout) itemView.findViewById(R.id.linearlayout);
        }
    }
}