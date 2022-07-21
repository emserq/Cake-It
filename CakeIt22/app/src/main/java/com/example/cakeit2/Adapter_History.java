package com.example.cakeit2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter_History extends RecyclerView.Adapter<Adapter_History.MyViewHolder> {

    Context context;
    ArrayList<CustomCake_GlobalVariable> list;

    public Adapter_History(Context context, ArrayList<CustomCake_GlobalVariable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_History.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_purchasehistory,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_History.MyViewHolder holder, int position) {

        CustomCake_GlobalVariable pickup = list.get(position);

        String uid = pickup.getUid();

        holder.itemname.setText(pickup.getName() + "  #");
        holder.id.setText(pickup.getOrderid());
        // since di ko mafetch yung orderstatus Received, sinettext ko nalang, 3:40 na ng madaling araw eh
        holder.status.setText("Received");
        holder.name.setText(pickup.getUsername());
        holder.contact.setText(pickup.getUsercontact());
        holder.quantity.setText(pickup.getQuantity());
        holder.total.setText(pickup.getPrice());
        holder.orderTime.setText(pickup.getTimeorder());
        holder.orderReceived.setText(pickup.getTimereceived());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView itemname, id, status, name, contact, quantity, total, orderTime, orderReceived;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemname = itemView.findViewById(R.id.tvItemName);
            id = itemView.findViewById(R.id.tvOrderID);
            status = itemView.findViewById(R.id.tvCustomStatus);
            name = itemView.findViewById(R.id.tvUname);
            contact = itemView.findViewById(R.id.tvUcontact);
            quantity = itemView.findViewById(R.id.tvQuantity);
            total = itemView.findViewById(R.id.tvTotal);
            orderTime = itemView.findViewById(R.id.tvOrderTime);
            orderReceived = itemView.findViewById(R.id.tvOrderReceived);

        }
    }
}
