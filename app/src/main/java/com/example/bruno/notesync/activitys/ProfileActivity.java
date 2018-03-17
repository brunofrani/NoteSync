package com.example.bruno.notesync.activitys;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bruno.notesync.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileActivity extends AppCompatActivity {


    TextView email;
    ImageView imageView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        email = findViewById(R.id.textShowEmail);
        email.setEnabled(false);
        imageView = findViewById(R.id.imageViewProfile);

        user = FirebaseAuth.getInstance().getCurrentUser();
        email.setText(user.getEmail());
        email.setText(user.getEmail());

    }


}
