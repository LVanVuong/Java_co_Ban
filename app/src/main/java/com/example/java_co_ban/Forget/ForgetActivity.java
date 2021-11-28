package com.example.java_co_ban.Forget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java_co_ban.ChangePassWord.ChangePassWordActivity;

import com.example.java_co_ban.LoginFrament.LoginFragmentActivity;
import com.example.java_co_ban.R;
import com.example.java_co_ban.Sign_Up.DataLocal;
import com.example.java_co_ban.Sign_Up.user.User;

import java.util.List;

public class ForgetActivity extends AppCompatActivity {
    EditText taikhoan , sodienthoai;
    Button Laylaimk;
    TextView mHome , changepassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        Anhxa();
        Forget();
        Home();
        ChangePassWord();
    }
    public void Forget(){
        Laylaimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = taikhoan.getText().toString().trim();
                String PhoneNumber = sodienthoai.getText().toString().trim();
                List<User> listUser = DataLocal.getListUser();
                for (User item : listUser) {
                    if (item.getPhoneNumbers().equals(PhoneNumber) && item.getTaikhoan().equals(Username)) {
                        Toast.makeText(ForgetActivity.this, item.getMatkhau().toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ForgetActivity.this, "Tài khoản hoặc số điện thoại không đúng", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    public   void Home (){
        mHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetActivity.this, LoginFragmentActivity.class);
                startActivity(intent);
            }
        });
    }
    private void ChangePassWord(){
        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgetActivity.this, ChangePassWordActivity.class);
                startActivity(intent);
            }
        });
    }


    private void Anhxa(){
        mHome = (TextView) findViewById(R.id.Home);
        changepassword = (TextView) findViewById(R.id.Doimk);
        taikhoan = (EditText) findViewById(R.id.TenTk);
        sodienthoai =(EditText) findViewById(R.id.SoDt);
        Laylaimk = (Button) findViewById(R.id.Laylaimk);

    }
}