package com.example.cakeit2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_CustomRollCakeOrder extends RecyclerView.Adapter<Adapter_CustomRollCakeOrder.MyViewHolder> {

    Context context;
    ArrayList<CustomRollCake_GlobalVariable> list;

    public Adapter_CustomRollCakeOrder(Context context, ArrayList<CustomRollCake_GlobalVariable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_CustomRollCakeOrder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_customrollcake,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_CustomRollCakeOrder.MyViewHolder holder, int position) {

        CustomRollCake_GlobalVariable rollcake = list.get(position);

        holder.size.setText(rollcake.getSize());
        holder.sponge.setText(rollcake.getSponge());
        holder.filling.setText(rollcake.getFilling());
        holder.frosting.setText(rollcake.getFrosting());
        holder.toppings.setText(rollcake.getTopping());
        holder.candles.setText(rollcake.getCandle());
        holder.quantity.setText(rollcake.getQuantity());
        holder.status.setText(rollcake.getOrderstatus());
        holder.name.setText(rollcake.getUsername());
        holder.contact.setText(rollcake.getUsercontact());
        holder.id.setText(rollcake.getOrderid());
        holder.total.setText(rollcake.getPrice());
        holder.orderTime.setText(rollcake.getTimeorder());

        String status = holder.status.getText().toString();

        if (status.equals("To pickup")){
            holder.status.setBackgroundResource(R.drawable.statuscustomtopickup);
        }

        if (status.equals("Received")){
            holder.status.setBackgroundResource(R.drawable.statuscustomreceived);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView size, sponge, filling, frosting, toppings, candles, quantity, total, status, name, contact, id, orderTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            size = itemView.findViewById(R.id.tvSize);
            sponge = itemView.findViewById(R.id.tvSponge);
            filling = itemView.findViewById(R.id.tvFilling);
            frosting = itemView.findViewById(R.id.tvFrosting);
            toppings = itemView.findViewById(R.id.tvToppings);
            candles = itemView.findViewById(R.id.tvCandles);
            quantity = itemView.findViewById(R.id.tvQuantity);
            status = itemView.findViewById(R.id.tvCustomStatus);
            name = itemView.findViewById(R.id.tvUname);
            contact = itemView.findViewById(R.id.tvUcontact);
            total = itemView.findViewById(R.id.tvTotal);
            id = itemView.findViewById(R.id.tvOrderID);
            orderTime = itemView.findViewById(R.id.tvOrderTime);

        }
    }
}
