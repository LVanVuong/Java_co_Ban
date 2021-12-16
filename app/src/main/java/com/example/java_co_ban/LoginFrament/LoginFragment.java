package com.example.java_co_ban.LoginFrament;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java_co_ban.Forget.ForgetActivity;
import com.example.java_co_ban.R;
import com.example.java_co_ban.SearchDislay.ListTopicActivity;
//import com.example.java_co_ban.SearchDislay.SearchActivity;
import com.example.java_co_ban.Sign_Up.DataLocal;
import com.example.java_co_ban.Sign_Up.user.User;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class LoginFragment extends Fragment {

    SharedPreferences sharedPreferences;
    EditText UserName, Password;
    TextView Forget_Password;
    public static ImageView facebook;
    Button Login;
    CheckBox checkbox;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup)  inflater.inflate(R.layout.login_fragment,container,false);


        UserName = root.findViewById(R.id.email);
        Password = root.findViewById(R.id.pass);
        Forget_Password = root.findViewById(R.id.forget);
        Login = root.findViewById(R.id.button);
        checkbox = root.findViewById(R.id.checkbox);
        facebook = root.findViewById(R.id.facebook);


        sharedPreferences = getContext().getSharedPreferences("data",Context.MODE_PRIVATE);

        UserName.setText(sharedPreferences.getString("taikhoan", ""));
        Password.setText(sharedPreferences.getString("matkhau", ""));
        checkbox.setChecked(sharedPreferences.getBoolean("checked", false));

        dangnhap();
        Forget_Password();

        setupLoginFb();
        return root;
    }

    private void setupLoginFb() {
       facebook.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(),FacebookActivity.class);
               startActivity(intent);
           }
       });


    }


    private void dangnhap() {
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = UserName.getText().toString().trim();
                String password = Password.getText().toString().trim();
                List<User> listUser = DataLocal.getListUser();
                String name = "admin";
                String pass = "admin";

                // Tài khoản mặc định
                if (username.equals(name) && password.equals(pass)) {
                    CheckBox(checkbox, username, password);
                    Intent intent = new Intent(getActivity(), ListTopicActivity.class);
                    startActivity(intent);

                    // tài khoản trong danh sách đăng kí
                } else if (timkiemdangnhap(username, listUser, password)) {
                    Toast.makeText(getActivity(), "đăng nhập thành công", Toast.LENGTH_LONG).show();
                    // nếu có check
                    CheckBox(checkbox, username, password);
                    Intent intent = new Intent(getActivity(), ListTopicActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(getActivity(), "lỗi đăng nhập ", Toast.LENGTH_LONG).show();

                }


            }
        });
    }

    private boolean timkiemdangnhap(String user, List<User> listUser, String password) {
        for (User item : listUser) {
            if (item.getTaikhoan().equals(user) && item.getMatkhau().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private void CheckBox(CheckBox checkbox, String username, String password) {
        if (checkbox.isChecked()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("taikhoan", username);
            editor.putString("matkhau", password);
            editor.putBoolean("checked", true);
            editor.commit();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("taikhoan");
            editor.remove("matkhau");
            editor.remove("checked");
            editor.commit();
        }


    }
    private void Forget_Password(){
        Forget_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForgetActivity.class);
                startActivity(intent);
            }
        });
    }

}