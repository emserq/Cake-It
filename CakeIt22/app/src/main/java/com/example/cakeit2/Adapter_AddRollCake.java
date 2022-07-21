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

public class Adapter_AddRollCake extends RecyclerView.Adapter<Adapter_AddRollCake.MyViewHolder> {

    Context context;
    ArrayList<AddRollCake_GlobalVariable> rollcakelist;

    public Adapter_AddRollCake(Context context, ArrayList<AddRollCake_GlobalVariable> rollcakelist) {
        this.context = context;
        this.rollcakelist = rollcakelist;
    }

    @NonNull
    @Override
    public Adapter_AddRollCake.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_rollcake, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_AddRollCake.MyViewHolder holder, int position) {

        AddRollCake_GlobalVariable addRollCake_globalVariable = rollcakelist.get(position);
        String imageUri = null;
        imageUri = addRollCake_globalVariable.getImage();
        Picasso.get().load(imageUri).into(holder.rollcakeImage);
        holder.rollcakeName.setText(addRollCake_globalVariable.getName());
        holder.rollcakeDescription.setText(addRollCake_globalVariable.getDescription());
        holder.rollcakePrice.setText(addRollCake_globalVariable.getPrice());
        String rollcakeID = addRollCake_globalVariable.getCakeID();

        holder.selectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelectedRollCake.class);
                intent.putExtra("rollcakeID", rollcakeID);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rollcakelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView rollcakeImage;
        TextView rollcakeName, rollcakeDescription, rollcakePrice;
        ImageView selectItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            rollcakeImage = itemView.findViewById(R.id.ivRollCakeImage);
            rollcakeName = itemView.findViewById(R.id.tvRollCakeName);
            rollcakeDescription = itemView.findViewById(R.id.tvRollCakeDescription);
            rollcakePrice = itemView.findViewById(R.id.tvRollCakePrice);

            selectItem = itemView.findViewById(R.id.btnSelectItem);

        }
    }
}
