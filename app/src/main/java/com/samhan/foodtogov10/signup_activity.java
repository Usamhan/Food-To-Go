package com.samhan.foodtogov10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup_activity extends AppCompatActivity {

    EditText edtTxt_signup_mail,edtTxt_signup_pass,edtTxt_signup_username;
    Button btn_SignUp;
    TextView txt_GotoLogInPage;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtTxt_signup_mail=findViewById(R.id.edtTxt_signup_mail);
        edtTxt_signup_pass=findViewById(R.id.edtTxt_signup_pass);
        edtTxt_signup_username=findViewById(R.id.edtTxt_username);

        firebaseAuth=FirebaseAuth.getInstance();

//        txt_GotoLogInPage=findViewById(R.id.txt_GotoLogInPage);

        btn_SignUp=findViewById(R.id.btn_SignUp);

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail=edtTxt_signup_mail.getText().toString();
                String password=edtTxt_signup_pass.getText().toString();
//                String username=edtTxt_signup_pass.getText().toString();

                if(!mail.equals("") && !password.equals(""))
                {
                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(getApplicationContext(), "user created", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });




                }
            }
        });
    }
}