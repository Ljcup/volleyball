package com.example.volleyball;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Creatematch extends AppCompatActivity {

    private EditText Orgname,Team1name,Team2name,Team1cap,Team2cap,Location,Matchdate;
    private Button Creatematch;
    FirebaseFirestore db;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creatematch);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Match");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Orgname = (EditText)findViewById(R.id.etorgname);
        Team1name = (EditText)findViewById(R.id.etteam1name);
        Team1cap = (EditText)findViewById(R.id.etteam1cap);
        Team2name = (EditText)findViewById(R.id.etteam2name);
        Team2cap = (EditText)findViewById(R.id.etteam2cap);
        Location = (EditText)findViewById(R.id.etlocation);
        Matchdate = (EditText)findViewById(R.id.etdate);

        Creatematch = (Button)findViewById(R.id.btcreatematch);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        Creatematch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orgname = Orgname.getText().toString().trim();
                String team1name = Team1name.getText().toString().trim();
                String team2name = Team2name.getText().toString().trim();
                String team1cap = Team1cap.getText().toString().trim();
                String team2cap =Team2cap.getText().toString().trim();
                String location =Location.getText().toString().trim();
                String matchtdate = Matchdate.getText().toString().trim();
                String orguuid = mAuth.getUid();

                Map<String, Object> match = new HashMap<>();
                match.put("orgname",orgname);
                match.put("orguuid",orguuid);
                match.put("Team1",team1name);
                match.put("Team1cap",team1cap);
                match.put("Team2",team2name);
                match.put("Team2cap",team2cap);
                match.put("location",location);
                match.put("Date",matchtdate);
                match.put("Team1score",0);
                match.put("Team2score",0);

                db.collection("matches")
                        .add(match)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(Creatematch.this,"Successful",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Creatematch.this,scoring.class);
                                intent.putExtra("matchid",documentReference.getId());
                                startActivity(intent);
                                finish();
                            }
                        });
            }
        });


    }
}