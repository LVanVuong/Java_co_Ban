package com.example.java_co_ban.Navigation;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.java_co_ban.ChangePassWord.ChangePassWordFragment;
import com.example.java_co_ban.LoginFrament.LoginFragment;
import com.example.java_co_ban.LoginFrament.LoginFragmentActivity;
import com.example.java_co_ban.R;
import com.example.java_co_ban.ListTopic.ListTopicFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_MY_PROFILE = 1;
    private static final int FRAGMENT_CHANEPASS = 2;
    public static final int MY_REQUEST_CODE = 10;

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;

    private int mCurrentFragment = FRAGMENT_HOME;
    final private ProfileFragment profileFragment = new ProfileFragment();
    private ImageView imageAvatar;
    private TextView name, email;


    final private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent == null) {
                            return;
                        }
                        Uri uri = intent.getData();
                        profileFragment.setMuri(uri);
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            profileFragment.setBitmapImageView(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.navication_view);
        initUI();
        Toolbar toolbar = findViewById(R.id.tool_bar_home);
        setSupportActionBar(toolbar);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);

        replaceFragment(new ListTopicFragment());
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        ShowInformation(null);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            if (mCurrentFragment != FRAGMENT_HOME) {
                replaceFragment(new ListTopicFragment());
                mCurrentFragment = FRAGMENT_HOME;
            }

        } else if (id == R.id.nav_myprofile) {
            if (mCurrentFragment != FRAGMENT_MY_PROFILE) {
                replaceFragment(profileFragment);
                mCurrentFragment = FRAGMENT_MY_PROFILE;
            }

        } else if (id == R.id.nav_change_pass) {
            if (mCurrentFragment != FRAGMENT_CHANEPASS) {
                replaceFragment(new ChangePassWordFragment());
                mCurrentFragment = FRAGMENT_CHANEPASS;
            }

        } else if (id == R.id.nav_Logout) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginFragmentActivity.class);
            startActivity(intent);
        }

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        return true;
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, fragment);
        transaction.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        } else {
            Toast.makeText(MainActivity.this, "Vui lòng cho phép truy cập", Toast.LENGTH_SHORT).show();
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncher.launch(Intent.createChooser(intent, "Seclect Picture"));
    }

    private void initUI() {
        imageAvatar = navigationView.getHeaderView(0).findViewById(R.id.mImgavatar);
        name = navigationView.getHeaderView(0).findViewById(R.id.stlName);
        email = navigationView.getHeaderView(0).findViewById(R.id.strEmail);
    }

    public void ShowInformation(String imgUrl) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        String mName = user.getDisplayName();
        String mEmail = user.getEmail();
        Uri uri = user.getPhotoUrl();
        if (imgUrl!=null && !imgUrl.equals("")) {
            uri = Uri.parse(imgUrl);
        }


        if (mName == null) {
            name.setVisibility(View.GONE);

        } else {
            name.setVisibility(View.VISIBLE);
            name.setText(mName);
        }
        email.setText(mEmail);
        Glide.with(this).load(uri).error(R.drawable.avatar_defaut).into(imageAvatar);
    }
}