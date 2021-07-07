package com.example.blogapp;

public class item {
    //id name price description sellerid
    String id;
    String name;

    String description;
    String url;
    String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public item() {
    }

    public item(String id, String name,  String description,String uid ,String url) {
        this.id = id;
        this.name = name;
        this.uid=uid;
        this.description = description;
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}