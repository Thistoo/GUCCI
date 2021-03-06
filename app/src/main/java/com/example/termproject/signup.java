package com.example.termproject;

//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.example.termproject.domain.Member;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//
//public class signup extends AppCompatActivity {
//
//
//    EditText Email, Password, Nickname;
//    Button btnSignup;
//    static final String TAG = "SignUp";
//    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//    FirebaseUser user;
//    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_signup);
//        btnSignup = findViewById(R.id.btnSignup);
//        Email = findViewById(R.id.SignUpEmailEditText);
//        Password = findViewById(R.id.SignUpPasswordEditText);
//        Nickname = findViewById(R.id.SignUpNicknameEditText);
//
//        btnSignup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String EmailValue, PasswordValue, NicknameValue, NameValue, BirthDateValue;
//                EmailValue = Email.getText().toString();
//                PasswordValue = Password.getText().toString();
//                NicknameValue = Nickname.getText().toString();
//
//                if (conditionChecker(EmailValue, PasswordValue, NicknameValue)) {
//                    Member member = new Member(EmailValue, NicknameValue);
//                    SignUp(member, PasswordValue);
//                } else {
//                    Log.e("signUpError", "Fail to SignUp : check the condition");
//                }
//            }
//        });
//    }
//
//    private boolean conditionChecker(String EmailValue, String PasswordValue, String NicknameValue){// ???????????? ?????????. ?????? ??????
//        if (EmailValue.length() != 0 && PasswordValue.length() != 0 && NicknameValue.length() != 0)
//            return true;
//        else
//            return false;
//    }
//
//
//    private void SignUp(Member member, String PasswordValue) {
//        firebaseAuth.createUserWithEmailAndPassword(member.getEmail(), PasswordValue).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    Log.e("SignUp", "???????????? ??????");
//                    // ????????? ????????? firebaseAuth ?????? ????????????. uid??? ?????? ??????.
//                    user = firebaseAuth.getCurrentUser();
//                    // ?????? ????????? ????????? ????????? DB??? ????????? ??????.
//                    firebaseFirestore.collection("user").document(user.getUid()).set(member).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Log.e("SignUp", "???????????? ??????");
//                                StartActivity(MainActivity.class);
//                            } else {
//                                Log.e("SignUp", "???????????? ??????");
//                            }
//                        }
//                    });
//                } else {
//                    Log.e("SignUp", "???????????? ??????");
//                }
//            }
//        });
//    }
//
//
//    private void StartActivity(Class c) {
//        Intent intent = new Intent(this, c);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }
//}


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

public class signup extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPassword, editTextNickname;
    Button buttonSignup;

//    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://mobile-programming-91257-default-rtdb.asia-southeast1.firebasedatabase.app/");
//    private DatabaseReference mReference = mDatabase.getReference();


    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user;
    FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();

    ProgressDialog progressDialog;
    //define firebase object
//    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();



        //initializig firebase auth object
//        firebaseAuth = FirebaseAuth.getInstance();

        /*
        if (firebaseAuth.getCurrentUser() != null) {
            //?????? ????????? ???????????? ??? ??????????????? ?????????
            finish();
            //????????? profile ??????????????? ??????.
            startActivity(new Intent(getApplicationContext(), nagiki.class)); //????????? ??? ProfileActivity
        }
        */


        //initializing views
        editTextEmail = (EditText) findViewById(R.id.SignUpEmailEditText);
        editTextPassword = (EditText) findViewById(R.id.SignUpPasswordEditText);
        editTextNickname = (EditText) findViewById(R.id.SignUpNicknameEditText);

        buttonSignup = (Button) findViewById(R.id.btnSignup);
//        progressDialog = new ProgressDialog(this);

        //button click event
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    //Firebse creating a new user
    private void registerUser() {
        //???????????? ???????????? email, password??? ????????????.
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        //email??? password??? ???????????? ???????????? ?????? ??????.
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email??? ????????? ?????????.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password??? ????????? ?????????.", Toast.LENGTH_SHORT).show();
        }

        //email??? password??? ????????? ???????????? ????????? ?????? ????????????.
        progressDialog.setMessage("??????????????????. ????????? ?????????...");
        progressDialog.show();

//        creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password) //????????????
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
//                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Get information of logged in user
                            user = firebaseAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "???????????? ??????",Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            String uid = user != null ? user.getUid() : null;
                            /*
                            String nickname = editTextNickname.getText().toString();

                            if(uid != null){
                                //userData userInfo = new userData();
                                //userInfo.setUid(uid);
                                //userInfo.setNickname(nickname);
                                //mReference.child("/USER/").child(nickname).push().setValue(uid);


                            }

                             */
                        }
                        else {
                            //???????????????
                            Toast.makeText(signup.this, "???????????? ??????!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });


    }

    @Override
    public void onClick(View view) {
        if(view == buttonSignup) {
            //TODO
            registerUser();
        }
    }

    /*
    //button click event
    @Override
    public void onClick(View view) {
        if(view == buttonSignup) {
            //TODO
            registerUser();
        }
    }

     */
}