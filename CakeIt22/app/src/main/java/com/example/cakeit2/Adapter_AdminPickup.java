package com.example.cakeit2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Layout;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Adapter_AdminPickup extends RecyclerView.Adapter<Adapter_AdminPickup.MyViewHolder> {

    Context context;
    ArrayList<CustomCake_GlobalVariable> list;

    public Adapter_AdminPickup(Context context, ArrayList<CustomCake_GlobalVariable> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Adapter_AdminPickup.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_adminpickup,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_AdminPickup.MyViewHolder holder, int position) {

        CustomCake_GlobalVariable pickup = list.get(position);

        String uid = pickup.getUid();

        holder.itemname.setText(pickup.getName() + "  #");
        holder.id.setText(pickup.getOrderid());
        holder.status.setText("To pickup");
        holder.name.setText(pickup.getUsername());
        holder.contact.setText(pickup.getUsercontact());
        holder.quantity.setText(pickup.getQuantity());
        holder.total.setText(pickup.getPrice());
        holder.orderTime.setText(pickup.getTimeorder());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String popupID = pickup.getOrderid();
                String itemname = pickup.getName();

                if (itemname.equals("Custom Cake")){

                    // Setting up the pop up window
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.popup_pickupcustomcake, null);

                    TextView orderID, itemName, userName, userContact, itemQuantity, itemTotal,
                    size, shape, structure, layers, filling, sponge, toppings, frosting, message, candles;

                    AppCompatButton endTransaction;
                    AlertDialog.Builder dialog;

                    endTransaction = dialogView.findViewById(R.id.btn_endTransaction);
                    dialog = new AlertDialog.Builder(context);

                    orderID = dialogView.findViewById(R.id.tvOrderID);
                    itemName = dialogView.findViewById(R.id.tvItemName);
                    userName = dialogView.findViewById(R.id.tvUserName);
                    userContact = dialogView.findViewById(R.id.tvUserContact);
                    itemQuantity = dialogView.findViewById(R.id.tvItemQuantity);
                    itemTotal = dialogView.findViewById(R.id.tvItemTotal);

                    size = dialogView.findViewById(R.id.tvSize);
                    shape = dialogView.findViewById(R.id.tvShape);
                    structure = dialogView.findViewById(R.id.tvStructure);
                    layers = dialogView.findViewById(R.id.tvLayers);
                    filling = dialogView.findViewById(R.id.tvFilling);
                    sponge = dialogView.findViewById(R.id.tvSponge);
                    toppings = dialogView.findViewById(R.id.tvToppings);
                    frosting = dialogView.findViewById(R.id.tvFrosting);
                    message = dialogView.findViewById(R.id.tvMessage);
                    candles = dialogView.findViewById(R.id.tvCandles);

                    FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    CustomCake_GlobalVariable order = snapshot.getValue(CustomCake_GlobalVariable.class);

                                    orderID.setText(order.getOrderid());
                                    itemName.setText(order.getName());
                                    userName.setText(order.getUsername());
                                    userContact.setText(order.getUsercontact());
                                    itemQuantity.setText(order.getQuantity());
                                    itemTotal.setText(order.getPrice());

                                    size.setText(order.getSize());
                                    shape.setText(order.getShape());
                                    structure.setText(order.getStructure());
                                    layers.setText(order.getLayers());
                                    filling.setText(order.getFilling());
                                    sponge.setText(order.getSponge());
                                    toppings.setText(order.getToppings());
                                    frosting.setText(order.getFrosting());
                                    message.setText(order.getMessage());
                                    candles.setText(order.getCandles());

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                    // Showing the pop up window
                    builder.setView(dialogView);
                    builder.setCancelable(true);
                    builder.show();


                    endTransaction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.setTitle("End Transaction")
                                    .setMessage("Are you sure?")
                                    .setCancelable(true)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            // Getting the time order
                                            Calendar calendar = Calendar.getInstance();
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
                                            String date = simpleDateFormat.format(calendar.getTime());

                                            // Storing the time received in the pickups database
                                            FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID)
                                                    .child("timereceived").setValue(date);

                                            // Passing the pickup data in total sales database
                                            FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID)
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                            OrderCakeID_GlobalVariable order = snapshot.getValue(OrderCakeID_GlobalVariable.class);

                                                            FirebaseDatabase.getInstance().getReference("Total Sales").child(popupID)
                                                                    .setValue(order);

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                            // Passing the pickup data in users purchase history database
                                            FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID)
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                            OrderCakeID_GlobalVariable order = snapshot.getValue(OrderCakeID_GlobalVariable.class);

                                                            FirebaseDatabase.getInstance().getReference("User").child(uid).child("My Purchases").child(popupID)
                                                                    .setValue(order);

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                            // Changing the status in my purchases databse from pending to received
                                            FirebaseDatabase.getInstance().getReference("User").child(uid).child("My Purchases").child(popupID)
                                                    .child("orderstatus").setValue("Received");

                                            // Changing the status from to pickup to received
                                            FirebaseDatabase.getInstance().getReference("User").child(uid).child("My Orders").child("Custom Order")
                                                    .child("Cake").child(popupID).removeValue();

                                            // Deleting in pickup data from pickup database
                                            FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID).removeValue();

                                            Toast.makeText(context, "Transaction has been completed", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, AdminPickupCustom.class);
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
                else if (itemname.equals("Custom Rollcake")){

                    // Setting up the pop up window
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.popup_pickupcustomrollcake, null);

                    TextView orderID, itemName, userName, userContact, itemQuantity, itemTotal,
                            size, filling, sponge, toppings, frosting, candles;

                    AppCompatButton endTransaction;
                    AlertDialog.Builder dialog;

                    endTransaction = dialogView.findViewById(R.id.btn_endTransaction);
                    dialog = new AlertDialog.Builder(context);

                    orderID = dialogView.findViewById(R.id.tvOrderID);
                    itemName = dialogView.findViewById(R.id.tvItemName);
                    userName = dialogView.findViewById(R.id.tvUserName);
                    userContact = dialogView.findViewById(R.id.tvUserContact);
                    itemQuantity = dialogView.findViewById(R.id.tvItemQuantity);
                    itemTotal = dialogView.findViewById(R.id.tvItemTotal);

                    size = dialogView.findViewById(R.id.tvSize);
                    filling = dialogView.findViewById(R.id.tvFilling);
                    sponge = dialogView.findViewById(R.id.tvSponge);
                    toppings = dialogView.findViewById(R.id.tvToppings);
                    frosting = dialogView.findViewById(R.id.tvFrosting);
                    candles = dialogView.findViewById(R.id.tvCandles);

                    FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    CustomRollCake_GlobalVariable order = snapshot.getValue(CustomRollCake_GlobalVariable.class);

                                    orderID.setText(order.getOrderid());
                                    itemName.setText(order.getName());
                                    userName.setText(order.getUsername());
                                    userContact.setText(order.getUsercontact());
                                    itemQuantity.setText(order.getQuantity());
                                    itemTotal.setText(order.getPrice());

                                    size.setText(order.getSize());
                                    filling.setText(order.getFilling());
                                    sponge.setText(order.getSponge());
                                    toppings.setText(order.getTopping());
                                    frosting.setText(order.getFrosting());
                                    candles.setText(order.getCandle());

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                    // Showing the pop up window
                    builder.setView(dialogView);
                    builder.setCancelable(true);
                    builder.show();


                    endTransaction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.setTitle("End Transaction")
                                    .setMessage("Are you sure?")
                                    .setCancelable(true)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            // Getting the time order
                                            Calendar calendar = Calendar.getInstance();
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
                                            String date = simpleDateFormat.format(calendar.getTime());

                                            // Storing the time received in the pickups database
                                            FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID)
                                                    .child("timereceived").setValue(date);

                                            // Passing the pickup data in total sales database
                                            FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID)
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                            OrderCakeID_GlobalVariable order = snapshot.getValue(OrderCakeID_GlobalVariable.class);

                                                            FirebaseDatabase.getInstance().getReference("Total Sales").child(popupID)
                                                                    .setValue(order);

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                            // Passing the pickup data in users purchase history database
                                            FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID)
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                            OrderCakeID_GlobalVariable order = snapshot.getValue(OrderCakeID_GlobalVariable.class);

                                                            FirebaseDatabase.getInstance().getReference("User").child(uid).child("My Purchases").child(popupID)
                                                                    .setValue(order);

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                            // Changing the status in my purchases databse from pending to received
                                            FirebaseDatabase.getInstance().getReference("User").child(uid).child("My Purchases").child(popupID)
                                                    .child("orderstatus").setValue("Received");

                                            // Changing the status from to pickup to received
                                            FirebaseDatabase.getInstance().getReference("User").child(uid).child("My Orders").child("Custom Order")
                                                    .child("Roll Cake").child(popupID).removeValue();

                                            // Deleting in pickup data from pickup database
                                            FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID).removeValue();

                                            Toast.makeText(context, "Transaction has been completed", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, AdminPickupCustom.class);
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
                else if (itemname.equals("Custom Cupcake")){

                    // Setting up the pop up window
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.popup_pickupcustomcupcake, null);

                    TextView orderID, itemName, userName, userContact, itemQuantity, itemTotal,
                            sponge, filling, frosting, toppings, candles;

                    AppCompatButton endTransaction;
                    AlertDialog.Builder dialog;

                    endTransaction = dialogView.findViewById(R.id.btn_endTransaction);
                    dialog = new AlertDialog.Builder(context);

                    orderID = dialogView.findViewById(R.id.tvOrderID);
                    itemName = dialogView.findViewById(R.id.tvItemName);
                    userName = dialogView.findViewById(R.id.tvUserName);
                    userContact = dialogView.findViewById(R.id.tvUserContact);
                    itemQuantity = dialogView.findViewById(R.id.tvItemQuantity);
                    itemTotal = dialogView.findViewById(R.id.tvItemTotal);

                    filling = dialogView.findViewById(R.id.tvFilling);
                    sponge = dialogView.findViewById(R.id.tvSponge);
                    toppings = dialogView.findViewById(R.id.tvToppings);
                    frosting = dialogView.findViewById(R.id.tvFrosting);
                    candles = dialogView.findViewById(R.id.tvCandles);

                    FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    CustomCupCake_GlobalVariable order = snapshot.getValue(CustomCupCake_GlobalVariable.class);

                                    orderID.setText(order.getOrderid());
                                    itemName.setText(order.getName());
                                    userName.setText(order.getUsername());
                                    userContact.setText(order.getUsercontact());
                                    itemQuantity.setText(order.getQuantity());
                                    itemTotal.setText(order.getPrice());

                                    filling.setText(order.getFilling());
                                    sponge.setText(order.getSponge());
                                    toppings.setText(order.getToppings());
                                    frosting.setText(order.getFrosting());
                                    candles.setText(order.getCandles());

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                    // Showing the pop up window
                    builder.setView(dialogView);
                    builder.setCancelable(true);
                    builder.show();


                    endTransaction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.setTitle("End Transaction")
                                    .setMessage("Are you sure?")
                                    .setCancelable(true)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            // Getting the time order
                                            Calendar calendar = Calendar.getInstance();
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
                                            String date = simpleDateFormat.format(calendar.getTime());

                                            // Storing the time received in the pickups database
                                            FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID)
                                                    .child("timereceived").setValue(date);

                                            // Passing the pickup data in total sales database
                                            FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID)
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                            OrderCakeID_GlobalVariable order = snapshot.getValue(OrderCakeID_GlobalVariable.class);

                                                            FirebaseDatabase.getInstance().getReference("Total Sales").child(popupID)
                                                                    .setValue(order);

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                            // Passing the pickup data in users purchase history database
                                            FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID)
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                            OrderCakeID_GlobalVariable order = snapshot.getValue(OrderCakeID_GlobalVariable.class);

                                                            FirebaseDatabase.getInstance().getReference("User").child(uid).child("My Purchases").child(popupID)
                                                                    .setValue(order);

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                            // Changing the status in my purchases databse from pending to received
                                            FirebaseDatabase.getInstance().getReference("User").child(uid).child("My Purchases").child(popupID)
                                                    .child("orderstatus").setValue("Received");

                                            // Changing the status from to pickup to received
                                            FirebaseDatabase.getInstance().getReference("User").child(uid).child("My Orders").child("Custom Order")
                                                    .child("Cup Cake").child(popupID).removeValue();

                                            // Deleting in pickup data from pickup database
                                            FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID).removeValue();

                                            Toast.makeText(context, "Transaction has been completed", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, AdminPickupCustom.class);
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
                else {

                    // Setting up the pop up window
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.popup_pickupnormal, null);

                    ImageView itemImage;
                    TextView orderID, itemName, userName, userContact, itemQuantity, itemTotal, itemDescription;

                    AppCompatButton endTransaction;
                    AlertDialog.Builder dialog;

                    endTransaction = dialogView.findViewById(R.id.btn_endTransaction);
                    dialog = new AlertDialog.Builder(context);

                    itemImage = dialogView.findViewById(R.id.ivItemImage);
                    orderID = dialogView.findViewById(R.id.tvOrderID);
                    itemName = dialogView.findViewById(R.id.tvItemName);
                    userName = dialogView.findViewById(R.id.tvUserName);
                    userContact = dialogView.findViewById(R.id.tvUserContact);
                    itemQuantity = dialogView.findViewById(R.id.tvItemQuantity);
                    itemTotal = dialogView.findViewById(R.id.tvItemTotal);
                    itemDescription = dialogView.findViewById(R.id.tvItemDescription);

                    FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    OrderCakeID_GlobalVariable order = snapshot.getValue(OrderCakeID_GlobalVariable.class);

                                    String ITEMIMAGE = order.getImageurl();

                                    Picasso.get().load(ITEMIMAGE).into(itemImage);
                                    orderID.setText(order.getOrderid());
                                    itemName.setText(order.getName());
                                    userName.setText(order.getUsername());
                                    userContact.setText(order.getUsercontact());
                                    itemQuantity.setText(order.getQuantity());
                                    itemTotal.setText(order.getPrice());
                                    itemDescription.setText(order.getDescription());

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                    // Showing the pop up window
                    builder.setView(dialogView);
                    builder.setCancelable(true);
                    builder.show();


                    endTransaction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialog.setTitle("End Transaction")
                                    .setMessage("Are you sure?")
                                    .setCancelable(true)
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            // Getting the time order
                                            Calendar calendar = Calendar.getInstance();
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
                                            String date = simpleDateFormat.format(calendar.getTime());

                                            // Storing the time received in the pickups database
                                            FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID)
                                                    .child("timereceived").setValue(date);

                                            // Passing the pickup data in total sales database
                                            FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID)
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                            OrderCakeID_GlobalVariable order = snapshot.getValue(OrderCakeID_GlobalVariable.class);

                                                            FirebaseDatabase.getInstance().getReference("Total Sales").child(popupID)
                                                                    .setValue(order);

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                            // Passing the pickup data in users purchase history database
                                            FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID)
                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                            OrderCakeID_GlobalVariable order = snapshot.getValue(OrderCakeID_GlobalVariable.class);

                                                            FirebaseDatabase.getInstance().getReference("User").child(uid).child("My Purchases").child(popupID)
                                                                    .setValue(order);

                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError error) {

                                                        }
                                                    });

                                            // Changing hte status in my purchases databse from pending to received
                                            FirebaseDatabase.getInstance().getReference("User").child(uid).child("My Purchases").child(popupID)
                                                    .child("orderstatus").setValue("Received");

                                            // Deleting the order data from normal orders
                                            FirebaseDatabase.getInstance().getReference("User").child(uid).child("My Orders").child("Normal Orders")
                                                    .child(popupID).removeValue();

                                            // Deleting in pickup data from pickup database
                                            FirebaseDatabase.getInstance().getReference("Orders").child("Pickups").child(popupID).removeValue();

                                            Toast.makeText(context, "Transaction has been completed", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(context, AdminPickupCustom.class);
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

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout card;
        TextView itemname, id, status, name, contact, quantity, total, orderTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.card_adminpickup);

            itemname = itemView.findViewById(R.id.tvItemName);
            id = itemView.findViewById(R.id.tvOrderID);
            status = itemView.findViewById(R.id.tvCustomStatus);
            name = itemView.findViewById(R.id.tvUname);
            contact = itemView.findViewById(R.id.tvUcontact);
            quantity = itemView.findViewById(R.id.tvQuantity);
            total = itemView.findViewById(R.id.tvTotal);
            orderTime = itemView.findViewById(R.id.tvOrderTime);

        }
    }
}
