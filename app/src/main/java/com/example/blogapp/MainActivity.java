package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button btn,login2;
    EditText email,pass,username;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.register);

        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        login2=findViewById(R.id.login2);
        username=findViewById(R.id.username);

        sharedPreferences = getSharedPreferences("UserDetail",MODE_PRIVATE);
        myEdit = sharedPreferences.edit();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("USERS");//pointer to database


        if(auth.getCurrentUser()!=null)
        {
            Intent in =new Intent(MainActivity.this,Blogs.class);
            startActivity(in);
            finish();
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailid=email.getText().toString();
                String passw=pass.getText().toString();
                String usern=username.getText().toString();
                RegisterUser(emailid,passw,usern);
            }
        });
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Login.class);
                startActivity(i);
            }
        });

    }

    private void RegisterUser(String emailid, String passw,String usern) {
        auth.createUserWithEmailAndPassword(emailid,passw).addOnCompleteListener(MainActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "registration sucessful", Toast.LENGTH_SHORT).show();
                    String uid = auth.getUid();
                    User obj = new User(usern, emailid, uid);
                    //code to add usern,emailid and uid to database
                    myRef.child(uid).setValue(obj);
                    myEdit.putString("name", usern);
                    myEdit.commit();

                }
                else{
                    Toast.makeText(MainActivity.this,"registration failed",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}