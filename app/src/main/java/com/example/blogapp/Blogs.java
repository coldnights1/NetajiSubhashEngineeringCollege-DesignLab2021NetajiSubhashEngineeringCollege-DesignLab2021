package com.example.blogapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Blogs extends AppCompatActivity {

    Button makeBlog,goToBlog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blogs);

        makeBlog=findViewById(R.id.makeBlog);
        goToBlog=findViewById(R.id.blogList);

        makeBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Blogs.this,MakeBlog.class);
                startActivity(intent);
            }
        });

        goToBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Blogs.this,BlogList.class);
                startActivity(intent);
            }
        });


    }
}