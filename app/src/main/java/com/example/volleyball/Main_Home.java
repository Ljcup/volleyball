package com.example.volleyball;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import dalvik.system.InMemoryDexClassLoader;

public class Main_Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__home);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);

//        Menu menu = navigationView.getMenu();
//        menu.findItem(R.id.nav_logout).setVisible(true);
//        menu.findItem(R.id.nav_profile).setVisible(true);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_closer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){

            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                break;

            case R.id.nav_match:
                Intent intent5 = new Intent(Main_Home.this,Match.class);
                startActivity(intent5);
                break;

            case R.id.nav_creatematch:
                Intent intent = new Intent(Main_Home.this,Creatematch.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                logout();
                break;

            case R.id.nav_rules:
                Intent intent1 = new Intent(Main_Home.this,Rules.class);
                startActivity(intent1);
                break;

            case R.id.nav_profile:
                Intent intent2 = new Intent(Main_Home.this,Profile.class);
                startActivity(intent2);
                break;

            case R.id.nav_aboutus:
                Intent intent3 = new Intent(Main_Home.this,AboutUs.class);
                startActivity(intent3);
                break;

            case R.id.nav_contactus:
                Intent intent4 = new Intent(Main_Home.this,ContactUs.class);
                startActivity(intent4);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent i1 = new Intent(Main_Home.this,Send_otp.class);
        startActivity(i1);
        finish();
    }
}