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

public class Adapter_AddCupCake extends RecyclerView.Adapter<Adapter_AddCupCake.MyViewHolder> {

    Context context;
    ArrayList<AddCupCake_GlobalVariable> cupcakelist;

    public Adapter_AddCupCake(Context context, ArrayList<AddCupCake_GlobalVariable> cupcakelist) {
        this.context = context;
        this.cupcakelist = cupcakelist;
    }

    @NonNull
    @Override
    public Adapter_AddCupCake.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_cupcake,parent,false);
        return new Adapter_AddCupCake.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_AddCupCake.MyViewHolder holder, int position) {

        AddCupCake_GlobalVariable addCupCake_globalVariable = cupcakelist.get(position);
        String iamgeUri = null;
        iamgeUri = addCupCake_globalVariable.getImage();
        Picasso.get().load(iamgeUri).into(holder.cupcakeImage);
        holder.cupcakeName.setText(addCupCake_globalVariable.getName());
        holder.cupcakeDescription.setText(addCupCake_globalVariable.getDescription());
        holder.cupcakePrice.setText(addCupCake_globalVariable.getPrice());
        String cupcakeID = addCupCake_globalVariable.getCakeID();

        holder.selectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelectedCupCake.class);
                intent.putExtra("cupcakeID", cupcakeID);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cupcakelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView cupcakeImage;
        TextView cupcakeName, cupcakeDescription, cupcakePrice;
        ImageView selectItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cupcakeImage = itemView.findViewById(R.id.ivCupCakeImage);
            cupcakeName = itemView.findViewById(R.id.tvCupCakeName);
            cupcakeDescription = itemView.findViewById(R.id.tvCupCakeDescription);
            cupcakePrice = itemView.findViewById(R.id.tvCupCakePrice);

            selectItem = itemView.findViewById(R.id.btnSelectItem);

        }
    }
}
