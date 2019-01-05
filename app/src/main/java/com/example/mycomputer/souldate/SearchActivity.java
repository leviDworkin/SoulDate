package com.example.mycomputer.souldate;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private DatabaseReference mDatabase, userRef;
    private User user;
    private FirebaseUser currentUser;
    private LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        userRef = mDatabase.child(currentUser.getUid());

        linear = findViewById(R.id.linear_search);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    final String friendUid = snapshot.getKey();
                    final User fuser = snapshot.getValue(User.class);
                    //Dynamically adds button
                    final Button friendDatabtn = new Button(SearchActivity.this);
                    friendDatabtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    friendDatabtn.setText(fuser.getF_name()+" "+fuser.getL_name()); //put friend name here
                    friendDatabtn.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
                    friendDatabtn.setTextSize(18);
                    friendDatabtn.setSingleLine(true);
                    linear.addView(friendDatabtn);

                    friendDatabtn.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
//                                    Toast.makeText(userActivity.this, friendDatabtn.getText().toString(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SearchActivity.this,friendActivity.class);
                            intent.putExtra("FRIEND_UID_KEY",friendUid);
                            startActivity(intent);
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
}
