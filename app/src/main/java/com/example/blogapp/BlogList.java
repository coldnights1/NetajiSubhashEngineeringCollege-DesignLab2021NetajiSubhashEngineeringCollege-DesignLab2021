package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BlogList extends AppCompatActivity {
    RecyclerView rcl;
    List<item> listdata;
    ItemAdapter adapter;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_list);

        rcl=findViewById(R.id.rclView);
        rcl.setHasFixedSize(true);
        rcl.setLayoutManager(new LinearLayoutManager(this));
        listdata=new ArrayList<>();
        auth= FirebaseAuth.getInstance();

        final DatabaseReference nm= FirebaseDatabase.getInstance().getReference("ITEMS");
        nm.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot npsnapshot : dataSnapshot.getChildren()){
                        item l=npsnapshot.getValue(item.class);
                        listdata.add(l);
                    }
                    adapter=new ItemAdapter(listdata,BlogList.this);
                    rcl.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}