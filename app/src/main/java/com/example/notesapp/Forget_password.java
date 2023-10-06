package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forget_password extends AppCompatActivity {
    private EditText password_forgot;
    private Button passwordRecoverButton;
    private TextView go_back_to_login;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        getSupportActionBar().hide();
        password_forgot = findViewById(R.id.password_forgot);
        passwordRecoverButton = findViewById(R.id.passwordRecoverButton);
        go_back_to_login = findViewById(R.id.go_back_to_login);
        firebaseAuth = FirebaseAuth.getInstance();

        go_back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forget_password.this, MainActivity.class);
                startActivity(intent);
            }
        });

        passwordRecoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = password_forgot.getText().toString().trim();
                if(mail.isEmpty())
                {
                    Toast.makeText(Forget_password.this, "Please enter the mail id", Toast.LENGTH_SHORT).show();
                }
                else{
                    // firebase work
                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Forget_password.this, "mail is sent, you can reset password", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Forget_password.this, MainActivity.class));
                            }
                            else {
                                Toast.makeText(Forget_password.this, "email is wrong or not exiting user", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

}