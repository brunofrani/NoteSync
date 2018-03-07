package com.example.bruno.notesync.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bruno.notesync.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {


    ImageView logoimage;
    EditText emailField;
    EditText passwordField;
    Button loginButton;
    TextView gotosignup;

    private FirebaseAuth mAuth;

    static final String TAG = "in app tag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.buttonSignIn);

        emailField = findViewById(R.id.editTextEmail);
        passwordField = findViewById(R.id.editTextPassword);
        gotosignup = findViewById(R.id.textViewGoToSU);
        mAuth = FirebaseAuth.getInstance();


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
            startActivity(intent);
        } else {
            // No user is signed in
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if (!emailField.getText().toString().equals(null)&& !passwordField.getText().toString().equals(null)){
                signIn(emailField.getText().toString(), passwordField.getText().toString());
               /* else
                    {
                    Toast.makeText(getApplicationContext(),"FIll in the fields",Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        gotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

       /* FirebaseUser currentUser = mAuth.getCurrentUser();

        if (!currentUser.equals(null)){
            Intent intent = new Intent(getApplicationContext(),EditActivity.class);
            intent.putExtra("username",currentUser.getDisplayName());
            startActivity(intent);



        }*/

    }

    public void signIn(String email, String password)

    {
        if (!email.isEmpty() && !password.isEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(getApplicationContext(), ShowActivity.class);
                                // intent.putExtra("username",user.getDisplayName());
                                startActivity(intent);

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT).show();

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
