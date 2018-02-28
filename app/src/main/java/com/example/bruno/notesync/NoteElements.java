package com.example.bruno.notesync;

/**
 * Created by Bruno on 2/27/2018.
 */


public class NoteElements {

    private String Note;
    private String UserName;


    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }


    public NoteElements() {

    }


    public NoteElements(String note, String username) {
        Note = note;
        UserName = username;
    }
}
