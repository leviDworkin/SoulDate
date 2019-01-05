package com.example.mycomputer.souldate;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class reportActivity extends AppCompatActivity {
    private EditText report,name;
    private Button reportbtn;
    private DatabaseReference mDatabase,managerRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        managerRef = mDatabase.child("manager");
        report = findViewById(R.id.reportText);
        name = findViewById(R.id.name);
        reportbtn = findViewById(R.id.sendReport);
        reportbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(reportActivity.this);
                builder.setMessage("Send report to the manager?").
                        setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Manager man = new Manager(name.getText().toString().trim(),report.getText().toString().trim());
                                managerRef.push().setValue(man);
                                Toast.makeText(reportActivity.this, "Report sent to the manager", Toast.LENGTH_SHORT).show();
                                reportActivity.super.onBackPressed();
                            }
                        }).setNegativeButton("Close",null);
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
