package com.example.bruno.notesync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bruno on 2/26/2018.
 */


public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "NoteSync1.db";

    // Contacts table name
    private static final String TABLE_NOTES = "Notes";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NOTE = "note";
    private static final String KEY_USERNAME = "username";


    String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NOTE + " TEXT,"
            + KEY_USERNAME + " TEXT" + ")";


    // String CreateDb = "create table Notes (note text, username text,id integer Primary Key )";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_NOTES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("drop table if exists TABLE_NOTES");
        onCreate(sqLiteDatabase);
    }


    public void addNote(NoteElements noteElements) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();

        Values.put(KEY_NOTE, noteElements.getNote());
        Values.put(KEY_USERNAME, noteElements.getUserName());

        long result = db.insert(TABLE_NOTES, null, Values);

        db.close();

    }

   /*
    public NoteElements getNote() {

        SQLiteDatabase db = this.getReadableDatabase();



        String[] projection = {KEY_ID,KEY_NOTE,KEY_USERNAME};

        Cursor cursor = db.query(TABLE_NOTES,projection,null,null,null,null,null);

        if (cursor !=null )
            cursor.moveToFirst();

            NoteElements elements = new NoteElements(cursor.getString(0),cursor.getString(1),(cursor.getString(2)));



        return elements;
    }*/


    /*  public Cursor getAllNotes(){
          SQLiteDatabase db = this.getReadableDatabase();
          return null;
      }*/
    public List<NoteElements> getAllNotes() {

        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {KEY_ID, KEY_NOTE, KEY_USERNAME};
        List<NoteElements> noteElements = new ArrayList<NoteElements>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NOTES, null);

        if (cursor.moveToFirst()) {
            do {
                NoteElements elment = new NoteElements();
                elment.setNote(cursor.getString(1));
                elment.setUserName(cursor.getString(2));

                noteElements.add(elment);

            } while (cursor.moveToNext());
        }

        return noteElements;
    }

    public int getNotesCont() {
        return 0;
    }

    public int uodateNote(NoteElements noteElements) {
        return 0;
    }


    public void deleteNote(NoteElements noteElements) {

    }
}
