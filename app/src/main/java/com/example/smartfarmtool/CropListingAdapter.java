package com.example.smartfarmtool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CropListingAdapter extends RecyclerView.Adapter<CropListingAdapter.MyViewHolder> {

    Context mContext;
    List<CropDetail> mDataSet;

    public CropListingAdapter(Context context, List<CropDetail> arrayList){
        mContext = context;
        mDataSet = arrayList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView crop_name, current_cost, start_date;
        CardView crop_tile;

        public MyViewHolder(@NonNull View view) {
            super(view);
            crop_name = view.findViewById(R.id.crop_name);
            current_cost = view.findViewById(R.id.current_cost);
            start_date = view.findViewById(R.id.start_date);
            crop_tile = view.findViewById(R.id.crop_tile);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.crop_single_detail_tile,viewGroup,false);
        return (new MyViewHolder(v));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, int i) {
        final CropDetail detail = mDataSet.get(i);
        viewHolder.crop_name.setText(detail.getName().toUpperCase());
        viewHolder.current_cost.setText("₹ "+String.valueOf(detail.getTotalCost()));
        viewHolder.start_date.setText(detail.getDate());
        viewHolder.crop_tile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditExpensesActivity.class);
                intent.putExtra("id",detail.getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return (mDataSet==null)?0:mDataSet.size();
    }
}
