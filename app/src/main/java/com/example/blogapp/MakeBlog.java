package com.example.blogapp;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class MakeBlog extends AppCompatActivity {
    Button Itempage;
    Button submit;
    Uri mImageUri;//stores the path of image
    EditText des;
    StorageReference mStorageRef ;

    EditText pr;
    EditText na;
    Button logout;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference myref;
    ImageView img;
    String imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_blog);

        logout=findViewById(R.id.logout);
        submit=findViewById(R.id.submit);
        mStorageRef= FirebaseStorage.getInstance().getReference("ItemImages");
        des=findViewById(R.id.description);

        img =findViewById(R.id.itemimage);
        na=findViewById(R.id.name);
        Intent x=getIntent();


        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfilechooser();

            }
        });
        auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myref = database.getReference("ITEMS");


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent in=new Intent(MakeBlog.this,Login.class);
                startActivity(in);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadfile();
            }
        });
    }

    private void openfilechooser() {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,1);//if request code is 1,then our app is safe
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            mImageUri=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri);
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "error in choosing", Toast.LENGTH_SHORT).show();
            }
        }


    }
    private String getFileExtension(Uri uri)
    {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadfile() {
        StorageReference fileReference =mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
        fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!urlTask.isSuccessful());
                Uri downloadUrl = urlTask.getResult();
                final String sdownload_url = String.valueOf(downloadUrl);
                imageUri=String.valueOf(downloadUrl);
                Log.d("Check",imageUri+"");
                submitItem(imageUri);

            }

        });
    }

    private void submitItem(String imageUri) {
        String descrip=des.getText().toString();

        String name=na.getText().toString();
        String uniqueId = myref.push().getKey();
        Log.d("Check2",imageUri+"");

        item i =new item(uniqueId,name, descrip,auth.getUid() ,imageUri);
        myref.child(uniqueId).setValue(i).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(),"Succesfully posted",Toast.LENGTH_SHORT).show();
            }
        });
    }


}