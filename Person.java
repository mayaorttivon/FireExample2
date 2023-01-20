package com.example.fireexample2;

public class Person {
    String email;

    public Person()
    {
        this.email="";
    }
    public Person(String email)
    {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
