package com.example.volleyball;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Map;

public class MatchRView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_r_view);
        final String[] strtitle = {"-- vs --"};
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        TextView Team1set1, Team1set2, Team1set3, Team1set4, Team1set5;
        TextView Team2set1, Team2set2, Team2set3, Team2set4, Team2set5;
        TextView Team1name, Team2name;

        Toolbar toolbar =findViewById(R.id.toolbar);
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

        String documentid = getIntent().getStringExtra("docid");

        db.collection("matches").document(documentid)
                .addSnapshotListener((value, error) -> {
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

                       strtitle[0] = value.getString("Team1") +"  vs  "+value.getString("Team2");
                    }
                });


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Score");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}