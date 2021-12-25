package com.example.java_co_ban.fcm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.java_co_ban.LoginFrament.LoginFragmentActivity;
import com.example.java_co_ban.Navigation.MainActivity;
import com.example.java_co_ban.R;
import com.example.java_co_ban.Sever.CheckingNetwork;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NextLogin extends AppCompatActivity {
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
        firebaseAuth = FirebaseAuth.getInstance();
        localData();
    }

    private void localData() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nexActivity();
            }
        },2000);
    }

    private void nexActivity() {
            checkUser();
    }
    private void checkUser(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser == null){
            startActivity(new Intent(this, LoginFragmentActivity.class));

        } else{

            startActivity(new Intent(this, MainActivity.class));

        }
        finish();
    }
}

