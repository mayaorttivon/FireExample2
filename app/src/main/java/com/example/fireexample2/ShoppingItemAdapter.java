package com.example.fireexample2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ShoppingItemAdapter extends ArrayAdapter<ShoppingItem> {

    Context context;
    List<ShoppingItem> objects;

    public ShoppingItemAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<ShoppingItem> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_layout, parent, false);
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvAmount = view.findViewById(R.id.tvAmount);
        ShoppingItem shoppingItem = objects.get(position);
        tvName.setText(shoppingItem.getDesc());
        tvAmount.setText(String.valueOf(shoppingItem.getAmount()));
        return view;
    }
}
