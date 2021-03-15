package com.example.volleyball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView logo;
    private static int TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,Send_otp.class);
                startActivity(intent);
                finish();
            }
        },TIME_OUT);

        Toast.makeText(MainActivity.this,"Welcome To LJ CUP SocreBoard",Toast.LENGTH_SHORT).show();

    }
}