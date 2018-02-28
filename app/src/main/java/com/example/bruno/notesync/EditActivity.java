package com.example.bruno.notesync;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    String message;
    EditText noteEdit;
    EditText Username;
    NoteElements noteElements ;
    DbHelper helper;


    public void write()
    {

         helper = new DbHelper(this);
       noteElements = new NoteElements(noteEdit.getText().toString(),"Bruno");
        helper.addNote(noteElements);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_layout);
        noteEdit= findViewById(R.id.textId);
       // Username =findViewById(R.id.editTextUsername);

      // NoteElements noteElements = new NoteElements("Here is a note", "here is a userna"," The id");

        //helper = new DbHelper(this);
        //helper = new DbHelper(this);




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
                write();
                String sting = noteElements.getNote();
                 Log.d("Is Saved $",sting);


            case R.id.discardItem:

                message = item.getTitle().toString();

                Intent itent = new Intent(getApplicationContext(),ShowActivity.class);
                startActivity(itent);



        }
        return super.onOptionsItemSelected(item);
    }
}

