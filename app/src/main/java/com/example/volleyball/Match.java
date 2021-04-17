package com.example.volleyball;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirestoreRegistrar;
import com.google.firebase.firestore.Query;

public class Match extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Match");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.matchlist);

        Query query =db.collection("matches").orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<MyModel> match = new FirestoreRecyclerOptions.Builder<MyModel>()
                .setQuery(query,MyModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<MyModel, MyViewHolder>(match) {
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow,parent,false);
                return new MyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull MyModel model) {
                holder.team1name.setText(model.getTeam1());
                holder.team2name.setText(model.getTeam2());

                holder.linearLayout.setOnClickListener(view -> {
                   String id =  getSnapshots().getSnapshot(position).getId();
                   Intent i = new Intent(Match.this,MatchRView.class);
                   i.putExtra("docid",id);
                   startActivity(i);
                });
            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView team1name,team2name;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            team1name = itemView.findViewById(R.id.team1name);
            team2name = itemView.findViewById(R.id.team2name);

            linearLayout = itemView.findViewById(R.id.row);
        }
    }
}