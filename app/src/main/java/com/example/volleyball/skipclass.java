package com.example.volleyball;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class skipclass {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db =FirebaseFirestore.getInstance();
    String uuid = mAuth.getUid();
    public  skipclass(Context ct){
        db.collection("player_user")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            boolean flag = false;
                            for (DocumentSnapshot documentSnapshot : task.getResult()){
                                String id = documentSnapshot.getId();
                                if( uuid.equals(id)){
                                    flag = true;
                                    break;
                                }
                            }
                            if(flag){
                                Intent intent = new Intent(ct,Main_Home.class);
                                ct.startActivity(intent);
                            }else{
                                Intent intent = new Intent(ct,Home.class);
                                ct.startActivity(intent);
                            }
                        }
                    }
                });
    }
}
