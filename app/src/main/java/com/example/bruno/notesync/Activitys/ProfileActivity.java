package com.example.bruno.notesync.Activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bruno.notesync.Activitys.LoginActivity;
import com.example.bruno.notesync.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ProfileActivity extends AppCompatActivity {


    EditText name;
    TextView email;

    ImageView imageView;
    TextView signout;

    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.textShowEmail);

        name.setEnabled(false);
        email.setEnabled(false);
        // uid = findViewById(R.id.textViewEmail);
        imageView = findViewById(R.id.imageViewProfile);
        signout = findViewById(R.id.textViewSignOut);

        user = FirebaseAuth.getInstance().getCurrentUser();


        name.setText(user.getDisplayName());


        email.setText(user.getEmail());

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                setDisplayName("my name is ");
                name.setText(user.getDisplayName());
            }
        });

        // name.setText(user.getDisplayName());

        // uid.setText(user.getUid());
        email.setText(user.getEmail());


        // NoteElements noteElements = new NoteElements("Here is a note", "here is a userna"," The id");

        //helper = new DbHelper(this);
        //helper = new DbHelper(this);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    public void setDisplayName(String display)

    {


        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(display)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "User profile updated.");
                        }
                    }
                });

    }
}
