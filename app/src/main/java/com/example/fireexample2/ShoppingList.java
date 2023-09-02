package com.example.fireexample2;

import java.util.ArrayList;

public class ShoppingList {
    String title;
    ArrayList<ShoppingItem> lst;

    public ShoppingList() {
        lst = new ArrayList<ShoppingItem>();
        //this item should be created by the app and not here
        ShoppingItem shoppingItem = new ShoppingItem("test", 3);
        lst.add(shoppingItem);
    }

    public ShoppingList(ArrayList<ShoppingItem> lst) {
        this.lst = lst;
    }

    public ArrayList<ShoppingItem> getLst() {
        return lst;
    }

    public void setLst(ArrayList<ShoppingItem> lst) {
        this.lst = lst;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
