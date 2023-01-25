package com.example.fireexample2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

public class ShoppingListActivity extends AppCompatActivity {

    ListView lv;
    ShoppingItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        adapter = new ShoppingItemAdapter(this, 0, 0, DataManager.getLatestShoppingList());
        lv = findViewById(R.id.lvShoppingItems);
        lv.setAdapter(adapter);
        DatabaseReference dbRef = DataManager.getDB().getReference("people");
        dbRef.setValue(DataManager.people);
    }
}