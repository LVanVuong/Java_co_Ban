package com.example.java_co_ban.Intro;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.java_co_ban.LoginFrament.LoginFragmentActivity;
import com.example.java_co_ban.R;
import com.example.java_co_ban.fcm.NextLogin;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class IntroFragment extends Fragment {
    private static final String TAG = IntroFragment.class.getName();

    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.intro,container,false);
        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"haha");
                Intent intent = new Intent(getActivity(), NextLogin.class);

                startActivity(intent);
            }
        });
        return root;
    }
}