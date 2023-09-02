package com.example.fireexample2;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DataManager {

    private static FirebaseAuth auth;
    private  static FirebaseDatabase db;

    public static ArrayList<Person> people;

    public static FirebaseAuth getAuth() {
        if( auth == null )
        {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }

    public static FirebaseDatabase getDB()
    {
        if( db == null )
        {
            db = FirebaseDatabase.getInstance();
        }
        return db;
    }


    public static void retrievePeople() {
        DatabaseReference dbRef = getDB().getReference("people");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<ArrayList<Person>> t = new GenericTypeIndicator<ArrayList<Person>>() {};
                DataManager.people = snapshot.getValue(t);
                if( people ==null )
                {
                    people = new ArrayList<Person>();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }


    public static void setPeople(ArrayList<Person> people) {
        DataManager.people = people;
    }


    public static Person getLoggedInPerson()
    {
        FirebaseUser user = getAuth().getCurrentUser();
        String email = user.getEmail();
        for (Person person:people)
        {
            if( person.getEmail().equals(email))
                return person;
        }
        return null;
    }


    public static int getLoggedInPersonIndex()
    {
        FirebaseUser user = getAuth().getCurrentUser();
        String email = user.getEmail();
        for (int i=0; i< people.size();i++)
        {
            if( people.get(i).getEmail().equals(email))
                return i;
        }
        return -1;
    }

    public static ArrayList<ShoppingItem> getLatestShoppingList()
    {
        return getLoggedInPerson().getShoppingLists().get(0).getLst();
    }

 /*   public static void authenticateAndCreatePerson(String email, String password)
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
//retrieve data
                                                   dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                       @Override
                                                       public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                           GenericTypeIndicator<ArrayList<Person>> t = new GenericTypeIndicator<ArrayList<Person>>() {};
                                                           ArrayList<Person> peopleTest = snapshot.getValue(t);
                                                           //   tvMessage.setText(String.valueOf(peopleTest.size()));
                                                           //    if( ly.getChildAt(ly.getChildCount()-1)!=tvMessage)
                                                           //         ly.addView(tvMessage);
                                                       }

                                                       @Override
                                                       public void onCancelled(@NonNull DatabaseError error) {

                                                       }
                                                   });
                                               } else {
                                                 //  Toast.makeText(MainActivity.this, task.getException().getClass().getName(), Toast.LENGTH_SHORT).show();
/*if( task.getException() instanceof FirebaseAuthUserCollisionException )
{
tvMessage.setText("User does not exist, if you are a new user' please sign-up");
ly.addView(tvMessage);
}*/

                                            /*       Log.d("User Auth", "User Creation failed");
                                               }
                                           }
                                       }

                );
// DatabaseReference dbref = db.getReference("people");


    }*/

}
