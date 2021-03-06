package com.example.smartfarmtool;

import android.arch.persistence.room.Room;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {



    List<User> users;


    public UserAdapter(List<User> users)
    {
        this.users = users;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {







        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_row, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.cropname.setText(users.get(i).getCropname());
        viewHolder.quantity.setText(users.get(i).getQuantity()+" Quintal");
        viewHolder.rate.setText("₹ " + users.get(i).getRate()+"/Quintal");
        viewHolder.date.setText(users.get(i).getDate());



    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {



        public TextView cropname;
        public TextView quantity;
        public TextView rate;
        public TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cropname = itemView.findViewById(R.id.cropname);
            quantity = itemView.findViewById(R.id.quantity_);
            rate = itemView.findViewById(R.id.rate_);
            date = itemView.findViewById(R.id.currentdate_selling);




        }
    }
}
