package com.example.volleyball;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {

    FirebaseFirestore db;
    TextView name,dob,email,gender;
    Button logout;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        name = (TextView)findViewById(R.id.prnamedata);
        dob = (TextView)findViewById(R.id.prdobdata);
        email = (TextView)findViewById(R.id.premaildata);
        gender = (TextView)findViewById(R.id.prgenderdata);
        logout = (Button)findViewById(R.id.prbtnsignout);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db.collection("player_user")
                .document(mAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        String namestr = documentSnapshot.getString("Name");
                        String dobstr = documentSnapshot.getString("DOB");
                        String emailstr = documentSnapshot.getString("Email");
                        String genderstr = documentSnapshot.getString("Gender");

                        name.setText(namestr);
                        dob.setText(dobstr);
                        email.setText(emailstr);
                        gender.setText(genderstr);
                    }
                });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i1 = new Intent(Profile.this,Send_otp.class);
                startActivity(i1);
                finish();
            }
        });
    }
}