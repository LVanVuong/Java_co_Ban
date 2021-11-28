package com.example.java_co_ban.LoginFrament;



import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.java_co_ban.Sign_Up.SignUp_Fragment;

public class LoginAdaper extends FragmentStateAdapter {


    public LoginAdaper(@NonNull FragmentActivity fragmentActivity, int tabCount) {
        super(fragmentActivity);

    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                LoginFragment loginFragment = new LoginFragment();

                return loginFragment;
            case 1:
                SignUp_Fragment signUp_fragment = new SignUp_Fragment();
                return signUp_fragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
