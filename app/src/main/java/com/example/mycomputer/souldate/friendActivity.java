package com.example.mycomputer.souldate;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class friendActivity extends AppCompatActivity {
    private DatabaseReference mDatabase,currentUserFriendsRef;
    private TextView nameText, aboutText, ageText;
    private ImageView image;
    private FirebaseUser currentUser;
    private User fuser,user;
    private Button chatBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        nameText = findViewById(R.id.nameText);
        aboutText = findViewById(R.id.aboutText);
        ageText = findViewById(R.id.ageText);
        image = findViewById(R.id.profPic);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        final String friendUid = getIntent().getExtras().getString("FRIEND_UID_KEY").trim();
        System.out.println("friends id: "+friendUid);
        mDatabase.child(friendUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fuser = dataSnapshot.getValue(User.class);
                nameText.setText(fuser.getF_name()+" "+fuser.getL_name());
                ageText.setText(String.valueOf(fuser.getAge()));
                aboutText.setText(fuser.getAbout());
                if(fuser.getGender().equals("Male")){
                    image.setImageResource(R.drawable.generic_male);
                }else{
                    image.setImageResource(R.drawable.generic_female);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mDatabase.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        chatBtn = findViewById(R.id.chatBtn);
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(friendActivity.this, "Unavailable feature at the moment", Toast.LENGTH_SHORT).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(friendActivity.this);
                builder.setMessage("Add friend to your friend list?").
                        setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(user.getFriends().contains(friendUid)){
                                    Toast.makeText(friendActivity.this, "You are already friends", Toast.LENGTH_SHORT).show();
                                }else{
                                    currentUserFriendsRef = mDatabase.child(currentUser.getUid()).child("friends");
                                    ArrayList<String> temp = new ArrayList<>();
                                    temp.addAll(user.getFriends());
                                    temp.add(friendUid);
                                    currentUserFriendsRef.setValue(temp);
                                    Toast.makeText(friendActivity.this, "Friend added successfully", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }).setNegativeButton("Close",null);
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

}
