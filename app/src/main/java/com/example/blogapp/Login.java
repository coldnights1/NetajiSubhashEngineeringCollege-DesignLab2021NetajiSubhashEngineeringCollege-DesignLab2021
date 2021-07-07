package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    Button login;
    EditText pass1,email1;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Intent i =getIntent();
        pass1=findViewById(R.id.password1);
        email1=findViewById(R.id.email1);
        login=findViewById(R.id.login1);
        auth=FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailid=email1.getText().toString();
                String passw=pass1.getText().toString();
                if(!emailid.isEmpty() && !passw.isEmpty())
                    Loginuser(emailid,passw);
                else
                    Toast.makeText(Login.this,"invalid input",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Loginuser(String emailid, String passw) {
        auth.signInWithEmailAndPassword(emailid,passw).addOnCompleteListener(Login.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Login.this,"registration sucessful",Toast.LENGTH_SHORT).show();

                    System.out.println("registration successful");
                    Intent i= new Intent(Login.this,Blogs.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(Login.this,"registration failed",Toast.LENGTH_SHORT).show();
                    System.out.println("registration failed");

                }
            }
        });
    }

}