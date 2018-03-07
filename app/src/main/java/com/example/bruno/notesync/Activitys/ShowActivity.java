package com.example.bruno.notesync.Activitys;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.bruno.notesync.JavaClasses.AdapterRecycler;
import com.example.bruno.notesync.JavaClasses.DbHelper;
import com.example.bruno.notesync.JavaClasses.NoteElements;
import com.example.bruno.notesync.JavaClasses.OnClickListener;
import com.example.bruno.notesync.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ShowActivity extends AppCompatActivity {


    DbHelper helper;

    ArrayList<NoteElements> noteList;


    //GridView gridView;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    AdapterRecycler adapterRecycler;


    Button show;
    TextView textShow;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton floatingActionButton;
    FirebaseDatabase database;
    FirebaseUser firebaseUser;

    DatabaseReference myRef;

    private final String TagNAME = "Show Avitivity";

    private String EmailOfCurrentUser;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show, menu);

        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);

        database = FirebaseDatabase.getInstance();
        EmailOfCurrentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        // database.setPersistenceEnabled(true);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        floatingActionButton = findViewById(R.id.fab);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        myRef = database.getReference("AllNotes").child("notes");

        Toolbar tb = findViewById(R.id.toolbar_id_show);
        setSupportActionBar(tb);
        ActionBar actionbar = getSupportActionBar();

        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.ic_drawer2);


        // helper = new DbHelper(this);
        // noteList = (ArrayList<NoteElements>) helper.getAllNotes();

        // readFromFirebase();

        noteList = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {

                    NoteElements noteElements = noteSnapshot.getValue(NoteElements.class);

                    if (noteElements.getEmail().equalsIgnoreCase(EmailOfCurrentUser)) {

                        Log.d(TagNAME, "Value is: " + noteElements.getDate());
                        noteList.add(noteElements);
                    }
                }
                Collections.reverse(noteList);
                adapterRecycler = new AdapterRecycler(noteList);

                mRecyclerView.setAdapter(adapterRecycler);

                adapterRecycler.setOnItemClickListener(new OnClickListener() {
                    @Override
                    public void onItemClick(int position, View view) {
                        String uid = noteList.get(position).getUid();
                        Log.d("TAG", "onItemClick position: " + position);
                        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                        intent.putExtra("uid", uid);
                        startActivity(intent);

                    }


                    @Override
                    public void onItemLongClick(int position, View view) {
                        Log.d("TAG", "onItemLongClick position: " + position);
                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                startActivity(intent);

            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case (R.id.SignOutItem):

                        FirebaseAuth.getInstance().signOut();

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);

                        break;

                    case (R.id.profileItem):

                        Intent profileintent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(profileintent);

                        break;


                }
                return true;
            }
        });



      /* noteList= new ArrayList<NoteElements>();


        noteList.add(new NoteElements("Note","bruno"));
        noteList.add(new NoteElements("Note","bruno"));*/


    }

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


    @Override
    public void onBackPressed() {

        moveTaskToBack(true);


    }
}
