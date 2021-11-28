package com.example.java_co_ban.ChangePassWord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java_co_ban.LoginFrament.LoginFragmentActivity;
import com.example.java_co_ban.R;
import com.example.java_co_ban.Sign_Up.DataLocal;
import com.example.java_co_ban.Sign_Up.user.User;

import java.util.List;

public class ChangePassWordActivity extends AppCompatActivity {
    EditText user , old_password , new_password , enter_the_new_password;
    Button mchange;
    TextView mhome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_word);
        Anhxa();
       ChangePW();
        Home();
    }
    private void ChangePW() {
        mchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        String Username = user.getText().toString().trim();
        String Old_password = old_password.getText().toString().trim();
        String New_password = new_password.getText().toString().trim();
        String ETN_password = enter_the_new_password.getText().toString().trim();
        List<User> listUser = DataLocal.getListUser();
        for (User item : listUser) {
            if (item.getTaikhoan().equals(Username) && item.getPhoneNumbers().equals(Old_password)) {
                    if (New_password.equals(ETN_password) && New_password.equals(Old_password)) {
                        Toast.makeText(ChangePassWordActivity.this, "Mật khẩu mới giống mật khẩu cũ", Toast.LENGTH_SHORT).show();
                    } else if (New_password.equals(ETN_password) && New_password.compareTo(Old_password) != 0) {
                        item.setMatkhau(New_password);
                    }
                    else {
                        Toast.makeText(ChangePassWordActivity.this, "Hai mật khẩu mới khác nhau",Toast.LENGTH_SHORT).show();
                    }
            }
        }
            }
        });
    }
    private void Home(){
        mhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangePassWordActivity.this, LoginFragmentActivity.class);
                startActivity(intent);
            }
        });
    }
    private void Anhxa(){
        user = (EditText) findViewById(R.id.users);
        old_password =(EditText) findViewById(R.id.MKold);
        new_password = (EditText) findViewById(R.id.Mknew);
        enter_the_new_password = (EditText) findViewById(R.id.Nlmknew);
        mchange =(Button) findViewById(R.id.change);
        mhome =(TextView) findViewById(R.id.mhome);
    }
}