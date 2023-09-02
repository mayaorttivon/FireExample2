package com.example.fireexample2;

public class ShoppingItem {
    String desc;
    int amount;

    public ShoppingItem() {
    }

    public ShoppingItem(String desc, int amount) {
        this.desc = desc;
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
