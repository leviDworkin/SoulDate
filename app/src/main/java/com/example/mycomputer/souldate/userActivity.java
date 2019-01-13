package com.example.mycomputer.souldate;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class userActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView about,name,age,friendsText;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;
    private DatabaseReference userRef,friendsRef;
    private User user;
    private ScrollView scrollView;
    private LinearLayout linear;
    private String fuid;
    private ArrayList<String> friendListUpdated, friendList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        this.getApplication().getUser();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        userRef = mDatabase.child(currentUser.getUid());
        friendsRef = userRef.child("friends");
        scrollView = findViewById(R.id.scroll_friends);
        linear = findViewById(R.id.linearInScroll);
        about = findViewById(R.id.about);
        age = findViewById(R.id.age);
        name = findViewById(R.id.name);
        friendsText = findViewById(R.id.freindsList);
        friendsText.setText("My Friends list:");
        friendList = new ArrayList<>();
        friendListUpdated = new ArrayList<>();
        //grab info
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                about.setText(user.getAbout());
                age.setText(String.valueOf(user.getAge()));
                name.setText(user.getF_name()+" "+user.getL_name());
                friendList.addAll(user.getFriends());
                int i=1;
                while(i<user.getFriends().size()){
                    fuid = user.getFriends().get(i++);
                    final String friendUid = fuid;
                    mDatabase.child(fuid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User friendUser = dataSnapshot.getValue(User.class);
//                            friendList = friendUser.getFriends();
                            //Dynamically adds button
                            final Button friendDatabtn = new Button(userActivity.this);
                            friendDatabtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));
                            friendDatabtn.setText(friendUser.getF_name()+" "+friendUser.getL_name()); //put friend name here
//                            friendDatabtn.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
                            friendDatabtn.setSingleLine(true);
                            friendDatabtn.setTextSize(22);
                            linear.addView(friendDatabtn);

                            friendDatabtn.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(userActivity.this, "Going to friends page", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(userActivity.this,friendActivity.class);
                                    intent.putExtra("FRIEND_UID_KEY",friendUid);
                                    intent.putExtra("USER_FRIENDS_KEY",user.getFriends());
                                    startActivity(intent);
                                }
                            });
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                friendListUpdated = user.getFriends();
                user = dataSnapshot.getValue(User.class);
                for (String friend: user.getFriends()){
                    if (!friendList.contains(friend)){
                        friendListUpdated.add(friend);
                        final String friendId = friend;
                        mDatabase.child(friend).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                User friendUser = dataSnapshot.getValue(User.class);
                                //Dynamically adds button
                                final Button friendDatabtn = new Button(userActivity.this);
                                friendDatabtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT));
                                friendDatabtn.setText(friendUser.getF_name()+" "+friendUser.getL_name()); //put friend name here
//                            friendDatabtn.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
                                friendDatabtn.setSingleLine(true);
                                friendDatabtn.setTextSize(22);
                                linear.addView(friendDatabtn);

                                friendDatabtn.setOnClickListener(new View.OnClickListener(){
                                    @Override
                                    public void onClick(View view) {
//                                    Toast.makeText(userActivity.this, friendDatabtn.getText().toString(), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(userActivity.this,friendActivity.class);
                                        intent.putExtra("FRIEND_UID_KEY",friendId);
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
                friendList = friendListUpdated;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(userActivity.this, SearchActivity.class);
                intent.putExtra("USER_FRIENDS_KEY",user.getFriends());
                Snackbar.make(view, "Search", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(userActivity.this);
            builder.setMessage("Exit user account?").
                    setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            userActivity.super.onBackPressed();
                        }
                    }).setNegativeButton("Close",null);
            AlertDialog alert = builder.create();
            alert.show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.report) {
            startActivity(new Intent(userActivity.this,reportActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Toast.makeText(this, "Unavailable feature at the moment", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this, "Unavailable feature at the moment", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.nav_manage) {
            Toast.makeText(this, "Unavailable feature at the moment", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.nav_send) {
            Toast.makeText(this, "Unavailable feature at the moment", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
