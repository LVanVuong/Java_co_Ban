package com.example.java_co_ban.Sign_Up;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.java_co_ban.LoginFrament.LoginFragment;
import com.example.java_co_ban.Navigation.MainActivity;
import com.example.java_co_ban.R;
import com.example.java_co_ban.ListTopic.ListTopicFragment;
//import com.example.java_co_ban.SearchDislay.SearchActivity;
import com.example.java_co_ban.Sign_Up.user.User;
import com.example.java_co_ban.fcm.SplashActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SignUp_Fragment extends Fragment {


    EditText taikhoan, matkhau, nlmatkhau, PhoneNumber , region;
    Button  Sign_UP;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.sign_up_fragment, container, false);
        taikhoan = root.findViewById(R.id.email_name);
        matkhau = root.findViewById(R.id.pass_sign_up);
        nlmatkhau = root.findViewById(R.id.pass_sign_up_1);
        PhoneNumber = root.findViewById(R.id.phone);

        Sign_UP = root.findViewById(R.id.button12);
        region = root.findViewById(R.id.region12);

        DangkiTK();

        return root;
    }

    private void DangkiTK() {
        Sign_UP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String username = taikhoan.getText().toString().trim();
                String password = matkhau.getText().toString().trim();
                String password2 = nlmatkhau.getText().toString().trim();
                if(username.isEmpty()){
                    Toast.makeText(getActivity(),"Vui lòng nhập email ",Toast.LENGTH_SHORT).show();
                } else if(password.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(getActivity(),"Vui lòng nhập mật khẩu ",Toast.LENGTH_SHORT).show();
                }
                   else if (password.equals(password2)) {
                            onClickSignUp();
                    }
                     else {
                        Toast.makeText(getActivity(), "mật khẩu không khớp", Toast.LENGTH_LONG).show();
                    }
                }

        });
    }
    private void onClickSignUp(){
        String username = taikhoan.getText().toString().trim();
        String password = matkhau.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(username, password)
               .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           startActivity(new Intent(getActivity(), SplashActivity.class));
                       } else {
                           Toast.makeText(getActivity(),"Authentication failed", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
    }




}
