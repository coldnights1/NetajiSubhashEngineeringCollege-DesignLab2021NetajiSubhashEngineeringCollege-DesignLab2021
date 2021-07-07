package com.example.blogapp;


public class User {
    String username;
    String email;
    String userid;//primary key

    public User(String username, String email, String userid) {
        this.username = username;
        this.email = email;
        this.userid = userid;
    }

    public User(){

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}