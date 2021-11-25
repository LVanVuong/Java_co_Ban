package com.example.java_co_ban.data_local;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java_co_ban.Login.LoginActivity;
import com.example.java_co_ban.R;
import com.example.java_co_ban.SearchDislay.SearchActivity;
import com.example.java_co_ban.data_local.user.User;
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

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = RegisterActivity.class.getName();
    TextView quaylai, dangkimk , Resend;
    EditText taikhoan, matkhau, nlmatkhau, PhoneNumber, SMSOTP , region;
    Button Verifyphonenumber;
    private FirebaseAuth mAuth;
    private String mVerifyId;
    PhoneAuthProvider.ForceResendingToken token;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Anhxa();
        Verify();
        DangkiTK();
        Quaylai();

    }

    private void DangkiTK() {
        dangkimk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(SMSOTP.getText().toString().isEmpty()){
                    SMSOTP.setError("Enter OTP First");
                    return;
                }
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerifyId,SMSOTP.getText().toString());
                authenticateUser(credential);


                String username = taikhoan.getText().toString().trim();
                String password = matkhau.getText().toString().trim();
                String password2 = nlmatkhau.getText().toString().trim();
                String mPhoneNumber = PhoneNumber.getText().toString().trim();
                List<User> listUser = DataLocal.getListUser();
                
                if (password.equals(password2) && kiemTraTaiKhoan(username, listUser) == false) {
                    User user = new User(username, password,mPhoneNumber);
                    DataLocal.setUser(user);
                    Toast.makeText(RegisterActivity.this, "đăng kí thành công", Toast.LENGTH_LONG).show();
                }
                if (kiemTraTaiKhoan(username, listUser) == true) {
                    Toast.makeText(RegisterActivity.this, " tài khoản đã được đăng kí", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "mật khẩu không khớp", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void Verify(){
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
                //authenticateUser(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerifyId = s;
                token = forceResendingToken;
                //PhoneNumber.setVisibility(View.GONE);
                //Verifyphonenumber.setVisibility(View.GONE );

                SMSOTP.setVisibility(View.VISIBLE);
                dangkimk.setVisibility(View.VISIBLE);
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

    private void Quaylai() {
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                onBackPressed();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Anhxa() {

        quaylai = (TextView) findViewById(R.id.Quaylai);
        dangkimk = (TextView) findViewById(R.id.DangKyMK);
        taikhoan = (EditText) findViewById(R.id.DKtaikhoan);
        matkhau = (EditText) findViewById(R.id.DKmatkhau);
        nlmatkhau = (EditText) findViewById(R.id.NLMatKhau);
        PhoneNumber = (EditText) findViewById(R.id.Sodienthoai);
        SMSOTP = (EditText) findViewById(R.id.MaOtp);
        Verifyphonenumber = (Button) findViewById(R.id.SenOtp);
        Resend = (TextView) findViewById(R.id.Resend);
        region = (EditText) findViewById(R.id.region);
    }
  public void VerifyPhoneNumber(String phone){

        // Send OTP
      PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
              .setActivity(this)
              .setPhoneNumber(phone)
              .setTimeout(60L,TimeUnit.SECONDS)
              .setCallbacks(callbacks)
              .build();
      PhoneAuthProvider.verifyPhoneNumber(options);
  }

  public void authenticateUser(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this, SearchActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
  }


}
