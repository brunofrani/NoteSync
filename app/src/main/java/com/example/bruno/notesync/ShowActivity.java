package com.example.bruno.notesync;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {


    public void read() {

      /*  DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

           String Projection[] ={"note"};

        Cursor cursor =   database.query("Notes",Projection,null,null,null,null,null);

        cursor.moveToFirst();
        textShow.setText(cursor.getString(0));*/
        //noteList.clear();
        noteList = new ArrayList<NoteElements>();


        noteList.add(new NoteElements("Note", "bruno"));
        //noteList = (ArrayList<NoteElements>) helper.getAllNotes();


    }

    DbHelper helper;

    ArrayList<NoteElements> noteList;
    Adapter Adapter1;

    GridView gridView;


    Button show;
    TextView textShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);
        show = findViewById(R.id.buttonShow);
        // textShow = findViewById(R.id.textViewShow);

        helper = new DbHelper(this);


        gridView = findViewById(R.id.gridview);

      /* noteList= new ArrayList<NoteElements>();


        noteList.add(new NoteElements("Note","bruno"));
        noteList.add(new NoteElements("Note","bruno"));*/


        noteList = (ArrayList<NoteElements>) helper.getAllNotes();

        Adapter1 = new Adapter(this, noteList);
        gridView.setAdapter(Adapter1);
        Adapter1.notifyDataSetChanged();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Toast.makeText(getApplicationContext(), "" + i, Toast.LENGTH_SHORT).show();
            }
        });


        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                read();

            }
        });
    }
}
