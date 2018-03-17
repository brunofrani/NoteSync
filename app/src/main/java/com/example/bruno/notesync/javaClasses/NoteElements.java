package com.example.bruno.notesync.javaClasses;

/**
 * Created by Bruno on 2/27/2018.
 */


public class NoteElements {

    private String note;
    private String email;
    private String date;
    private String uid;

    public NoteElements() {
    }

    public NoteElements(String note, String email, String date, String uid) {
        this.note = note;
        this.email = email;
        this.date = date;
        this.uid = uid;
    }

    public NoteElements(String note, String email, String date) {
        this.note = note;
        this.email = email;
        this.date = date;
    }

    public NoteElements(String note, String email) {
        this.note = note;
        this.email = email;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
