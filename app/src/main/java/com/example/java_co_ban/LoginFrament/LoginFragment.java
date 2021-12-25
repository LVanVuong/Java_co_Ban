package com.example.java_co_ban.LoginFrament;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import com.example.java_co_ban.Navigation.ProfileFragment;
import com.example.java_co_ban.R;

import com.example.java_co_ban.Sever.CheckingNetwork;
import com.example.java_co_ban.Sign_Up.DataLocal;
import com.example.java_co_ban.Sign_Up.user.User;

import com.example.java_co_ban.fcm.SplashActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.annotations.Nullable;

import java.util.List;

public class LoginFragment extends Fragment {

    private static final int RC_SIGN_IN = 100;
    SharedPreferences sharedPreferences;
    EditText UserName, Password;
    TextView Forget_Password;
    ImageView google;
    Button Login;
    CheckBox checkbox;
   GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_fragment, container, false);

        progressDialog = new ProgressDialog(getActivity());
        UserName = root.findViewById(R.id.email);
        Password = root.findViewById(R.id.pass);
        Forget_Password = root.findViewById(R.id.forget);
        Login = root.findViewById(R.id.button);
        checkbox = root.findViewById(R.id.checkbox);
        google = root.findViewById(R.id.google);


        sharedPreferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);

        UserName.setText(sharedPreferences.getString("taikhoan", ""));
        Password.setText(sharedPreferences.getString("matkhau", ""));
        checkbox.setChecked(sharedPreferences.getBoolean("checked", false));
        firebaseAuth = FirebaseAuth.getInstance();
        if(CheckingNetwork.isNetworkAvailable(getActivity())){
            dangnhap();
            Forget_Password();
            LoginGoogle();
        } else {
            Toast.makeText(getActivity(),"Internet Disconnected",Toast.LENGTH_LONG).show();
        }



        return root;
    }

    private void LoginGoogle() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))// Note: don't false.
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> googleSignInClientTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = googleSignInClientTask.getResult(ApiException.class);
                firebaseAuthWithGoogleAccount(account);

            } catch (Exception e) {

            }
        }
    }

    private void firebaseAuthWithGoogleAccount(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    String uid = firebaseUser.getUid();
                    String email = firebaseUser.getEmail();

                    @Override
                    public void onSuccess(AuthResult authResult) {


                        if (authResult.getAdditionalUserInfo().isNewUser()) {
                            Toast.makeText(getActivity(), "Account Created...\n" + email, Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(getActivity(), "Login User...\n" + email, Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(getActivity(), SplashActivity.class));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                    }
                });
    }
    private void dangnhap() {

            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = UserName.getText().toString().trim();
                    String password = Password.getText().toString().trim();
                    String name = "admin@gmail.com";
                    String pass = "admin1";
                    // Tài khoản mặc định
                    if(email.isEmpty() || password.isEmpty()){
                        Toast.makeText(getActivity(),"Vui lòng nhập email hoặc password",Toast.LENGTH_SHORT).show();
                    }
                    if (email.equals(name) && password.equals(pass)) {
                        CheckBox(checkbox, email, password);
                        Intent intent = new Intent(getActivity(), SplashActivity.class);
                        startActivity(intent);

                        // tài khoản trong danh sách đăng kí
                    } else  {

                        // nếu có check
                        CheckBox(checkbox, email, password);
                        onClickSignIn();


                    }

                }
            });
    }

    private void onClickSignIn() {
        String email = UserName.getText().toString().trim();
        String password = Password.getText().toString().trim();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                           startActivity(new Intent(getActivity(),SplashActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

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

    private void Forget_Password() {
        Forget_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForgetActivity.class);
                startActivity(intent);
            }
        });
    }

}