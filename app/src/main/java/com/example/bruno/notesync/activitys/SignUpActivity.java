package com.example.bruno.notesync.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bruno.notesync.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    EditText password;
    EditText email;
    Button signup;
    FirebaseAuth auth;
    CoordinatorLayout coordinatorLayout;
    static final String TAG = "in app tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.editTextEmailSignUp);
        password = findViewById(R.id.editTextPasswordSignUp);
        signup = findViewById(R.id.buttonSignUp);
        auth = FirebaseAuth.getInstance();
        coordinatorLayout = findViewById(R.id.coordinator_signUp);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser(email.getText().toString(), password.getText().toString());
                dismissKeyboard();
            }
        });
    }

    private void dismissKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void createUser(String email, String password) {

        if(!email.isEmpty()&&!password.isEmpty()){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                            intent.putExtra("username", user.getDisplayName());
                            startActivity(intent);

                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Snackbar snackbar = Snackbar
                                    .make(coordinatorLayout, task.getException().getMessage(), Snackbar.LENGTH_LONG);

                            snackbar.show();
                        }
                    }
                });
        }else
                    {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Fill In The Fields", Snackbar.LENGTH_LONG);

                        snackbar.show();
                        dismissKeyboard();
                    }
    }
}