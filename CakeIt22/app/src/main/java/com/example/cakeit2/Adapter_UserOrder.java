package com.example.cakeit2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_UserOrder extends RecyclerView.Adapter<Adapter_UserOrder.MyViewHolder> {

    Context context;
    ArrayList<OrderCakeID_GlobalVariable> list;

    public Adapter_UserOrder(Context context, ArrayList<OrderCakeID_GlobalVariable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_UserOrder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_orders,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_UserOrder.MyViewHolder holder, int position) {

        OrderCakeID_GlobalVariable orderCakeUser = list.get(position);

        Picasso.get().load(orderCakeUser.getImageurl()).into(holder.cakeImageUrl);
        holder.cakeName.setText(orderCakeUser.getName());
        holder.description.setText(orderCakeUser.getDescription());
        holder.quantity.setText(orderCakeUser.getQuantity());
        holder.name.setText(orderCakeUser.getUsername());
        holder.contact.setText(orderCakeUser.getUsercontact());
        holder.id.setText(orderCakeUser.getOrderid());
        holder.status.setText(orderCakeUser.getOrderstatus());
        holder.total.setText(orderCakeUser.getPrice());
        holder.orderTIme.setText(orderCakeUser.getTimeorder());

        String status = holder.status.getText().toString();

        if (status.equals("To pickup")){
            holder.status.setBackgroundResource(R.drawable.statusnormaltopickup);
        }

        if (status.equals("Received")){
            holder.status.setBackgroundResource(R.drawable.statusnormalreceived);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView cakeImageUrl;
        TextView cakeName, description, name, contact, id, quantity, status, total, orderTIme;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cakeImageUrl = itemView.findViewById(R.id.ivOrderImage);
            cakeName = itemView.findViewById(R.id.tvOrderName);
            description = itemView.findViewById(R.id.tvOrderDescription);

            name = itemView.findViewById(R.id.tvUname);
            contact = itemView.findViewById(R.id.tvUcontact);
            id = itemView.findViewById(R.id.tvOrderID);
            quantity = itemView.findViewById(R.id.tvOrderQuantity);
            status = itemView.findViewById(R.id.tvOrderStatus);
            total = itemView.findViewById(R.id.tvTotal);
            orderTIme = itemView.findViewById(R.id.tvOrderTime);

        }
    }
}
