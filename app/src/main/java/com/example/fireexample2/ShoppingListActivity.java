package com.example.fireexample2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

public class ShoppingListActivity extends AppCompatActivity implements View.OnClickListener {

    ListView lv;
    ShoppingItemAdapter adapter;
    Button btnAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        initViews();

       // DatabaseReference dbRef = DataManager.getDB().getReference("people");
       // dbRef.setValue(DataManager.people);
    }

    public void initViews()
    {

        Button btnAddItem = findViewById(R.id.btnAdd);
        btnAddItem.setOnClickListener(this);

        adapter = new ShoppingItemAdapter(this, 0, 0, DataManager.getLatestShoppingList());
        lv = findViewById(R.id.lvShoppingItems);
        lv.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,AddItem.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShoppingItem item = new ShoppingItem(data.getStringExtra("description"), Integer.parseInt(data.getStringExtra("amount")));
        adapter.add(item);
        adapter.notifyDataSetChanged();
        DatabaseReference dbRef = DataManager.getDB().getReference("people").child(DataManager.getLoggedInPersonIndex()+"").child("shoppingLists").child("0").child("lst");
        dbRef.setValue(DataManager.getLatestShoppingList());
    }
}