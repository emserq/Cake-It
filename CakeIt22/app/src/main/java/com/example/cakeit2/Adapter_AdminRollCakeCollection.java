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

public class Adapter_AdminRollCakeCollection extends RecyclerView.Adapter<Adapter_AdminRollCakeCollection.MyViewHolder> {

    Context context;
    ArrayList<AddRollCake_GlobalVariable> list;

    public Adapter_AdminRollCakeCollection(Context context, ArrayList<AddRollCake_GlobalVariable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_AdminRollCakeCollection.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_managerollcake,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_AdminRollCakeCollection.MyViewHolder holder, int position) {

        AddRollCake_GlobalVariable addRollCake_globalVariable = list.get(position);
        String imageUri = null;
        imageUri = addRollCake_globalVariable.getImage();
        Picasso.get().load(imageUri).into(holder.cakeImage);
        holder.cakeName.setText(addRollCake_globalVariable.getName());
        holder.description.setText(addRollCake_globalVariable.getDescription());
        holder.price.setText(addRollCake_globalVariable.getPrice());
        String cakeID = addRollCake_globalVariable.getCakeID();

        holder.selectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdminSelectedRollCake.class);
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

            cakeImage = view.findViewById(R.id.ivRollCakeImage);
            cakeName = view.findViewById(R.id.tvRollCakeName);
            description = view.findViewById(R.id.tvRollCakeDescription);
            price = view.findViewById(R.id.tvRollCakePrice);
            selectItem = view.findViewById(R.id.btnSelectItem);

        }
    }
}
