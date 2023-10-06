package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class createnotes extends AppCompatActivity {
    EditText createtitleofnote, createcontentofnote;
    FloatingActionButton savenote;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;                       // for using cloud firestore database
    ProgressBar progressbarofcreatenote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnotes);

        createtitleofnote = findViewById(R.id.createtitleofnote);
        createcontentofnote = findViewById(R.id.createcontentofnote);
        savenote = findViewById(R.id.savenote);
        progressbarofcreatenote= findViewById(R.id.progressbarofcreatenote);

        Toolbar toolbar = findViewById(R.id.toolbarofcreatenote);

        setSupportActionBar(toolbar);                                                        // it set's a toolbar at Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                              // for back button

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();


        savenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = createtitleofnote.getText().toString();      // title text stored in title variable
                String content = createcontentofnote.getText().toString();

                progressbarofcreatenote.setVisibility(View.VISIBLE);

                if(title.isEmpty() || content.isEmpty())
                {
                    Toast.makeText(createnotes.this, "both fields are required", Toast.LENGTH_SHORT).show();
                    progressbarofcreatenote.setVisibility(View.INVISIBLE);
                }
                else{
                    //firebase work
                    progressbarofcreatenote.setVisibility(View.VISIBLE);

                    DocumentReference documentReference = firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("mynotes").document();
                    Map<String,Object> note = new HashMap<>();
                    note.put("title", title);
                    note.put("content", content);

                    documentReference.set(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(createnotes.this, "note created successfully", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(createnotes.this, Notes_Activity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressbarofcreatenote.setVisibility(View.INVISIBLE);
                            Toast.makeText(createnotes.this, "failed to create notes", Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            }
        });


    }
}