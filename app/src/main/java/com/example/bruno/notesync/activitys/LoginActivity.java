package com.example.bruno.notesync.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bruno.notesync.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {


    EditText emailField;
    EditText passwordField;
    Button loginButton;
    TextView gotosignup;
    ConstraintLayout constraintLayout;

    private FirebaseAuth mAuth;

    static final String TAG = "Login Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
            startActivity(intent);
        }

        addListenerToLoginButton();
        addListenerToSignUpButton();
    }

    private void addListenerToSignUpButton() {
        gotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addListenerToLoginButton() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(emailField.getText().toString(), passwordField.getText().toString());
            }
        });
    }

    private void initViews() {
        loginButton = findViewById(R.id.buttonSignIn);
        emailField = findViewById(R.id.editTextEmail);
        passwordField = findViewById(R.id.editTextPassword);
        gotosignup = findViewById(R.id.textViewGoToSU);
        constraintLayout = findViewById(R.id.cordinator_edit);
    }


    public void signIn(String email, String password)

    {
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                                startActivity(intent);
                            } else {
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                              /*  Snackbar snackbar = Snackbar
                                        .make(constraintLayout, "Authentication failed." + task.getException().getMessage(), Snackbar.LENGTH_LONG);

                                snackbar.show();*/
                            }
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "FIll in the fields", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
