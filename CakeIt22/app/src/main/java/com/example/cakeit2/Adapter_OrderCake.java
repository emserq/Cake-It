package com.example.cakeit2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.URI;
import java.util.ArrayList;

public class Adapter_OrderCake extends RecyclerView.Adapter<Adapter_OrderCake.MyViewHolder> {

    Context context;
    ArrayList<OrderCakeID_GlobalVariable> list;

    public Adapter_OrderCake(Context context, ArrayList<OrderCakeID_GlobalVariable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_OrderCake.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_cakecart,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_OrderCake.MyViewHolder holder, int position) {

        OrderCakeID_GlobalVariable orderCake = list.get(position);

        Picasso.get().load(orderCake.getImageurl()).into(holder.cakeImageUrl);
        holder.cakeName.setText(orderCake.getName());
        holder.description.setText(orderCake.getDescription());
        holder.quantity.setText(orderCake.getQuantity());
        holder.price.setText(orderCake.getPrice());
        String ID = orderCake.getOrderid();

        holder.removeCakeCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder;
                builder= new AlertDialog.Builder(context);

                builder.setMessage("Are you sure you want to remove?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child("My Cart").child(ID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent intent = new Intent(context, CartActivity.class);
                                        context.startActivity(intent);
                                    }
                                });
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

        AlertDialog.Builder builder;
        ShapeableImageView cakeImageUrl;
        ImageView removeCakeCart;
        TextView cakeName, description, quantity, price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            builder = new AlertDialog.Builder(context);
            removeCakeCart = itemView.findViewById(R.id.btn_RemoveCartCake);

            cakeImageUrl = itemView.findViewById(R.id.ivImage);
            cakeName = itemView.findViewById(R.id.tvCakeName);
            description = itemView.findViewById(R.id.tvCakeDescription);
            quantity = itemView.findViewById(R.id.tvCakeQuantity);
            price = itemView.findViewById(R.id.tvCakePrice);

        }
    }
}
