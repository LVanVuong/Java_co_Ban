package com.example.java_co_ban.LoginFrament;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.java_co_ban.Intro.ViewPager2Adapter;
import com.example.java_co_ban.R;
//import com.example.java_co_ban.SearchDislay.ImageAdapter;
import com.facebook.CallbackManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragmentActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
//    View facebook;
//    View google;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private CallbackManager fbcallbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fragment);

        tabLayout =findViewById(R.id.tab_layout);
        viewPager2 = findViewById(R.id.view_pager2);
//        facebook = findViewById(R.id.facebook_one);
//        google = findViewById(R.id.google_one);


        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        LoginAdaper adapter = new LoginAdaper(this,tabLayout.getTabCount());
        viewPager2.setAdapter(adapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewPager2.setPageTransformer(new DepthPageTransformer());
        }
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



}