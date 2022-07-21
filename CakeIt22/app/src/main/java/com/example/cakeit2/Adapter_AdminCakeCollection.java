package com.example.cakeit2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_AdminCakeCollection extends RecyclerView.Adapter<Adapter_AdminCakeCollection.MyViewHolder> {

    Context context;
    ArrayList<AddCake_GlobalVariable> list;

    public Adapter_AdminCakeCollection(Context context, ArrayList<AddCake_GlobalVariable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_AdminCakeCollection.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_managecake,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_AdminCakeCollection.MyViewHolder holder, int position) {

        AddCake_GlobalVariable addCake_globalVariable = list.get(position);
        String imageUri = null;
        imageUri = addCake_globalVariable.getImage();
        Picasso.get().load(imageUri).into(holder.cakeImage);
        holder.cakeName.setText(addCake_globalVariable.getName());
        holder.description.setText(addCake_globalVariable.getDescription());
        holder.price.setText(addCake_globalVariable.getPrice());
        String cakeID = addCake_globalVariable.getCakeID();

        holder.selectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdminSelectedCake.class);
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

        ImageView cakeImage;
        TextView cakeName, description, price;
        ImageView selectItem;

        public MyViewHolder(View view) {
            super(view);

            cakeImage = view.findViewById(R.id.ivCakeImage);
            cakeName = view.findViewById(R.id.tvCakeName);
            description = view.findViewById(R.id.tvCakeDescription);
            price = view.findViewById(R.id.tvCakePrice);
            selectItem = view.findViewById(R.id.btnSelectItem);

        }
    }
}
