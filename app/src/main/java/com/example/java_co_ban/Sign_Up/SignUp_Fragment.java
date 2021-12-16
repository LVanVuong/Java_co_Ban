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

import com.example.java_co_ban.R;
import com.example.java_co_ban.SearchDislay.ListTopicActivity;
//import com.example.java_co_ban.SearchDislay.SearchActivity;
import com.example.java_co_ban.Sign_Up.user.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class SignUp_Fragment extends Fragment {

    TextView Resend;
    EditText taikhoan, matkhau, nlmatkhau, PhoneNumber, SMSOTP, region;
    Button Verifyphonenumber, Sign_UP;
    private FirebaseAuth mAuth;
    private String mVerifyId;
    PhoneAuthProvider.ForceResendingToken token;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.sign_up_fragment, container, false);
        Resend = root.findViewById(R.id.resend);
        taikhoan = root.findViewById(R.id.email_name);
        matkhau = root.findViewById(R.id.pass_sign_up);
        nlmatkhau = root.findViewById(R.id.pass_sign_up_1);
        PhoneNumber = root.findViewById(R.id.phone);
        SMSOTP = root.findViewById(R.id.Otp);
        Verifyphonenumber = root.findViewById(R.id.send_otp);
        Sign_UP = root.findViewById(R.id.button12);
        region = root.findViewById(R.id.region12);

        DangkiTK();
        Verify();

        return root;
    }

    private void DangkiTK() {
        Sign_UP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (SMSOTP.getText().toString().isEmpty()) {
                    SMSOTP.setError("Enter OTP First");
                    return;
                }
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerifyId, SMSOTP.getText().toString());
                authenticateUser(credential);


                String username = taikhoan.getText().toString().trim();
                String password = matkhau.getText().toString().trim();
                String password2 = nlmatkhau.getText().toString().trim();
                String mPhoneNumber = PhoneNumber.getText().toString().trim();
                List<User> listUser = DataLocal.getListUser();

                if (password.equals(password2) && !kiemTraTaiKhoan(username, listUser)) {
                    User user = new User(username, password, mPhoneNumber);
                    DataLocal.setUser(user);
                    Toast.makeText(getActivity(), "đăng kí thành công", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getActivity(), ListTopicActivity.class);
                    startActivity(intent);
                }
                if (kiemTraTaiKhoan(username, listUser)) {
                    Toast.makeText(getActivity(), " tài khoản đã được đăng kí", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "mật khẩu không khớp", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void Verify() {
        Resend.setEnabled(false);
        mAuth = FirebaseAuth.getInstance();
        Verifyphonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mPhoneNumber = PhoneNumber.getText().toString().trim();
                String Region = region.getText().toString().trim();
                mPhoneNumber = mPhoneNumber.substring(1, mPhoneNumber.length());
                mPhoneNumber = Region + mPhoneNumber;
                VerifyPhoneNumber(mPhoneNumber);
            }
        });
        Resend.setOnClickListener(new View.OnClickListener() {
            String mPhoneNumber = PhoneNumber.getText().toString().trim();

            @Override
            public void onClick(View v) {
                VerifyPhoneNumber(mPhoneNumber);
                Resend.setEnabled(false);
            }
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                authenticateUser(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerifyId = s;
                token = forceResendingToken;
                //PhoneNumber.setVisibility(View.GONE);
                //Verifyphonenumber.setVisibility(View.GONE );

                SMSOTP.setVisibility(View.VISIBLE);
                Sign_UP.setVisibility(View.VISIBLE);
                Resend.setVisibility(View.VISIBLE);
                Resend.setEnabled(false);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Resend.setEnabled(true);
            }
        };
    }

    private boolean kiemTraTaiKhoan(String user, List<User> listUser) {
        for (User item : listUser) {
            if (item.getTaikhoan().equals(user)) {
                return true;
            }
        }
        return false;
    }

    public void VerifyPhoneNumber(String phone) {

        // Send OTP
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setActivity(getActivity())
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(callbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    public void authenticateUser(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), ListTopicActivity.class);
                startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
