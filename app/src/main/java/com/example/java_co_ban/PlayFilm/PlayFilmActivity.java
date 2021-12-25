package com.example.java_co_ban.PlayFilm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.java_co_ban.ListFilm.Film;
import com.example.java_co_ban.R;

import java.util.ArrayList;

public class PlayFilmActivity extends AppCompatActivity {


    private static String TAG = "PlayFilmActivity";

    private VideoView videoView;
    private static int position = 0;
    double current_pos, total_duration;
    TextView current, total;
    RelativeLayout relativeLayout;
    Handler mHandler, handler;
    ImageView prev, next, pause, full_screen;
    SeekBar seekBar;
    ProgressBar videoLoading;

    public  ArrayList<Film> filmsArrayList = new ArrayList<>() ;
    int currentPosition = 0;

    LinearLayout showProgress,Show_ConTrol;
    boolean isVisible = true;

    private Runnable mRunnable;

    private boolean isFullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_film);

        GetListFilm();
//
        initView();
        setupVideo();




//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        String token = task.getResult();
//
//                        // Log and toast
//
//                        Log.e(TAG, token);
//                    }
//                });
   }


    private void GetListFilm() {
        Intent intent = getIntent();
        filmsArrayList.clear();
//        R
        if(intent != null){
            if(intent.hasExtra("film")){
                Film film = intent.getParcelableExtra("film");
                filmsArrayList.add(film);
            }
            if(intent.hasExtra("mfilm")){
                ArrayList<Film> mfilmArrayList = intent.getParcelableArrayListExtra("mfilm");
                filmsArrayList = mfilmArrayList;
            }
        }
    }
    private void initView() {
        videoView = (VideoView) findViewById(R.id.videoview);
        prev = (ImageView) findViewById(R.id.prev);
        next = (ImageView) findViewById(R.id.next);
        pause = (ImageView) findViewById(R.id.pause);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        current = (TextView) findViewById(R.id.current);
        total = (TextView) findViewById(R.id.total);
        full_screen = (ImageView) findViewById(R.id.full_screen);

        videoLoading = (ProgressBar) findViewById(R.id.pgLoading);
        relativeLayout = findViewById(R.id.relative);
        showProgress = findViewById(R.id.showProgress);
        Show_ConTrol = findViewById(R.id.Show_Control);

        // Video tiep theo
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition++;
                if (currentPosition >= filmsArrayList.size())
                    currentPosition = 0;
                PlayVideo(currentPosition);
            }
        });

        // Video truoc do
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition--;
                if (currentPosition < 0)
                    currentPosition = 0;
                PlayVideo(currentPosition);
            }
        });

        full_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int a = 0;
                if (!isFullScreen) {
                    PlayFilmActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    hideSystemUI();
                    isFullScreen = true;
                } else {
                    PlayFilmActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    showSystemUI();
                    isFullScreen = false;
                }
            }
        });

    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

//    public  void hideView(){
//        seekBar.setVisibility(View.VISIBLE);
//    }

    public void setupVideo() {
        mHandler = new Handler();
        handler = new Handler();
        current_pos = 0;
        total_duration = 0;

        //display video duration
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.i(TAG, "onPrepared");
                videoLoading.setVisibility(View.GONE);
                Show_ConTrol.setVisibility(View.VISIBLE);
                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                }
                setVideoProgress();
            }

        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                currentPosition++;
                if (currentPosition > filmsArrayList.size())
                    currentPosition = 0;
                PlayVideo(currentPosition);
            }
        });

        PlayVideo(currentPosition);
        setPause();
    }

    public void PlayVideo(int position) {
        Film film = filmsArrayList.get(position);
        String videoURL = film.getLinkfilm() == "" ? VideoViewUtils.URL_VIDEO_SAMPLE : film.getLinkfilm();
        videoLoading.setVisibility(View.VISIBLE);
        VideoViewUtils.playURLVideo(PlayFilmActivity.this, videoView, videoURL);
        pause.setImageResource(R.drawable.pause_two);
        hideLayout();
    }


    public void setPause() {
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoView.isPlaying()) {
                    videoView.pause();
                    pause.setImageResource(R.drawable.play_two);
                    videoLoading.setVisibility(View.GONE);
                } else {
                    videoView.start();
                    pause.setImageResource(R.drawable.pause_two);
                }
            }
        });
    }

    public void setVideoProgress() {
        //get the video duration
        current_pos = videoView.getCurrentPosition();
        total_duration = videoView.getDuration();

        //display video duration
        total.setText(timeConversion((long) total_duration));
        current.setText(timeConversion((long) current_pos));
        seekBar.setMax((int) total_duration);
        handler = new Handler();

        mRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    int pos = videoView.getCurrentPosition();
                    Log.i(TAG, "current_pos: " + current_pos);
                    Log.i(TAG, "pos: " + pos);
                    current.setText(timeConversion((long) pos));
                    seekBar.setProgress((int) pos);
                    current_pos = pos;
                    handler.postDelayed(this, 1000);
                } catch (IllegalStateException ed) {
                    ed.printStackTrace();
                }
            }
        };
        handler.postDelayed(mRunnable, 1000);

        //seekbar change listner
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Log.i(TAG, "onProgressChanged");
                videoLoading.setVisibility(View.GONE);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.i(TAG, "onStartTrackingTouch");

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i(TAG, "onStopTrackingTouch");
                videoLoading.setVisibility(View.VISIBLE);
                current_pos = seekBar.getProgress();
                videoView.seekTo((int) current_pos);

            }
        });
    }


    public String timeConversion(long value) {
        String songTime;
        int dur = (int) value;
        int hrs = (dur / 3600000);
        int mns = (dur / 60000) % 60000;
        int scs = dur % 60000 / 1000;

        if (hrs > 0) {
            songTime = String.format("%02d:%02d:%02d", hrs, mns, scs);
        } else {
            songTime = String.format("%02d:%02d", mns, scs);
        }
        return songTime;
    }


    // TODO: Khi có dùng hàm này, cần giải phóng mHandler & runnable trong hàm OnDestroy
    public void hideLayout() {

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Show_ConTrol.setVisibility(View.GONE);
                showProgress.setVisibility(View.GONE);
                full_screen.setVisibility(View.GONE);
                isVisible = false;
            }
        };
        handler.postDelayed(runnable, 5000);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(runnable);
                if (isVisible) {
                    Show_ConTrol.setVisibility(View.GONE);
                    showProgress.setVisibility(View.GONE);
                    full_screen.setVisibility(View.GONE);
                    isVisible = false;
                } else {
                    Show_ConTrol.setVisibility(View.VISIBLE);
                    showProgress.setVisibility(View.VISIBLE);
                    full_screen.setVisibility(View.VISIBLE);
                    mHandler.postDelayed(runnable, 5000);
                    isVisible = true;
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        if (handler != null) {
            handler.removeCallbacks(mRunnable);
            handler = null;
            mRunnable = null;
        }
        super.onDestroy();
    }

}