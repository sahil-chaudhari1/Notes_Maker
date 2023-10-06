package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.tracing.FirebaseTrace;

public class MainActivity extends AppCompatActivity {

    EditText login_mail, login_password;
    RelativeLayout loginButton, signup_button1;
    TextView goToForget_password;
    FirebaseAuth firebaseAuth;
    ProgressBar progressbarofmainactivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        login_mail = findViewById(R.id.login_mail);
        login_password = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.loginButton);
        signup_button1 = findViewById(R.id.signupButton1);
        goToForget_password = findViewById(R.id.goToForget_password);
        progressbarofmainactivity = findViewById(R.id.progressbarofmainactivity);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser!=null)                  // if user already exist then directly open Notes_Activity no need to again login
        {
            finish();
            startActivity(new Intent(MainActivity.this, Notes_Activity.class));
        }

        signup_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Activity_signUp.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = login_mail.getText().toString().trim();
                String password = login_password.getText().toString().trim();

                if(mail.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<7)
                {
                    Toast.makeText(MainActivity.this, "password should greater than 7", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //login firebase
                    progressbarofmainactivity.setVisibility(view.VISIBLE);
                   firebaseAuth.signInWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful())
                           {
                               checkVerification();
                           }
                           else
                           {
                               Toast.makeText(MainActivity.this, "Accout doen't exist", Toast.LENGTH_SHORT).show();
                               progressbarofmainactivity.setVisibility(view.INVISIBLE);
                           }
                       }
                   });

                }
            }
        });

        goToForget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Forget_password.class));
            }
        });
    }

    private void checkVerification()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser.isEmailVerified()==true)
        {
            Toast.makeText(this, "Log In", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this, Notes_Activity.class));
        }
        else {
            progressbarofmainactivity.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Check email or password", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

}