package com.example.cakeit2;

public class OrderCakeID_GlobalVariable {

    String username, usercontact, useraddress,  imageurl, name, description, quantity, price, orderid, orderstatus, uid, timeorder, timereceived;

    public OrderCakeID_GlobalVariable(String username, String usercontact, String useraddress, String imageurl, String name, String description, String quantity, String price, String orderid, String orderstatus, String uid, String timeorder, String timereceived) {
        this.username = username;
        this.usercontact = usercontact;
        this.useraddress = useraddress;
        this.imageurl = imageurl;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.orderid = orderid;
        this.orderstatus = orderstatus;
        this.uid = uid;
        this.timeorder = timeorder;
        this.timereceived = timereceived;
    }

    public OrderCakeID_GlobalVariable() {
    }

    public String getUsername() {
        return username;
    }

    public String getUsercontact() {
        return usercontact;
    }

    public String getUseraddress() {
        return useraddress;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public String getOrderid() {
        return orderid;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public String getUid() {
        return uid;
    }

    public String getTimeorder() {
        return timeorder;
    }

    public String getTimereceived() {
        return timereceived;
    }
}
