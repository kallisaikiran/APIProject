package com.example.finalproj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apiprojects.tmdbapp.RetrofitModel.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText etName,etEmail,etuname,etPassword;
    TextView tv_already_have_acc;
    Button btn_signup;
    ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        etName=(EditText)findViewById(R.id.etName);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etuname=(EditText)findViewById(R.id.etuname);
        etPassword=(EditText)findViewById(R.id.etPassword);
        btn_signup=(Button)findViewById(R.id.btn_signup);
        loadingBar = new ProgressDialog(RegistrationActivity.this);
        tv_already_have_acc=(TextView)findViewById(R.id.tv_already_have_acc);
        tv_already_have_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String username = etuname.getText().toString();
                String pass = etPassword.getText().toString();



                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(RegistrationActivity.this, "Please write your email...", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(name)) {
                    Toast.makeText(RegistrationActivity.this, "Please write name...", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(pass)) {
                    Toast.makeText(RegistrationActivity.this, "Please Choose your password...", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(username)) {
                    Toast.makeText(RegistrationActivity.this, "Please write your username...", Toast.LENGTH_SHORT).show();
                    return;
                }  else {
                    loadingBar.setTitle("Create Account");
                    loadingBar.setMessage("Please wait, while we are checking the credentials.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    ValidatepEmail(name,email,username,pass);
                }
            }
        });

    }

    private void ValidatepEmail(final String name,final String email,final String username,final String pass) {
User user=new User();
user.setUser_email(email);
user.setUser_name(name);
user.setUser_username(username);
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(RegistrationActivity.this,"user registered sucessfully",Toast.LENGTH_LONG).show();
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                loadingBar.dismiss();
                                finish();
                                Toast.makeText(RegistrationActivity.this,"user registered sucessfully",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(RegistrationActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                }else{
                    Toast.makeText(RegistrationActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });



    }

}