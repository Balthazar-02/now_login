package com.balthazar.newlogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegistrerActivity extends AppCompatActivity {

    TextView alreadyhaveA;
    EditText inputEmail, inputPassword,inputConfirmPassword;
    Button btnRegistrer;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAutjh;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrer);
        alreadyhaveA = findViewById(R.id.alreadyhaveA);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        inputEmail = findViewById(R.id.newinputemail);
        inputPassword = findViewById(R.id.newinputPassword);
        inputConfirmPassword = findViewById(R.id.reinputPassword);
        btnRegistrer = findViewById(R.id.btnregister);
        progressDialog  = new ProgressDialog(this);
        mAutjh = FirebaseAuth.getInstance();
        mUser = mAutjh.getCurrentUser();


        alreadyhaveA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrerActivity.this, MainActivity.class));
            }
        });

        btnRegistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerforAuth();
            }
        });
    }

    private void PerforAuth() {
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String confirmPassword = inputConfirmPassword.getText().toString();


        if (!email.matches(emailPattern))
        {
            inputEmail.setError("Enter Correct Email please");
        }else if (password.isEmpty() || password.length()<6)
        {
            inputPassword.setError("Enter correct password !");
        }else  if (! password.equals(confirmPassword))
        {
            inputConfirmPassword.setError("this is not the correct password !!!!!");
        } else
        {
            progressDialog.setMessage(" please wait a minute for the Registration ...");
            progressDialog.setTitle("registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            mAutjh.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(RegistrerActivity.this, "Registration success", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegistrerActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(RegistrerActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}