package com.example.bruno.notesync;

import android.content.Context;
import android.graphics.Movie;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;
import java.util.zip.Inflater;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class Adapter extends BaseAdapter {

    ArrayList<NoteElements> noteElements;
    Context context;
    LayoutInflater inflater;

    public Adapter(Context context, ArrayList<NoteElements> noteElements) {

        this.noteElements = noteElements;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return noteElements.size();
    }

    @Override
    public Object getItem(int i) {
        return noteElements.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        view = inflater.inflate(R.layout.item_dual, viewGroup, false);

        TextView noteText = view.findViewById(R.id.textView1);
        TextView usernameText = view.findViewById(R.id.textView2);

        NoteElements note = (NoteElements) getItem(i);
        noteText.setText(note.getNote());
        usernameText.setText(note.getUserName());

        return view;
    }
}
