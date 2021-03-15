package com.example.volleyball;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class Verify_otp extends AppCompatActivity {

    private TextView tvmono;
    private EditText inotp1;
    private EditText inotp2;
    private EditText inotp3;
    private EditText inotp4;
    private EditText inotp5;
    private EditText inotp6;
    private TextView tvresend;
    private Button btverify;
    private String verificationID;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        tvmono = (TextView) findViewById(R.id.tvmono);
        tvmono.setText(String.format("+91-%s" ,getIntent().getStringExtra("mobileno")));
        firebaseAuth = FirebaseAuth.getInstance();
        verificationID = getIntent().getStringExtra("VerificationId");

        inotp1 = (EditText)findViewById(R.id.inotp1);
        inotp2 = (EditText)findViewById(R.id.inotp2);
        inotp3 = (EditText)findViewById(R.id.inotp3);
        inotp4 = (EditText)findViewById(R.id.inotp4);
        inotp5 = (EditText)findViewById(R.id.inotp5);
        inotp6 = (EditText)findViewById(R.id.inotp6);

        tvresend = (TextView)findViewById(R.id.tvresendotp);

        btverify = (Button)findViewById(R.id.btverify);

        setOTPinput();

        btverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmptyotp()){
                    Toast.makeText(Verify_otp.this,"Enter Valid OTP",Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = inotp1.getText().toString().trim() +
                        inotp2.getText().toString().trim() +
                        inotp3.getText().toString().trim() +
                        inotp4.getText().toString().trim() +
                        inotp5.getText().toString().trim() +
                        inotp6.getText().toString().trim() ;

                if(verificationID != null){
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationID,code);
                    signInWithPhoneAuthCredential(phoneAuthCredential);
                }

            }
        });

    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential){
        firebaseAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            skipclass sc = new skipclass(Verify_otp.this);
                            Toast.makeText(Verify_otp.this,"Verified",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Verify_otp.this,"The OTP is invalid",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setOTPinput(){
        inotp1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inotp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inotp2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inotp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inotp3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inotp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inotp4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inotp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inotp5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inotp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean isEmptyotp(){
        if (inotp1.getText().toString().trim().isEmpty() ||
                inotp2.getText().toString().trim().isEmpty() ||
                inotp3.getText().toString().trim().isEmpty() ||
                inotp4.getText().toString().trim().isEmpty() ||
                inotp5.getText().toString().trim().isEmpty() ||
                inotp6.getText().toString().trim().isEmpty())
            return true;
        else
            return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            Intent intent = new Intent(Verify_otp.this,Home.class);
            startActivity(intent);
            finish();
        }
    }
}