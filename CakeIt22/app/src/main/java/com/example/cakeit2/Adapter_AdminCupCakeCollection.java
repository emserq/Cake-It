package com.example.cakeit2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_AdminCupCakeCollection extends RecyclerView.Adapter<Adapter_AdminCupCakeCollection.MyViewHolder> {

    Context context;
    ArrayList<AddCupCake_GlobalVariable> list;

    public Adapter_AdminCupCakeCollection(Context context, ArrayList<AddCupCake_GlobalVariable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_AdminCupCakeCollection.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_managecupcake,parent,false);
        return new Adapter_AdminCupCakeCollection.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_AdminCupCakeCollection.MyViewHolder holder, int position) {

        AddCupCake_GlobalVariable addCupcake = list.get(position);
        String imageUri = null;
        imageUri = addCupcake.getImage();
        Picasso.get().load(imageUri).into(holder.cupcakeImage);
        holder.cupcakeName.setText(addCupcake.getName());
        holder.description.setText(addCupcake.getDescription());
        holder.price.setText(addCupcake.getPrice());
        String cakeID = addCupcake.getCakeID();

        holder.selectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdminSelectedCupCake.class);
                intent.putExtra("cakeID", cakeID);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView cupcakeImage;
        TextView cupcakeName, description, price;
        ImageView selectItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cupcakeImage = itemView.findViewById(R.id.ivCupCakeImage);
            cupcakeName = itemView.findViewById(R.id.tvCupCakeName);
            description = itemView.findViewById(R.id.tvCupCakeDescription);
            price = itemView.findViewById(R.id.tvCupCakePrice);
            selectItem = itemView.findViewById(R.id.btnSelectItem);

        }
    }
}
