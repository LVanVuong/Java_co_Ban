package com.example.java_co_ban.Forget;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java_co_ban.ChangePassWord.ChangePassWordFragment;

import com.example.java_co_ban.LoginFrament.LoginFragmentActivity;
import com.example.java_co_ban.R;
import com.example.java_co_ban.Sign_Up.DataLocal;
import com.example.java_co_ban.Sign_Up.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ForgetActivity extends AppCompatActivity {
    EditText mEmail ;
    Button Laylaimk;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        progressDialog = new ProgressDialog(this);
        Anhxa();
        Forget();

    }
    public void Forget(){
        Laylaimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = mEmail.getText().toString().trim();

                FirebaseAuth auth = FirebaseAuth.getInstance();
                progressDialog.show();
                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(ForgetActivity.this,"Email sent.", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                    startActivity(new Intent(ForgetActivity.this,LoginFragmentActivity.class));
                                }else {
                                    Toast.makeText(ForgetActivity.this,"email not registered", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }



    private void Anhxa(){

        mEmail = (EditText) findViewById(R.id.TenTk);
        Laylaimk = (Button) findViewById(R.id.Laylaimk);

    }
}