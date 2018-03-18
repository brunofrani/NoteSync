package com.example.bruno.notesync.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bruno.notesync.R;
import com.example.bruno.notesync.javaClasses.DbHelper;
import com.example.bruno.notesync.javaClasses.NoteElements;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {


   // static final int REQUEST_IMAGE_CAPTURE = 1;
    //static int nr = 0;
    private final String TagNAME = "Edit Avitivity";
    EditText noteEdit;
   // EditText Username;
   // ImageView picture;
    String message;
    CoordinatorLayout coordinatorLayout;
    NoteElements noteElements;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    DatabaseReference myRef;
    DatabaseReference ref;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
   // StorageReference imagesRef;
    String IntentUid;
    String uid;
    String note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);



       coordinatorLayout = findViewById(R.id.cordinator_edit);

        // firebaseDatabase = FirebaseDatabase.getInstance();
        // firebaseDatabase.setPersistenceEnabled(true);

        Intent intent = getIntent();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

      //  imagesRef = storageReference.child("images");
        IntentUid = intent.getStringExtra("uid");
        Log.d(TagNAME, "Intent value" + IntentUid);


        noteEdit = findViewById(R.id.textEdit);
        // Username =findViewById(R.id.editTextUsername);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("AllNotes").child("notes");
        uid = FirebaseAuth.getInstance().getUid();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        Toolbar tb1 = findViewById(R.id.toolbar);
        setSupportActionBar(tb1);


        if (IntentUid != null) {

            Log.d("TAG", "Intent has data.");
            ref = database.getReference("AllNotes").child("notes").child(IntentUid);
            // Attach a listener to read the data at our posts reference
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    NoteElements noteElements1 = new NoteElements();
                    noteElements1 = dataSnapshot.getValue(NoteElements.class);
                    noteEdit.setText((CharSequence) noteElements1.getNote());
                    //
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });

        }

    }



    public void writeToFirebase() {
        note = noteEdit.getText().toString();
        Log.d(TagNAME, "note content " + note);

        String email = firebaseUser.getEmail();
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Log.d(TagNAME, "on write to firebase");
        if (!note.isEmpty() && IntentUid == null) {
            Log.d(TagNAME, "notetext is not empty.");
            String Uid = myRef.push().getKey();
            noteElements = new NoteElements();
            noteElements.setUid(Uid);
            noteElements.setEmail(email);
            noteElements.setDate(currentDateTimeString);
            noteElements.setNote(note);
            Log.d(TagNAME, "noteelements uid " + Uid);
            myRef.child(Uid).setValue(noteElements);

            Log.d(TagNAME, "db reference" + myRef.toString());

            Intent itent = new Intent(getApplicationContext(), ShowActivity.class);
            startActivity(itent);

        } else if (IntentUid != null) {
            note = noteEdit.getText().toString();
            myRef.child(IntentUid).child("note").setValue(note);
            Log.d(TagNAME, "note updated ");
            Intent itent = new Intent(getApplicationContext(), ShowActivity.class);
            startActivity(itent);
        } else {
           /* Toast.makeText(getApplicationContext(), "Add some text ",
                    Toast.LENGTH_SHORT).show();*/
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Note Is Empty", Snackbar.LENGTH_LONG);

            snackbar.show();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.saveItem:
                message = item.getTitle().toString();
                writeToFirebase();
                break;

            case R.id.discardItem:

                if (!noteEdit.getText().toString().isEmpty()) {
                    noteEdit.getText().clear();
                    Snackbar.make(coordinatorLayout, "Note Cleared", Snackbar.LENGTH_LONG).show();
                    dismisKeyboard();
                }else
                    {
                        Snackbar.make(coordinatorLayout, "Note Is Empty", Snackbar.LENGTH_LONG).show();
                        dismisKeyboard();
                    }
                break;


        }


        return super.onOptionsItemSelected(item);

    }
    private void dismisKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
    }


