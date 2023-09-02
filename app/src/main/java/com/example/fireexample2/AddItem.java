package com.example.fireexample2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItem extends AppCompatActivity implements View.OnClickListener {

    Button btnSaveItem;
    EditText etDescription;
    EditText etAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        initViews();
    }

    public void initViews()
    {
        btnSaveItem = findViewById(R.id.btnSaveItem);
        btnSaveItem.setOnClickListener(this);
        etDescription = findViewById(R.id.etDesciprion);
        etAmount = findViewById(R.id.etAmount);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("description", etDescription.getText().toString() );
        intent.putExtra("amount", etAmount.getText().toString() );
        setResult(RESULT_OK, intent);
        finish();
    }
}