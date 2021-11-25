package com.example.java_co_ban.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java_co_ban.Forget.ForgetActivity;
import com.example.java_co_ban.R;
import com.example.java_co_ban.data_local.DataLocal;
import com.example.java_co_ban.data_local.RegisterActivity;
import com.example.java_co_ban.data_local.user.User;
import com.example.java_co_ban.SearchDislay.SearchActivity;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    EditText matkhau, taikhoan;
    TextView dangky, dangnhap, quenmk;
    CheckBox checkbox;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        anhxa();
        taikhoan.setText(sharedPreferences.getString("taikhoan", ""));
        matkhau.setText(sharedPreferences.getString("matkhau", ""));
        checkbox.setChecked(sharedPreferences.getBoolean("checked", false));
        Dangki();
        dangnhap();
        QuenMK();

    }

    private void Dangki() {
        dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    private void dangnhap() {
        dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = taikhoan.getText().toString().trim();
                String password = matkhau.getText().toString().trim();
                List<User> listUser = DataLocal.getListUser();

                if (timkiemdangnhap(username,listUser,password)) {
                    Toast.makeText(LoginActivity.this, "đăng nhập thành công", Toast.LENGTH_LONG).show();
                    // nếu có check
                    CheckBox(checkbox,username,password);
                    Intent intent = new Intent(LoginActivity.this, SearchActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this, "lỗi đăng nhập ", Toast.LENGTH_LONG).show();

                }


            }
        });
    }
    private  boolean timkiemdangnhap(String  user, List<User> listUser,String password) {
        for (User item : listUser) {
            if(item.getTaikhoan().equals(user) && item.getMatkhau().equals(password)) {
                return true;
            }
        }
        return false;
    }
    private void CheckBox(CheckBox checkbox, String username , String password){
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
    private void QuenMK(){
        quenmk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);

            }
        });
    }

    private void anhxa() {
        checkbox = (CheckBox) findViewById(R.id.checkBox);
        matkhau = (EditText) findViewById(R.id.MatKhau);
        taikhoan = (EditText) findViewById(R.id.Taikhoan);
        dangky = (TextView) findViewById(R.id.dangky);
        dangnhap = (TextView) findViewById(R.id.dangnhap);
        quenmk = (TextView) findViewById(R.id.quenmk);
    }
}