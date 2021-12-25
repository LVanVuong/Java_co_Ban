package com.example.java_co_ban.Navigation;


import static com.example.java_co_ban.Navigation.MainActivity.MY_REQUEST_CODE;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.java_co_ban.ListTopic.ListTopicFragment;
import com.example.java_co_ban.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    ImageView imageViewavatar;
    EditText stlfullname, email;
    Button mUpdateprofile;
    Uri muri;
    private MainActivity mMainActivity;
    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);


        imageViewavatar = root.findViewById(R.id.imgavatar);
        stlfullname = root.findViewById(R.id.fullname);
        email = root.findViewById(R.id.email_profile);
        progressDialog = new ProgressDialog(getActivity());
        mMainActivity = (MainActivity) getActivity();


        mUpdateprofile = root.findViewById(R.id.btn_update_profile);
        FirebaseAuth.getInstance();

        initLister();
        UpdateProfile();
        setInformation();
        return root;
    }

    private void initLister() {
        imageViewavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermission();
            }
        });
    }

    private void onClickRequestPermission() {

        if (mMainActivity == null) {
            return;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mMainActivity.openGallery();
            return;
        }
        if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            mMainActivity.openGallery();
        } else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            getActivity().requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

    private void setInformation() {
        FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
        if (User == null) {
            return;
        }
        stlfullname.setText(User.getDisplayName());
        email.setText(User.getEmail());
        Glide.with(getActivity()).load(User.getPhotoUrl()).error(R.drawable.avatar_defaut).into(imageViewavatar);
    }

    public void setBitmapImageView(Bitmap bitmapimageView) {
        imageViewavatar.setImageBitmap(bitmapimageView);
    }

    public void setMuri(Uri uri) {

        this.muri = uri;
    }

    public void onClickUpdateProfile() {
        Bitmap bitmap = null;
        try {
            if (muri != null) {
                bitmap = MediaStore.Images.Media.getBitmap(mMainActivity.getContentResolver(), muri);
                uploadImage(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void UpdateProfile() {
        mUpdateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                if (mEmail == null) {
                    return;
                }
                onClickUpdareEmail();
                onClickUpdateProfile();
            }
        });
    }

    private void onClickUpdareEmail() {
        String strNewEmail = email.getText().toString().trim();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        progressDialog.show();
        user.updateEmail(strNewEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "User email address updated.", Toast.LENGTH_SHORT).show();
                            mMainActivity.ShowInformation(null);
                        }
                    }
                });
    }


    // TOdo: Update Img sever


    private void uploadImage(Bitmap imageBitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 3, stream);
        byte[] b = stream.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        ArrayList<Pair<String, String>> params = new ArrayList<Pair<String, String>>();
        params.add(new Pair<>("image", encodedImage));

        try {
            new AsyncUploader().execute("https://emiliakute.000webhostapp.com/sever/profile.php", getQuery(params));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private String getQuery(List<Pair<String, String>> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (Pair<String, String> pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.first, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.second, "UTF-8"));
        }
        return result.toString();
    }

    private class AsyncUploader extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            String urlString = strings[0];
            String params = strings[1];
            URL url = null;
            InputStream stream = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                urlConnection.connect();

                OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
                wr.write(params);
                wr.flush();

                stream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
                String result = reader.readLine();
                return result;
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (!result.contains("https")) {
                Toast.makeText(mMainActivity, "Upload profile khong thanh cong!", Toast.LENGTH_SHORT).show();
            } else  Toast.makeText(mMainActivity, result, Toast.LENGTH_SHORT).show();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user == null) {
                return;
            }
            String strFullname = stlfullname.getText().toString().trim();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(strFullname)
                    .setPhotoUri(Uri.parse(result))
                    .build();
            progressDialog.show();
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Update profile sucess", Toast.LENGTH_SHORT).show();
                                if (!result.contains("https")) {
                                    mMainActivity.ShowInformation(null);
                                } else {
                                    mMainActivity.ShowInformation(result);
                                }
                                startActivity(new Intent(getActivity(), MainActivity.class));
                            }
                        }
                    });
        }
    }

}