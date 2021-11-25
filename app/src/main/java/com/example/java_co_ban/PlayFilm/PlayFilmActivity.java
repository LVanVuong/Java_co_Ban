package com.example.java_co_ban.PlayFilm;

import static android.bluetooth.BluetoothGattDescriptor.PERMISSION_READ;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.java_co_ban.Anime.model.ItemFilm;
import com.example.java_co_ban.Anime.model.ResItemFilm;
import com.example.java_co_ban.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayFilmActivity extends AppCompatActivity {


    private static String TAG = "PlayFilmActivity";

    private VideoView videoView;
    private int position = 0;
    double current_pos, total_duration;
    TextView current, total;
    RelativeLayout relativeLayout;
    Handler mHandler, handler;
    ImageView prev, next, pause, full_screen;
    SeekBar seekBar;
    ProgressBar videoLoading;

    ResItemFilm mResItemFilm;
    List<ItemFilm> itemFilms;
    int currentPosition = 0;

    LinearLayout showProgress,Show_ConTrol;
    boolean isVisible = true;

    private Runnable mRunnable;

    private boolean isFullScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_film);
        // Dong nay phai chay truoc
        itemFilms = getListItemFilm();
        getItemFilmFromIntent();
        initView();
        setupVideo();

        // token


        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast

                        Log.e(TAG, token);
                    }
                });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getItemFilmFromIntent();
    }

    private List<ItemFilm> getListItemFilm() {
        ResItemFilm resItemFilm = (ResItemFilm) getIntent().getSerializableExtra("key");
        if (resItemFilm != null) {
            mResItemFilm = resItemFilm;
            currentPosition = resItemFilm.getSelectedPosition(); // Vi tri film se play
            return resItemFilm.getItemFilms(); // danh sach phim
        }
        return new ArrayList<>(); // danh sach rong
    }

    private void getItemFilmFromIntent() {
        String urlPhim = getIntent().getStringExtra("url_phim");
        // Chi xu ly khi co url_phim
        if (urlPhim != null && !urlPhim.equals("")) {
            // Xu ly them phim vao cuoi danh sach phim, neu nguoi dung dang xem phim ma click vao notification
            ItemFilm itemFilm = new ItemFilm(-1, "Ten phim", -1L, urlPhim, "tacgia");
            if (itemFilms == null) {
                itemFilms = new ArrayList<>();
            }
            itemFilms.add(itemFilm);
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
                if (currentPosition > itemFilms.size())
                    currentPosition = 0;
                else currentPosition++;
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
                if (currentPosition > itemFilms.size())
                    currentPosition = 0;
                PlayVideo(currentPosition);
            }
        });

        PlayVideo(currentPosition);
        setPause();
    }

    public void PlayVideo(int position) {
        ItemFilm item = itemFilms.get(position);
        String videoURL = item.getLinkFilm() == "" ? VideoViewUtils.URL_VIDEO_SAMPLE : item.getLinkFilm();
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