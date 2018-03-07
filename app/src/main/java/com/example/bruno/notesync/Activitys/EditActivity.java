package com.example.bruno.notesync.Activitys;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.example.bruno.notesync.JavaClasses.DbHelper;
import com.example.bruno.notesync.JavaClasses.NoteElements;
import com.example.bruno.notesync.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {


    EditText noteEdit;
    EditText Username;

    static int nr = 0;

    String message;
    NoteElements noteElements ;
    DbHelper helper;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    DatabaseReference myRef;
    DatabaseReference ref;


    String IntentUid;
    String uid;
    String note;

    private final String TagNAME = "Edit Avitivity";


   /* public void write()
    {

         note =noteEdit.getText().toString();

         helper = new DbHelper(this);
        if(!note.isEmpty())
        {
       noteElements = new NoteElements(note,"Bruno");
        helper.addNote(noteElements);
        }else
            {
                Toast.makeText(getApplicationContext(), "Add some text " ,
                        Toast.LENGTH_SHORT).show();
            }



    }*/


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
            //  nr ++;
            //  finish();

        } else if (IntentUid != null) {
            note = noteEdit.getText().toString();
            myRef.child(IntentUid).child("note").setValue(note);
            Log.d(TagNAME, "note updated ");
            Intent itent = new Intent(getApplicationContext(), ShowActivity.class);
            startActivity(itent);
        } else {
            Toast.makeText(getApplicationContext(), "Add some text ",
                    Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        // firebaseDatabase = FirebaseDatabase.getInstance();
        // firebaseDatabase.setPersistenceEnabled(true);

        Intent intent = getIntent();
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

            Log.d("TAG", "INtent has data.");
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






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

            case R.id.saveItem:
                message = item.getTitle().toString();
               // NoteElements noteElements = new NoteElements("Here is a note", "here is a userna"," The id");
               // DbHelper helper = new DbHelper(this);
               // helper.addNote(noteElements);


                //String sting = noteElements.getNote();
               // Log.d("Is Saved $",sting);
                // write();
                writeToFirebase();


                break;

            case R.id.discardItem:

                noteEdit.getText().clear();

                Toast.makeText(getApplicationContext(), "Note Discarded",
                        Toast.LENGTH_SHORT).show();
                break;






        }
        return super.onOptionsItemSelected(item);
    }


}

