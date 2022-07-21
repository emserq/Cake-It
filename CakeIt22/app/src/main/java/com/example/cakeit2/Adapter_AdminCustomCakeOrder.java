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
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Adapter_AdminCustomCakeOrder extends RecyclerView.Adapter<Adapter_AdminCustomCakeOrder.MyViewHolder> {

    Context context;
    ArrayList<CustomCake_GlobalVariable> list;
    AlertDialog.Builder builder;

    public Adapter_AdminCustomCakeOrder(Context context, ArrayList<CustomCake_GlobalVariable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_AdminCustomCakeOrder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_admincustomcake,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_AdminCustomCakeOrder.MyViewHolder holder, int position) {

        CustomCake_GlobalVariable order = list.get(position);

        String idd = order.getOrderid();
        String uid = order.getUid();

        holder.name.setText(order.getUsername());
        holder.contact.setText(order.getUsercontact());
        holder.quantity.setText(order.getQuantity());
        holder.id.setText(order.getOrderid());
        holder.total.setText(order.getPrice());
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
                                FirebaseDatabase.getInstance().getReference("User").child(uid).child("My Orders").child("Custom Order")
                                        .child("Cake").child(idd).child("orderstatus").setValue("To pickup");

                                // Deleting the order
                                FirebaseDatabase.getInstance().getReference("Orders").child("Custom Orders").child("Cake").child(idd).removeValue();

                                Intent intent = new Intent(context, AdminCustomCakeOrdersActivity.class);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView forpickup;
        TextView size, shape, structure, layers, filling, sponge, toppings, frosting, message, candles, quantity, total, status, name, contact, id, orderTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            forpickup = itemView.findViewById(R.id.ivForPickup);

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

