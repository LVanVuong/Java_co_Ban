package com.example.java_co_ban.ChangePassWord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java_co_ban.LoginFrament.LoginFragmentActivity;
import com.example.java_co_ban.Navigation.MainActivity;
import com.example.java_co_ban.R;
import com.example.java_co_ban.Sign_Up.DataLocal;
import com.example.java_co_ban.Sign_Up.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChangePassWordFragment extends Fragment {
    EditText  new_password , enter_the_new_password;
    Button mchange;

    private ProgressDialog progressDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_change_pass_word, container, false);

        new_password =  root.findViewById(R.id.Mknew);
        enter_the_new_password = root.findViewById(R.id.Nlmknew);
        mchange = root.findViewById(R.id.change);
        progressDialog = new ProgressDialog(getActivity());

       ChangePW();

        return root;
    }
    private void ChangePW() {
        mchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCLickChangePass();
            }
            });

    }

    private void onCLickChangePass() {
        String strPassword = new_password.getText().toString().trim();
        String strNewPassword = enter_the_new_password.getText().toString().trim();
        if(strPassword.isEmpty() || strNewPassword.isEmpty()){
            Toast.makeText(getActivity(), "Please Enter A Password", Toast.LENGTH_SHORT).show();
        }
        
        else if(strPassword.equals(strNewPassword)) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            progressDialog.show();
            user.updatePassword(strPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(getActivity(), "user password updated", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "user password update false!", Toast.LENGTH_SHORT).show();
        }
    }




}