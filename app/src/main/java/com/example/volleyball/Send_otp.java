package com.example.volleyball;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;


import java.util.concurrent.TimeUnit;

public class Send_otp extends AppCompatActivity {

    private Button btsendotp;
    private EditText etmono;
    public FirebaseAuth mAuth;
    public PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_otp);

        btsendotp = (Button)findViewById(R.id.btsendotp);
        etmono = (EditText)findViewById(R.id.etmono);

        mAuth = FirebaseAuth.getInstance();

        btsendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etmono.getText().toString().trim().isEmpty() || etmono.getText().toString().trim().length() != 10)
                {
                    Toast.makeText(Send_otp.this,"Enter valid Mobile Number",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String mobile = etmono.getText().toString().trim();

                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber("+91"+mobile)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(Send_otp.this)
                            .setCallbacks(mCallbacks)
                            .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }

            }
        });
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Send_otp.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                Toast.makeText(Send_otp.this,"OTP Sent",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Send_otp.this,Verify_otp.class);
                i.putExtra("mobileno", etmono.getText().toString());
                i.putExtra("VerificationId",verificationID);
                startActivity(i);
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            skipclass sk = new skipclass(Send_otp.this);
        }
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential){
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            skipclass sk = new skipclass(Send_otp.this);
                            Toast.makeText(Send_otp.this,"Verified",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Send_otp.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}