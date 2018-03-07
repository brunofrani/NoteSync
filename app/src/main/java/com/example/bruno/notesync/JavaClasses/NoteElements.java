package com.example.bruno.notesync.JavaClasses;

/**
 * Created by Bruno on 2/27/2018.
 */


public class NoteElements {

    private String Note;
    private String Email;
    private String Date;
    private String Uid;

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public NoteElements(String note, String email, String date, String uid) {
        Note = note;
        Email = email;
        Date = date;
        Uid = uid;
    }

    public String getNote() {

        return Note;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public void setNote(String note) {


        Note = note;
    }



    public NoteElements() {

    }

    public NoteElements(String note, String email, String date) {
        Note = note;
        Email = email;
        this.Date = date;
    }

    public NoteElements(String note, String email) {
        Note = note;
        Email = email;
    }
}
