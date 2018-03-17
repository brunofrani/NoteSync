package com.example.bruno.notesync.activitys;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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


    static final int REQUEST_IMAGE_CAPTURE = 1;
    static int nr = 0;
    private final String TagNAME = "Edit Avitivity";
    EditText noteEdit;
    EditText Username;
    ImageView picture;
    String message;
    NoteElements noteElements;
    DbHelper helper;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser firebaseUser;
    DatabaseReference myRef;
    DatabaseReference ref;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    StorageReference imagesRef;
    String IntentUid;
    String uid;
    String note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);

        picture = findViewById(R.id.imageViewPicture);

        picture.setVisibility(View.INVISIBLE);

        // firebaseDatabase = FirebaseDatabase.getInstance();
        // firebaseDatabase.setPersistenceEnabled(true);

        Intent intent = getIntent();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        imagesRef = storageReference.child("images");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Bundle extras = data.getExtras();
            //  Bitmap imageBitmap = (Bitmap) extras.get("data");
            // picture.setImageBitmap(imageBitmap);
            //  picture.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "picture saved",
                    Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(), "Add some text ",
                    Toast.LENGTH_SHORT).show();
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


            case R.id.openCamera:


                if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)

                {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                }

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                File filedirectory = Environment.getExternalStorageDirectory();
                Log.d(TagNAME, "directory content " + filedirectory.toString());
                File dir = new File(filedirectory.getAbsolutePath(), "/NoteSync");

                if (!dir.exists()) {
                    dir.mkdir();
                }
                String FileName = getFileName();
                File file = new File(dir, FileName);
                Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName(), file);
                Log.d(TagNAME, "uri content " + photoUri.toString());

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);


        }


        return super.onOptionsItemSelected(item);

    }

    private String getFileName() {
        String email = firebaseUser.getEmail();
        String DateTime = DateFormat.getDateTimeInstance().format(new Date());
        String Filename = email + DateTime;
        SimpleDateFormat sfd = new SimpleDateFormat("yyMMdd");
        String date = sfd.format(new Date());
        return date;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


                    // File filedirectory = Environment.getExternalStorageDirectory();
                    // Log.d(TagNAME, "directory content "+filedirectory.toString());
                    String filedirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
                    File dir = new File(filedirectory, "/NoteSync");
                    Log.d(TagNAME, "uri content " + dir.toString());
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    String FileName = getFileName();
                    File file = new File(dir, FileName);
                    Uri photoUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName(), file);
                    Log.d(TagNAME, "uri content " + photoUri.toString());

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied",
                            Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


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


}


