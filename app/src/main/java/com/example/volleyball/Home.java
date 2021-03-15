package com.example.volleyball;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText etName,etEmail,etDOB;
    private Button btcontinue;
    private String name,email,dob,gender;
    private RadioGroup rbgroup;
    private RadioButton rbFemale,rbMale;
    private FirebaseAuth mAuth;
    private String uuid;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        etName = (EditText) findViewById(R.id.etPersonName);
        etEmail = (EditText)findViewById(R.id.etEmailAddress);
        etDOB = (EditText)findViewById(R.id.etDate);

        rbgroup =(RadioGroup)findViewById(R.id.radioGroup);
        rbMale =(RadioButton)findViewById(R.id.rbmale);
        rbFemale =(RadioButton)findViewById(R.id.rbfemale);

        btcontinue = (Button)findViewById(R.id.btcontinue);

        db = FirebaseFirestore.getInstance();
        uuid = mAuth.getUid();


        rbgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rbfemale:
                        gender = (String) rbFemale.getText();

                        break;
                    case R.id.rbmale:
                        gender = (String)rbMale.getText();

                        break;
                }
            }
        });

        etDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        btcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = etName.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                dob = etDOB.getText().toString().trim();

                if(name.isEmpty() || email.isEmpty() || dob.isEmpty()){
                    Toast.makeText(Home.this,"Enter all data",Toast.LENGTH_SHORT).show();
                }else {
                    Map<String, String> user = new HashMap<>();
                    user.put("Name",name);
                    user.put("Email",email);
                    user.put("Gender",gender);
                    user.put("DOB",dob);

                    db.collection("player_user")
                            .document(uuid)
                            .set(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Home.this,"Successful",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Home.this,Main_Home.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Home.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }
    private void showDatePicker(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        db.collection("player_user")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if(task.isSuccessful()){
//                            for (DocumentSnapshot documentSnapshot : task.getResult()){
//                                String id = documentSnapshot.getId();
//                                if( uuid.equals(id)){
//                                    Intent intent = new Intent(Home.this,Main_Home.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            }
//                        }
//                    }
//                });
//    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

        int new_month = month + 1;
        String date = new_month +"/"+dayOfMonth+"/"+year;
        etDOB.setText(date);
    }
}