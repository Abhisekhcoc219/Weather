package com.example.weather;

public class User {
    private String name,email;
    User(String Name,String Emails){
        this.name=Name;
        this.email=Emails;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
