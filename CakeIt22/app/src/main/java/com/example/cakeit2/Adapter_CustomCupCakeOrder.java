package com.example.cakeit2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_CustomCupCakeOrder extends RecyclerView.Adapter<Adapter_CustomCupCakeOrder.MyViewHolder> {

    Context context;
    ArrayList<CustomCupCake_GlobalVariable> list;

    public Adapter_CustomCupCakeOrder(Context context, ArrayList<CustomCupCake_GlobalVariable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_CustomCupCakeOrder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_customcupcake,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_CustomCupCakeOrder.MyViewHolder holder, int position) {

        CustomCupCake_GlobalVariable order = list.get(position);

        holder.filling.setText(order.getFilling());
        holder.toppings.setText(order.getToppings());
        holder.sponge.setText(order.getSponge());
        holder.frosting.setText(order.getFrosting());
        holder.candles.setText(order.getCandles());
        holder.quantity.setText(order.getQuantity());
        holder.status.setText(order.getOrderstatus());
        holder.name.setText(order.getUsername());
        holder.contact.setText(order.getUsercontact());
        holder.id.setText(order.getOrderid());
        holder.total.setText(order.getPrice());
        holder.orderTime.setText(order.getTimeorder());

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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView sponge, filling, frosting, toppings, candles, quantity, total, status, name, contact, id, orderTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

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
            orderTime= itemView.findViewById(R.id.tvOrderTime);
        }
    }
}
