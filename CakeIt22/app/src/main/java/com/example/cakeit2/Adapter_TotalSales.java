package com.example.cakeit2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter_TotalSales extends RecyclerView.Adapter<Adapter_TotalSales.MyViewHolder> {

    Context context;
    ArrayList<CustomCake_GlobalVariable> list;

    public Adapter_TotalSales(Context context, ArrayList<CustomCake_GlobalVariable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_TotalSales.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_purchasehistory,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_TotalSales.MyViewHolder holder, int position) {

        CustomCake_GlobalVariable pickup = list.get(position);

        String uid = pickup.getUid();

        holder.itemname.setText(pickup.getName() + "  #");
        holder.id.setText(pickup.getOrderid());
        // since di ko mafetch yung orderstatus Received, sinettext ko nalang, 3:40 na ng madaling araw eh
        holder.status.setText("Completed");
        holder.name.setText(pickup.getUsername());
        holder.contact.setText(pickup.getUsercontact());
        holder.quantity.setText(pickup.getQuantity());
        holder.total.setText(pickup.getPrice());
        holder.orderTime.setText(pickup.getTimeorder());
        holder.orderReceived.setText(pickup.getTimereceived());
        holder.completed.setText("Completed Time:");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemname, id, status, name, contact, quantity, total, orderTime, orderReceived, completed;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.tvItemName);
            id = itemView.findViewById(R.id.tvOrderID);
            status = itemView.findViewById(R.id.tvCustomStatus);
            name = itemView.findViewById(R.id.tvUname);
            contact = itemView.findViewById(R.id.tvUcontact);
            quantity = itemView.findViewById(R.id.tvQuantity);
            total = itemView.findViewById(R.id.tvTotal);
            orderTime = itemView.findViewById(R.id.tvOrderTime);
            orderReceived = itemView.findViewById(R.id.tvOrderReceived);
            completed = itemView.findViewById(R.id.completed);

        }
    }
}
