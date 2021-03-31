package com.example.volleyball;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.LocaleDisplayNames;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Team_registration extends AppCompatActivity {

    private Button teamregBtn,addmem,addteamname;
    private ImageView imgclose;
    private EditText name, email , monbileno,age,teamname;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    private String gender;
    String collectionid;
    String team_name;
    private RadioGroup gendergroup;
    private RadioButton rbmale,rbfemale;
    private ListView listView;
    PersonListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_registration);

        teamregBtn = (Button) findViewById(R.id.teamregbtn);
        teamname = findViewById(R.id.TeamName);
        addteamname = findViewById(R.id.btnteamname);
        listView = (ListView)findViewById(R.id.list);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        ArrayList<Person> data = new ArrayList<>();


        addteamname.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if(teamname.getText().toString().trim().isEmpty()){
                    Toast.makeText(Team_registration.this,"Enter Team Name",Toast.LENGTH_SHORT).show();
                }else {

                    addteamname.setVisibility(View.INVISIBLE);
                    teamname.setCursorVisible(false);
                    teamname.setFocusable(false);
                    teamname.setFocusableInTouchMode(false);


                    team_name = teamname.getText().toString().trim();

                    Map<String,Object> team = new HashMap<>();
                    team.put("teamname",team_name);
                    team.put("uui",mAuth.getUid());

                    db.collection("team_name")
                            .add(team)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(Team_registration.this,"Successful",Toast.LENGTH_SHORT).show();
                                    collectionid = documentReference.getId();

                                    db.collection("team_name")
                                            .document(collectionid)
                                            .collection(team_name)
                                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                                                    if (error != null) {
                                                        Log.w("Error", "Listen failed.", error);
                                                        return;
                                                    }

                                                    for(DocumentChange dc: value.getDocumentChanges() ){
                                                        switch (dc.getType()){
                                                            case ADDED:
                                                                if(dc.getDocument().getData() != null){
                                                                    String name = dc.getDocument().getString("name");
                                                                    String age = dc.getDocument().getString("age");
                                                                    String gender = dc.getDocument().getString("gender");
                                                                    String docid = dc.getDocument().getId();
                                                                    Person person = new Person(name,age,gender,docid);
                                                                    data.add(person);
                                                                }
                                                        }
                                                    }
                                                    adapter = new PersonListAdapter(Team_registration.this,R.layout.list_layout,data,collectionid,team_name);
                                                    listView.setAdapter(adapter);
                                                    Log.d("Data"," "+ data);
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Team_registration.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        });



        teamregBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Team_registration.this);
                dialog.setContentView(R.layout.register_popup);
                imgclose = (ImageView) dialog.findViewById(R.id.btclose);
                name = (EditText)dialog.findViewById(R.id.edtregname);
                email = dialog.findViewById(R.id.edtregEmail);
                monbileno = dialog.findViewById(R.id.edtregnum);
                age = dialog.findViewById(R.id.edtregdob);
                addmem = dialog.findViewById(R.id.btnregadd);

                //this is comment
                rbmale = (RadioButton)dialog.findViewById(R.id.regrbmale);
                rbfemale = (RadioButton)dialog.findViewById(R.id.regrbfemale);
                gendergroup = (RadioGroup)dialog.findViewById(R.id.tmregregradioGroup);

                gendergroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.regrbfemale:
                                gender = rbfemale.getText().toString();
                                break;
                            case R.id.regrbmale:
                                gender = rbmale.getText().toString();
                                break;
                        }
                    }
                });

                addmem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        final String mobilenumber = monbileno.getText().toString().trim();
                        final String strname = name.getText().toString().trim();
                        final String stremail = email.getText().toString().trim();
                        final String strage = age.getText().toString().trim();

                        if (TextUtils.isEmpty(strname) || TextUtils.isEmpty(stremail) || TextUtils.isEmpty(strage)){
                            Toast.makeText(Team_registration.this,"Enter all data",Toast.LENGTH_SHORT).show();
                        }else {

                            Map<String,Object> teamdata = new HashMap<>();
                            teamdata.put("name",strname);
                            teamdata.put("email",stremail);
                            teamdata.put("age",strage);
                            teamdata.put("mobileno.",mobilenumber);
                            teamdata.put("gender",gender);

                            db.collection("team_name").document(collectionid).collection(team_name)
                                    .add(teamdata)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            Toast.makeText(Team_registration.this,"Done",Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    });
                        }
                    }
                });

                imgclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Team Registration");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}