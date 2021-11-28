package com.example.java_co_ban.Intro;

import android.os.Bundle;

import androidx.annotation.Nullable;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.java_co_ban.R;

public class IntroActivity extends FragmentActivity {

    private ViewPager2 viewPager2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager2 = findViewById(R.id.vpIntro);
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        viewPager2.setAdapter(viewPager2Adapter);

    }
}