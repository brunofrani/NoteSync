package com.example.bruno.notesync.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bruno.notesync.R;
import com.example.bruno.notesync.javaClasses.AdapterRecycler;
import com.example.bruno.notesync.javaClasses.DbHelper;
import com.example.bruno.notesync.javaClasses.NoteElements;
import com.example.bruno.notesync.javaClasses.OnClickListener;
import com.example.bruno.notesync.javaClasses.RvItemClickListener;
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


    private final String TagNAME = "Show Avitivity";

    ArrayList<NoteElements> noteList;
    AdapterRecycler adapterRecycler;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton floatingActionButton;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private String EmailOfCurrentUser;
    OnClickListener onClickListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);

        database = FirebaseDatabase.getInstance();
        EmailOfCurrentUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        floatingActionButton = findViewById(R.id.fab);
        mRecyclerView =  findViewById(R.id.my_recycler_view);
        myRef = database.getReference("AllNotes").child("notes");

        Toolbar tb = findViewById(R.id.toolbar_id_show);
        setSupportActionBar(tb);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.mipmap.ic_drawer2);

        noteList = new ArrayList<>();
        adapterRecycler = new AdapterRecycler(noteList);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TagNAME, "on data change");
               noteList.clear();
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    NoteElements noteElements = noteSnapshot.getValue(NoteElements.class);
                    if (noteElements.getEmail().equalsIgnoreCase(EmailOfCurrentUser))
                    {
                        Log.d(TagNAME, "Value is: " + noteElements.getDate());
                        noteList.add(noteElements);
                    }
                }
                Collections.reverse(noteList);
                adapterRecycler.notifyDataSetChanged();
              // adapterRecycler = new AdapterRecycler(noteList);
               // mRecyclerView.setAdapter(adapterRecycler);

            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        Log.w("TAG", "notelist elements"+noteList.size());
      // adapterRecycler = new AdapterRecycler(noteList);

        Log.w("TAG", "Failed to read value."+adapterRecycler.getItemCount());
        mRecyclerView.setAdapter(adapterRecycler);
       // adapterRecycler = new AdapterRecycler(noteList);
      //  mRecyclerView.setAdapter(adapterRecycler);


        mRecyclerView.addOnItemTouchListener(new RvItemClickListener(getApplicationContext(), mRecyclerView, new OnClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                String uid = noteList.get(position).getUid();
                Log.d("TAG", "onItemClick position: " + position);
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
               // mRecyclerView.setAdapter(adapterRecycler);
            }

            @Override
            public void onItemLongClick(final int position, View view) {

               /*
*/

                AlertDialog.Builder builder = new AlertDialog.Builder(ShowActivity.this);

                builder.setTitle("Attention");
                builder.setMessage("Do You Want To Delete This Note");
                builder.setIcon(R.mipmap.ic_action_discard);

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        String uid = noteList.get(position).getUid();
                        //myRef.child(uid).removeValue();
                        noteList.remove(noteList.get(position));
                        adapterRecycler.notifyDataSetChanged();
                        myRef.child(uid).removeValue();
                       // dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();


            }
        }));

//        adapterRecycler.notifyDataSetChanged();
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

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
    }
    @Override
    public void onBackPressed() {

        moveTaskToBack(true);
    }

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
}
