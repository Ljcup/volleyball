package com.example.volleyball;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class Creatematch extends AppCompatActivity {

    private Button creatematch;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    private TextInputLayout textInputLayout;
    private AutoCompleteTextView autoCompleteTextView,autoCompleteTextView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creatematch);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Match");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        creatematch = (Button)findViewById(R.id.btcreatematch);
        textInputLayout = findViewById(R.id.inputlayout);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView2 = findViewById(R.id.autoCompleteTextView2);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference subjectsRef = rootRef.collection("team_name");

        List<String> subjects = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, subjects);
        subjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("teamname");
                        subjects.add(subject);
                    }
                }
            }
        });

        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView2.setAdapter(adapter);

        creatematch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String team1  = autoCompleteTextView.getText().toString().trim();
                final String team2 = autoCompleteTextView2.getText().toString().trim();
                String orguuid = mAuth.getUid();

                if(team1.equals(team2) || team1.equals("Select Team") || team2.equals("Select Team")){
                    Toast.makeText(Creatematch.this,"Please Select valid Teams",Toast.LENGTH_SHORT).show();
                }else{

                    Timestamp timestamp = Timestamp.now();
                    Date date = timestamp.toDate();
                    Map<String,Object> match = new HashMap<>();

                    Map<String,Integer> team2score = new HashMap<>();
                    team2score.put("Set 1",0);
                    team2score.put("Set 2",0);
                    team2score.put("Set 3",0);
                    team2score.put("Set 4",0);
                    team2score.put("Set 5",0);
                    
                    Map<String,Integer> team1score = new HashMap<>();
                    team1score.put("Set 1",0);
                    team1score.put("Set 2",0);
                    team1score.put("Set 3",0);
                    team1score.put("Set 4",0);
                    team1score.put("Set 5",0);

                    match.put("Team1",team1);
                    match.put("Team2",team2);
                    match.put("orguuid",orguuid);
                    match.put("Team1score",team1score);
                    match.put("Team2score", team2score);
                    match.put("timestamp",date);


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

            }
        });
    }
}