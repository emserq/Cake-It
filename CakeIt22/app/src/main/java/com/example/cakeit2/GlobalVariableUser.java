package com.example.cakeit2;

public class GlobalVariableUser {
    public String name, address, contact, email, password;

    public GlobalVariableUser(String name, String address, String contact, String email, String password) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.email = email;
        this.password = password;
    }

    public GlobalVariableUser(){

    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
