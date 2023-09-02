package com.example.fireexample2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    EditText etEmail;
    EditText etPassword;
    Button btnSignIn;
    Button btnSignUp;
    TextView tvMessage;
    Button btnContinue;
    LinearLayout ly;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        DataManager.retrievePeople();

      //  tvMessage.setText(String.valueOf(DataManager.getPeople().size()));
      //  ly.addView(tvMessage);
    }

    public void initViews()
    {
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);


        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        tvMessage = new TextView(this);
        tvMessage.setTextSize(20);

        btnContinue = new Button(this);
        btnContinue.setText("Successful Sign-In! Click to Continue...");
        btnContinue.setOnClickListener(this);

        ly = findViewById(R.id.lyMain);
    }
    @Override
    public void onClick(View view) {
        if( view == btnSignIn )
        {
            DataManager.getAuth().signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                               @Override
                                               public void onComplete(@NonNull Task<AuthResult> task) {
                                                   if (task.isSuccessful()) {
                                                       Log.d("User Auth", "User signed in Successfully");
                                                       Toast.makeText(MainActivity.this, "Successful sign-in", Toast.LENGTH_SHORT).show();
                                                       ly.addView(btnContinue);

                                                   } else {
                                                       Toast.makeText(MainActivity.this, task.getException().getClass().getName(), Toast.LENGTH_SHORT).show();
                                                       if( task.getException() instanceof FirebaseAuthInvalidUserException )
                                                       {
                                                           tvMessage.setText("User does not exist, if you are a new user please sign-up");
                                                           ly.addView(tvMessage);
                                                       }

                                                       Log.d("User Auth", "User Creation failed");
                                                   }
                                               }
                                           }

                    );
        }
        else if( view == btnSignUp )
        {
           authenticateAndCreatePerson(etEmail.getText().toString(), etPassword.getText().toString());
        }
        else if( view == btnContinue )
        {
            Intent serviceIntent = new Intent(getApplicationContext(), DataChangesService.class);
            startService(serviceIntent);
            Intent intent = new Intent(this, ShoppingListActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void authenticateAndCreatePerson(String email, String password)
    {
        DataManager.getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                           @Override
                                           public void onComplete(@NonNull Task<AuthResult> task) {
                                               if (task.isSuccessful()) {
                                                   Log.d("User Auth", "User signed in Successfully");
                                                   //  Toast.makeText(MainActivity.this, "Successful sign-up", Toast.LENGTH_SHORT).show();
                                                   Person person = new Person(email);
                                                   DataManager.people.add(person);
                                                   DatabaseReference dbRef = DataManager.getDB().getReference("people");
                                                   dbRef.setValue(DataManager.people);
                                                   ly.addView(btnContinue);
//retrieve data
                                                   /*dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                       @Override
                                                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                           GenericTypeIndicator<ArrayList<Person>> t = new GenericTypeIndicator<ArrayList<Person>>() {};
                                                           ArrayList<Person> peopleTest = snapshot.getValue(t);
                                                           DataManager.setPeople(peopleTest);
                                                           ly.addView(btnContinue);
                                                           //   tvMessage.setText(String.valueOf(peopleTest.size()));
                                                           //    if( ly.getChildAt(ly.getChildCount()-1)!=tvMessage)
                                                           //         ly.addView(tvMessage);
                                                       }

                                                       @Override
                                                       public void onCancelled(@NonNull DatabaseError error) {

                                                       }
                                                   });*/
                                               } else {
                                                     Toast.makeText(MainActivity.this, task.getException().getClass().getName(), Toast.LENGTH_SHORT).show();
/*if( task.getException() instanceof FirebaseAuthUserCollisionException )
{
tvMessage.setText("User does not exist, if you are a new user' please sign-up");
ly.addView(tvMessage);
}*/

                                                   Log.d("User Auth", "User Creation failed");
                                               }
                                           }
                                       }

                );
// DatabaseReference dbref = db.getReference("people");


    }

}