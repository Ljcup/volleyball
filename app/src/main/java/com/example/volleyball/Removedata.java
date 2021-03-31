package com.example.volleyball;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Removedata {

    private ArrayList<Person> mylist;
    private int position;
    private String docid,collectionname;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Removedata(ArrayList<Person> mylist, int position,String docid,String collectionname) {
        this.mylist = mylist;
        this.position = position;
        this.docid = docid;
        this.collectionname = collectionname;
    }

    public Person findndelete(){
        Person temp;
        String tempid;

        temp = mylist.get(position);
        tempid = temp.getDocid();
        Log.d("tempid",tempid);


//        db.collection("team_name")
//                .document(docid)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        DocumentSnapshot snapshot = task.getResult();
//                        collectionname = snapshot.getString("teamname");
//                        Log.d("CollName",collectionname);
//                    }
//                });

        db.collection("team_name")
                .document(docid)
                .collection(collectionname)
                .document(tempid)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Deleted",tempid );
                    }
                });
        return temp;
    }
}
