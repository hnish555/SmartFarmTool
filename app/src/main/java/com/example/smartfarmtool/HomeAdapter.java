package com.example.smartfarmtool;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context context;
    private List<Buy> buyList;

    public HomeAdapter(Context context, List<Buy> buyList) {
        this.context = context;
        this.buyList = buyList;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.buy_view,viewGroup,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder viewHolder, int position) {
        Buy buy=buyList.get(position);

        viewHolder.cname.setText(buy.getCname());
        viewHolder.quantity.setText("Total "+buy.getQuantity()+" quintal");
        viewHolder.date.setText(buy.getDate());
        viewHolder.rate.setText("₹"+buy.getPrice()+"/quintal");
    }

    @Override
    public int getItemCount() {
        return buyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView cname;
        public TextView quantity;
        public TextView rate;
        public TextView date;
        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            context=context;
            cname = itemView.findViewById(R.id.buy_cropname);
            quantity = itemView.findViewById(R.id.buy_quantity_);
            rate = itemView.findViewById(R.id.buy_rate_);
            date = itemView.findViewById(R.id.buy_currentdate_selling);
        }
    }
}
