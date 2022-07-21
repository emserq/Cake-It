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

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Adapter_AdminCustomCupCakeOrder extends RecyclerView.Adapter<Adapter_AdminCustomCupCakeOrder.MyViewHolder> {

    Context context;
    ArrayList<CustomCupCake_GlobalVariable> list;
    AlertDialog.Builder builder;

    public Adapter_AdminCustomCupCakeOrder(Context context, ArrayList<CustomCupCake_GlobalVariable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_AdminCustomCupCakeOrder.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_admincustomcupcake,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_AdminCustomCupCakeOrder.MyViewHolder holder, int position) {

        CustomCupCake_GlobalVariable order = list.get(position);

        String idd = order.getOrderid();
        String uid = order.getUid();

        holder.name.setText(order.getUsername());
        holder.contact.setText(order.getUsercontact());
        holder.quantity.setText(order.getQuantity());
        holder.id.setText(order.getOrderid());
        holder.total.setText(order.getPrice());
        holder.sponge.setText(order.getSponge());
        holder.filling.setText(order.getFilling());
        holder.frosting.setText(order.getFrosting());
        holder.toppings.setText(order.getToppings());
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
                                        .child("Cup Cake").child(idd).child("orderstatus").setValue("To pickup");

                                // Deleting the order
                                FirebaseDatabase.getInstance().getReference("Orders").child("Custom Orders").child("Cup Cake").child(idd).removeValue();

                                Intent intent = new Intent(context, AdminCustomCupCakeOrdersActivity.class);
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
        return list.size();}

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView forpickup;
        TextView id, name, contact, quantity, total, sponge, filling, frosting, toppings, candles, orderTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            forpickup = itemView.findViewById(R.id.ivForPickup);

            id = itemView.findViewById(R.id.tvOrderID);
            name = itemView.findViewById(R.id.tvUname);
            contact = itemView.findViewById(R.id.tvUcontact);
            quantity = itemView.findViewById(R.id.tvQuantity);
            total = itemView.findViewById(R.id.tvTotal);
            sponge = itemView.findViewById(R.id.tvSponge);
            filling = itemView.findViewById(R.id.tvFilling);
            frosting = itemView.findViewById(R.id.tvFrosting);
            toppings = itemView.findViewById(R.id.tvToppings);
            candles = itemView.findViewById(R.id.tvCandles);
            orderTime = itemView.findViewById(R.id.tvOrderTime);

        }
    }
}
