package com.example.volleyball;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class scoring extends AppCompatActivity {

    private TextView Team1name, Team2name;
    private TextView Team1set1, Team1set2, Team1set3, Team1set4, Team1set5;
    private TextView Team2set1, Team2set2, Team2set3, Team2set4, Team2set5;
    private TextView Currentset;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button finishbtn, finishset;
    private Button Team1score, Team2score, Team1undo, Team2undo;
    Map<String, Object> Team1 = new HashMap<>();
    Map<String, Object> Team2 = new HashMap<>();
    int[] counter = {0,0,0};
    String[] sets = {"Set 1","Set 2","Set 3","Set 4","Set 5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Score");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String matchID = getIntent().getStringExtra("matchid");


        Team1name = findViewById(R.id.tvteam1name);
        Team2name = findViewById(R.id.tvteam2name);
        Team1set1 = findViewById(R.id.tvteam1set1);
        Team1set2 = findViewById(R.id.tvteam1set2);
        Team1set3 = findViewById(R.id.tvteam1set3);
        Team1set4 = findViewById(R.id.tvteam1set4);
        Team1set5 = findViewById(R.id.tvteam1set5);
        Team2set1 = findViewById(R.id.tvteam2set1);
        Team2set2 = findViewById(R.id.tvteam2set2);
        Team2set3 = findViewById(R.id.tvteam2set3);
        Team2set4 = findViewById(R.id.tvteam2set4);
        Team2set5 = findViewById(R.id.tvteam2set5);

        Currentset = findViewById(R.id.tvcurrentset);

        Team1score = findViewById(R.id.btteam1score);
        Team2score = findViewById(R.id.btteam2score);
        Team1undo = findViewById(R.id.btteam1undo);
        Team2undo = findViewById(R.id.btteam2undo);
        finishset = findViewById(R.id.btfinishset);
        finishbtn = findViewById(R.id.btfinish);



        db.collection("matches").document(matchID)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value,
                                        @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            Log.d("Error", String.valueOf(error));
                        }
                        if (value != null && value.exists()) {
                            Team1name.setText(value.getString("Team1"));
                            Team2name.setText(value.getString("Team2"));
                            Team1set1.setText(value.get("Team1score.Set 1")+"");
                            Team1set2.setText(value.get("Team1score.Set 2")+"");
                            Team1set3.setText(value.get("Team1score.Set 3")+"");
                            Team1set4.setText(value.get("Team1score.Set 4")+"");
                            Team1set5.setText(value.get("Team1score.Set 5")+"");
                            Team2set1.setText(value.get("Team2score.Set 1")+"");
                            Team2set2.setText(value.get("Team2score.Set 2")+"");
                            Team2set3.setText(value.get("Team2score.Set 3")+"");
                            Team2set4.setText(value.get("Team2score.Set 4")+"");
                            Team2set5.setText(value.get("Team2score.Set 5")+"");
                            Team1 = (Map<String, Object>) value.get("Team1score");
                            Team2 = (Map<String, Object>) value.get("Team2score");
                        }
                    }
                });

        Team1score.setOnClickListener(view -> {
            counter[0]++;
            Team1.replace(sets[counter[2]],counter[0]);
            Map<String,Object> score = new HashMap<>();
            score.put("Team1score",Team1);
            db.collection("matches").document(matchID).update(score);
        });

        Team2score.setOnClickListener(view -> {
            counter[1]++;
            Team2.replace(sets[counter[2]],counter[1]);
            Map<String,Object> score = new HashMap<>();
            score.put("Team2score",Team2);
            db.collection("matches").document(matchID).update(score);
        });

        Team1undo.setOnClickListener(view -> {
            if(counter[0]==0){

            }else{
                counter[0]--;
                Team1.replace(sets[counter[2]],counter[0]);
                Map<String,Object> score = new HashMap<>();
                score.put("Team1score",Team1);
                db.collection("matches").document(matchID).update(score);
            }
        });

        Team2undo.setOnClickListener(view -> {
            if(counter[1] == 0){

            }else{
                counter[1]--;
                Team2.replace(sets[counter[2]],counter[1]);
                Map<String,Object> score = new HashMap<>();
                score.put("Team2score",Team2);
                db.collection("matches").document(matchID).update(score);
            }
        });

        finishbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(scoring.this);

                builder.setMessage("Are you sure want to finish match?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                scoring.super.onBackPressed();
                            }
                        })

                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        finishset.setOnClickListener(view -> {
            if(counter[2] == 4){
                onBackPressed();
            }else {
                counter[2]++;
                counter[0] =0;
                counter [1] =0;
                Currentset.setText(sets[counter[2]]);
            }
        });

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure want to finish match?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scoring.super.onBackPressed();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        counter[2] = 0;
        Currentset.setText(sets[counter[2]]);
    }
}