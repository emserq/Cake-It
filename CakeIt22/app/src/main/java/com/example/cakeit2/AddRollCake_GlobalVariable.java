package com.example.cakeit2;

public class AddRollCake_GlobalVariable {

    String image, name, description, price, cakeID;

    public AddRollCake_GlobalVariable(String image, String name, String description, String price, String cakeID) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.price = price;
        this.cakeID = cakeID;
    }


    public AddRollCake_GlobalVariable() {
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getCakeID() {
        return cakeID;
    }
}
