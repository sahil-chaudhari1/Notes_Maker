package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_signUp extends AppCompatActivity {

    EditText signup_mail, signup_password;
    RelativeLayout signup_button2;
    TextView signup_to_login;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();
        signup_mail = findViewById(R.id.signup_mail);
        signup_password = findViewById(R.id.signup_password);
        signup_button2 = findViewById(R.id.signup_button2);
        signup_to_login = findViewById(R.id.signupToLogin);

        firebaseAuth = FirebaseAuth.getInstance();

        signup_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_signUp.this, MainActivity.class);
                startActivity(intent);
            }
        });

        signup_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = signup_mail.getText().toString().trim();
                String password = signup_password.getText().toString().trim();

                if(mail.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(Activity_signUp.this, "Please Enter all credentials", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<7)
                {
                    Toast.makeText(Activity_signUp.this, "password should greater than 7", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(Activity_signUp.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                sentEmailVerification();
                            }
                            else
                            {
                                Toast.makeText(Activity_signUp.this, "Registrastion Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    //send email verification
    private void sentEmailVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(Activity_signUp.this, "verification email is sent, varify and log in", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(Activity_signUp.this, MainActivity.class));
                }
            });
        }
        else
        {
            Toast.makeText(this, "varification failed", Toast.LENGTH_SHORT).show();
        }
    }
}