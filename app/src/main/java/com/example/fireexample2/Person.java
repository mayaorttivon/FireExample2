package com.example.fireexample2;

import java.util.ArrayList;

public class Person {
    String email;
    ArrayList<ShoppingList> shoppingLists;

    public ArrayList<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    public void setShoppingLists(ArrayList<ShoppingList> shoppingLists) {
        this.shoppingLists = shoppingLists;
    }

    public Person()
    {
        this.email="";
        this.shoppingLists = new ArrayList<>();
        shoppingLists.add(new ShoppingList());
    }
    public Person(String email)
    {
        this.email = email;
        this.shoppingLists = new ArrayList<>();
        shoppingLists.add(new ShoppingList());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
