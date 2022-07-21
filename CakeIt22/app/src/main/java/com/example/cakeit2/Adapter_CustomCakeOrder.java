package com.example.cakeit2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_CustomCakeOrder extends RecyclerView.Adapter<Adapter_CustomCakeOrder.MyViewHolder> {

    Context context;
    ArrayList<CustomCake_GlobalVariable> list;

    public Adapter_CustomCakeOrder(Context context, ArrayList<CustomCake_GlobalVariable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_CustomCakeOrder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_customcake,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_CustomCakeOrder.MyViewHolder holder, int position) {

        CustomCake_GlobalVariable order = list.get(position);

        holder.name.setText(order.getUsername());
        holder.contact.setText(order.getUsercontact());
        holder.quantity.setText(order.getQuantity());
        holder.total.setText(order.getPrice());
        holder.id.setText(order.getOrderid());
        holder.status.setText(order.getOrderstatus());
        holder.size.setText(order.getSize());
        holder.structure.setText(order.getStructure());
        holder.filling.setText(order.getFilling());
        holder.toppings.setText(order.getToppings());
        holder.message.setText(order.getMessage());
        holder.shape.setText(order.getShape());
        holder.layers.setText(order.getLayers());
        holder.sponge.setText(order.getSponge());
        holder.frosting.setText(order.getFrosting());
        holder.candles.setText(order.getCandles());
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

        TextView size, shape, structure, layers, filling, sponge, toppings, frosting, message, candles, quantity, total, status, name, contact, id, orderTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            size = itemView.findViewById(R.id.tvSize);
            shape = itemView.findViewById(R.id.tvShape);
            structure = itemView.findViewById(R.id.tvStructure);
            layers = itemView.findViewById(R.id.tvLayers);
            filling = itemView.findViewById(R.id.tvFilling);
            sponge = itemView.findViewById(R.id.tvSponge);
            toppings = itemView.findViewById(R.id.tvToppings);
            frosting = itemView.findViewById(R.id.tvFrosting);
            message = itemView.findViewById(R.id.tvMessage);
            candles = itemView.findViewById(R.id.tvCandle);
            quantity = itemView.findViewById(R.id.tvQuantity);
            total = itemView.findViewById(R.id.tvTotal);
            status = itemView.findViewById(R.id.tvCustomStatus);
            name = itemView.findViewById(R.id.tvUname);
            contact = itemView.findViewById(R.id.tvUcontact);
            id = itemView.findViewById(R.id.tvOrderID);
            orderTime = itemView.findViewById(R.id.tvOrderTime);

        }
    }
}

