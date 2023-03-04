package com.example.myfirebaserecycler;


import static com.example.myfirebaserecycler.R.id.recyclerView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private EditText name, desc, price;
    private ImageView img;
    private Button submit, view,choose;
    private FirebaseDatabase mdatabase;
    private DatabaseReference mref;
    private FirebaseStorage mstorage;
    private  Uri ImageUri;


    ProgressDialog progressDialog;


    private static int PICK_IMAGE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name= findViewById(R.id.name);
        desc= findViewById(R.id.description);
        price= findViewById(R.id.price);
        img= findViewById(R.id.photo);
        submit= findViewById(R.id.btnsubmit);
        view= findViewById(R.id.btnview);
        choose= findViewById(R.id.choose);
        progressDialog= new ProgressDialog(this);

        //Firebase database initialisation
        mdatabase=FirebaseDatabase.getInstance();
        mref= mdatabase.getReference().child("Uploads");
        mstorage= FirebaseStorage.getInstance();



        // implementing the button for choosing the image
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilceChooser();


            }
        });
        // implementing the button for submitting the image to firebase
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = name.getText().toString().trim();
                String description = desc.getText().toString();
                String Price =price.getText().toString();


                final StorageReference reference = mstorage.getReference().
                        child(System.currentTimeMillis() + "");
                Toast.makeText(MainActivity.this, ImageUri.toString(), Toast.LENGTH_SHORT).show();
                reference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                  reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                      @Override
                      public void onSuccess(Uri uri) {
                          progressDialog.setTitle("Uploading...");
                          progressDialog.show();
                          productModal modal = new productModal();
                          modal.setProductImage(uri.toString());
                          modal.setName(Name);
                          modal.setDescription(description);
                          modal.setPrice(Price);
                          mdatabase.getReference().child("Products").push().setValue(modal).
                                  addOnSuccessListener(new OnSuccessListener<Void>() {
                                      @Override
                                      public void onSuccess(Void unused) {
                                          Toast.makeText(MainActivity.this, "Product Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                          progressDialog.dismiss();
                                      }
                                  }).addOnFailureListener(new OnFailureListener() {
                                      @Override
                                      public void onFailure(@NonNull Exception e) {
                                          Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                      }
                                  });

                      }
                  });
                    }
                });

            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), productDisplay.class);
                startActivity(intent);

            }
        });
    }
// method for inserting to the firebase database
    // implementing the button for viewing the image
    private void openFilceChooser() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            ImageUri = data.getData();
            img = (ImageView) findViewById(R.id.photo);
            img.setImageURI(ImageUri);



        }
    }
}