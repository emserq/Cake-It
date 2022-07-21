package com.example.cakeit2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_AdminOrders extends RecyclerView.Adapter<Adapter_AdminOrders.MyViewHolder> {

    Context context;
    ArrayList<OrderCakeID_GlobalVariable> list;
    AlertDialog.Builder builder;

    public Adapter_AdminOrders(Context context, ArrayList<OrderCakeID_GlobalVariable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_AdminOrders.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_adminorder, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_AdminOrders.MyViewHolder holder, int position) {

        OrderCakeID_GlobalVariable order = list.get(position);

        String idd = order.getOrderid();
        String uid = order.getUid();

        Picasso.get().load(order.getImageurl()).into(holder.orderImage);
        holder.orderName.setText(order.getName());
        holder.orderDesc.setText(order.getDescription());
        holder.orderQuantity.setText(order.getQuantity());
        holder.name.setText(order.getUsername());
        holder.contact.setText(order.getUsercontact());
        holder.total.setText(order.getPrice());
        holder.id.setText(order.getOrderid());
        holder.orderTime.setText(order.getTimeorder());

        holder.forpickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder = new AlertDialog.Builder(context);

                builder.setTitle("Admin")
                        .setMessage("Ready for Pickup?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                // Passing the order in pickup database
                                FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(idd).setValue(order);

                                // Changing the pending status to to pickup
                                FirebaseDatabase.getInstance().getReference("User").child(uid).child("My Orders").child("Normal Orders")
                                        .child(idd).child("orderstatus").setValue("To pickup");

                                // Deleting the order
                                FirebaseDatabase.getInstance().getReference("Orders").child("Normal Orders").child(idd).removeValue();

                                Intent intent = new Intent(context, AdminOrdersActivity.class);
                                context.startActivity(intent);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView orderImage, forpickup;
        TextView orderName, orderDesc, orderQuantity, name, contact, total, id, orderTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            orderImage = itemView.findViewById(R.id.ivOrderImage);
            forpickup = itemView.findViewById(R.id.ivForPickup);

            orderName = itemView.findViewById(R.id.tvOrderName);
            orderDesc = itemView.findViewById(R.id.tvOrderDescription);
            name = itemView.findViewById(R.id.tvUname);
            contact = itemView.findViewById(R.id.tvUcontact);
            orderQuantity = itemView.findViewById(R.id.tvOrderQuantity);
            total = itemView.findViewById(R.id.tvTotal);
            id = itemView.findViewById(R.id.tvOrderID);
            orderTime = itemView.findViewById(R.id.tvOrderTime);

        }
    }
}
