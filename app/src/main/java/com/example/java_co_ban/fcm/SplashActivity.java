package com.example.java_co_ban.fcm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.java_co_ban.ListFilm.ListFilmAdapter;
import com.example.java_co_ban.ListTopic.ListTopicFragment;
import com.example.java_co_ban.LoginFrament.LoginFragment;
import com.example.java_co_ban.Navigation.MainActivity;
import com.example.java_co_ban.R;
import com.example.java_co_ban.Sever.CheckingNetwork;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        progressBar = findViewById(R.id.progress_bar);

        ThreeBounce threeBounce = new ThreeBounce();
        threeBounce.setColor(Color.BLACK);
        progressBar.setIndeterminateDrawable(threeBounce);
//        firebaseAuth = FirebaseAuth.getInstance();
        localData();
       // checkUser();
    }

    private void localData() {
        // Network Connectted
        if(CheckingNetwork.isNetworkAvailable(this)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            } ,3000);

        } else {
            // Network Disconneted
            Toast.makeText(this,"Network Disconneted",Toast.LENGTH_SHORT).show();
        }
    }
//    private void checkUser(){
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//        if(firebaseUser == null){
//            startActivity(new Intent(this, LoginFragment.class));
//            finish();
//        } else{
//            String email = firebaseUser.getEmail();
//
//        }
//    }
}