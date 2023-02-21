package com.samhan.foodtogov10.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.samhan.foodtogov10.R;

public class signup_activity extends AppCompatActivity {

    static  int PreqCode=1;
    static  int REQUESTCODE=1;
    Uri pickedImgUri;

    EditText edtTxt_signup_mail,edtTxt_signup_pass,edtTxt_confirmPass,edtTxt_signup_username;
    Button btn_SignUp;
    TextView txt_GotoLogInPage;
    FirebaseAuth firebaseAuth;
    ImageView ImgUserPhoto;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtTxt_signup_mail=findViewById(R.id.edtTxt_signup_mail);
        edtTxt_signup_pass=findViewById(R.id.edtTxt_signup_pass);
        edtTxt_signup_username=findViewById(R.id.edtTxt_username);
        edtTxt_confirmPass=findViewById(R.id.edtTxt_confirmPass);
        ImgUserPhoto=findViewById(R.id.img_UserImg);
        btn_SignUp=findViewById(R.id.btn_SignUp);

        firebaseAuth=FirebaseAuth.getInstance();

//        txt_GotoLogInPage=findViewById(R.id.txt_GotoLogInPage);

        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT>=22)
                {
                    checkAndRequestForPermission();
                }
                else
                {
                    openGallery();
                }
            }
        });

        btn_SignUp=findViewById(R.id.btn_SignUp);

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=edtTxt_signup_mail.getText().toString();
                String password=edtTxt_signup_pass.getText().toString();
                String confirmPass=edtTxt_confirmPass.getText().toString();
                String username=edtTxt_signup_pass.getText().toString();

                if(!mail.equals("") && !password.equals("") && !username.equals("") && password.equals(confirmPass))
                {
                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
//                            Toast.makeText(getApplicationContext(), "user created", Toast.LENGTH_SHORT).show();

                            updateUserInfo(username,pickedImgUri,firebaseAuth.getCurrentUser());
                        }





                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });



                }
                else
                {
                    Toast.makeText(signup_activity.this, "Please fill up all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateUserInfo(String username, Uri pickedImgUri, FirebaseUser currentUser) {

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath=mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest profileUpdate=new UserProfileChangeRequest.Builder()
                                .setDisplayName(username)
                                .setPhotoUri(uri)
                                .build();
                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(signup_activity.this, "completed", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);

                                        }
                                    }
                                });
                    }
                });
            }
        });

    }




    private void openGallery() {
        Intent galleryIntent=new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESTCODE);


    }

    private void checkAndRequestForPermission() {
        if(ContextCompat.checkSelfPermission(signup_activity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(signup_activity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(this, "permission required", Toast.LENGTH_SHORT).show();
            }
            else {
                ActivityCompat.requestPermissions(signup_activity.this,
                                                  new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                                  PreqCode);
            }

        }
        else
            openGallery();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==REQUESTCODE && data!=null)
        {
            pickedImgUri=data.getData();
            ImgUserPhoto.setImageURI(pickedImgUri);

        }
    }
}