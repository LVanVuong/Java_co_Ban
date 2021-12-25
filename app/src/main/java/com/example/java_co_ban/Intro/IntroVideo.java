package com.example.java_co_ban.Intro;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.java_co_ban.PlayFilm.PlayFilmActivity;
import com.example.java_co_ban.PlayFilm.VideoViewUtils;
import com.example.java_co_ban.R;
import com.github.ybq.android.spinkit.style.ThreeBounce;


public class IntroVideo extends AppCompatActivity {

    private VideoView videoView;
    ProgressBar loading;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_minion);

        loading = findViewById(R.id.loading);

        videoView = findViewById(R.id.videoview);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.introminion;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.start();

        ThreeBounce threeBounce = new ThreeBounce();
        threeBounce.setColor(Color.MAGENTA); // set color loading three bounce
        loading.setIndeterminateDrawable(threeBounce);
        Datalocal();

    }

    private void Datalocal() {
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
                Intent intent = new Intent(IntroVideo.this, IntroActivity.class);
                startActivity(intent);
            }
        }, 10000);

   }


}