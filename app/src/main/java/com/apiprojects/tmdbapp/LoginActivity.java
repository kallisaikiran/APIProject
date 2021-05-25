package com.apiprojects.tmdbapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apiprojects.tmdbapp.RetrofitModel.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseAuth mAuth;
    TextInputEditText editTextEmail,editTextPassword;
    Button btn_signin;
    TextView tvSignupHere,tv_forgot_pass;

    ProgressDialog loadingBar;
    private String parentDbName = "User_Registration";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setTitle("Sign In");
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        loadingBar = new ProgressDialog(LoginActivity.this);
        editTextEmail=(TextInputEditText)findViewById(R.id.editTextEmail);
        editTextPassword=(TextInputEditText)findViewById(R.id.editTextPassword);

        tvSignupHere=(TextView)findViewById(R.id.tvSignupHere);
        tvSignupHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));


            }
        });

        btn_signin=(Button)findViewById(R.id.btn_signin);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    userLogin();

            }
        });
    }

    String username;

    private void userLogin() {
        username = editTextEmail.getText().toString();
        String pass = editTextPassword.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Please write your Username...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(username, pass);
        }
    }

    private void AllowAccessToAccount(final String email, final String password) {
        Toast.makeText(LoginActivity.this, email + "" + password, Toast.LENGTH_LONG).show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "user Login  success", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                            String uId = user.getUid();
                            reference.child(uId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User customerPojo = snapshot.getValue(User.class);

                                    if (customerPojo != null) {
                                        String uname = customerPojo.getUser_username();
                                        SharedPreferences sp = getSharedPreferences("AA", 0);
                                        SharedPreferences.Editor et = sp.edit();
                                        et.putString("uname", uname);
                                        et.commit();
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_LONG).show();
                                }
                            });

                        } else {
                            Toast.makeText(LoginActivity.this, "user Login  failed", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

    }

}