package com.example.mycomputer.souldate;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class managerActivity extends AppCompatActivity {
    private DatabaseReference mDatabase, managerRef;
    private LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        managerRef = mDatabase.child("manager");
        linear = findViewById(R.id.linearManager);

        managerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Manager reportMan = snapshot.getValue(Manager.class);

                    final Button blockBtn = new Button(managerActivity.this);
                    blockBtn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    blockBtn.setText(reportMan.getName());
                    blockBtn.setTextSize(20);
                    blockBtn.setSingleLine(true);
                    linear.addView(blockBtn);

                    final TextView reportText = new TextView(managerActivity.this);
                    reportText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    reportText.setText(reportMan.getReport());
                    reportText.setMovementMethod(new ScrollingMovementMethod());
                    reportText.setSingleLine(true);
                    reportText.setTextSize(22);
                    linear.addView(reportText);

                    blockBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            admin.auth().updateUser(uid, {
//                                    disabled: true
//                            });
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(managerActivity.this);
            builder.setMessage("Exit user account?").
                    setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            managerActivity.super.onBackPressed();
                        }
                    }).setNegativeButton("Close",null);
            AlertDialog alert = builder.create();
            alert.show();

        }
    }
}
