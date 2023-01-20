package com.example.fireexample2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    FirebaseDatabase db;
    EditText etEmail;
    EditText etPassword;
    Button btnSignIn;
    Button btnSignUp;
    TextView tvMessage;
    LinearLayout ly;
    ArrayList<Person> alp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        tvMessage = new TextView(this);
//tvMessage.setHeight(60);
        tvMessage.setTextSize(20);
//tvMessage.setWidth(300);

        ly = findViewById(R.id.lyMain);

//alp = new ArrayList<Person>();
        DatabaseReference dbRef = db.getReference("people");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<Person>> t = new GenericTypeIndicator<ArrayList<Person>>() {};
                alp = snapshot.getValue(t);
                if( alp != null ) {
                    tvMessage.setText(String.valueOf(alp.size()));
                    if (ly.getChildAt(ly.getChildCount() - 1) != tvMessage)
                        ly.addView(tvMessage);
                }
                else
                {
                    alp = new ArrayList<Person>();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if( view == btnSignIn )
        {
            auth.signInWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                               @Override
                                               public void onComplete(@NonNull Task<AuthResult> task) {
                                                   if (task.isSuccessful()) {
                                                       Log.d("User Auth", "User signed in Successfully");
                                                       Toast.makeText(MainActivity.this, "Successful sign-in", Toast.LENGTH_SHORT).show();
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
            auth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                               @Override
                                               public void onComplete(@NonNull Task<AuthResult> task) {
                                                   if (task.isSuccessful()) {
                                                       Log.d("User Auth", "User signed in Successfully");
                                                       Toast.makeText(MainActivity.this, "Successful sign-up", Toast.LENGTH_SHORT).show();
                                                       Person person = new Person(etEmail.getText().toString());
                                                       alp.add(person);
                                                       DatabaseReference dbRef = db.getReference("people");
                                                       dbRef.setValue(alp);
//retrieve data
                                                       dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                           @Override
                                                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                               GenericTypeIndicator<ArrayList<Person>> t = new GenericTypeIndicator<ArrayList<Person>>() {};
                                                               ArrayList<Person> peopleTest = snapshot.getValue(t);
                                                               tvMessage.setText(String.valueOf(peopleTest.size()));
                                                               if( ly.getChildAt(ly.getChildCount()-1)!=tvMessage)
                                                                   ly.addView(tvMessage);
                                                           }

                                                           @Override
                                                           public void onCancelled(@NonNull DatabaseError error) {

                                                           }
                                                       });
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
}