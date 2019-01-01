package com.example.mycomputer.souldate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsSignupActivity extends AppCompatActivity {
    private EditText first_name, last_name, age, about;
    private Button registerbtn;
    private RadioGroup radiogroup;
    private ProgressBar progressBar;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_signup);

        registerbtn = findViewById(R.id.registerbtn);
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        age = findViewById(R.id.age);
        about = findViewById(R.id.about);
        radiogroup = findViewById(R.id.radioGrp);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        registerbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                addUser();
            }
        });
    }

    private void addUser(){
        String FirstName = first_name.getText().toString().trim();
        String LastName = last_name.getText().toString().trim();
        String age1 = age.getText().toString().trim();
        int Age = Integer.parseInt(age1);
        String About = about.getText().toString().trim();
        int selectedId = radiogroup.getCheckedRadioButtonId();
        // find the radiobutton by returned id
        RadioButton radiobtn = findViewById(selectedId);
        String Gender = radiobtn.getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(FirstName)|| TextUtils.isEmpty(LastName) || TextUtils.isEmpty(Gender)){
            Toast.makeText(DetailsSignupActivity.this, "Empty fields!", Toast.LENGTH_LONG).show();
        }else{

            String userId = getIntent().getExtras().getString("USER_ID_KEY");
            String userEmail = getIntent().getExtras().getString("USER_EMAIL_KEY");

            User user = new User(FirstName,LastName,Age,Gender,About,userEmail);
            mDatabase.child(userId).setValue(user);
            Intent intent = new Intent(DetailsSignupActivity.this,userActivity.class);
            startActivity(intent);
            Toast.makeText(DetailsSignupActivity.this, "User added", Toast.LENGTH_LONG).show();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
