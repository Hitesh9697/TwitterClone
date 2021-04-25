package com.example.twitterclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.twitterclone.databinding.ActivitySignUpBinding;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setTitle("Sign Up");
        ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Signing Up");

        binding.buttonNewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.editTextSignUpUsername.getText().toString().equals("") && binding.editTextSignUpPassword.getText().toString().equals("")){
                    Toast.makeText(SignUpActivity.this, "Username/Password is required", Toast.LENGTH_SHORT).show();
                } else if (!binding.editTextSignUpPassword.getText().toString().equals(binding.editTextSignUpConfirmPassword.getText().toString())
                        && !binding.editTextSignUpPassword.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "Password does not match. Please try again!", Toast.LENGTH_SHORT).show();
                } else {
                    ParseUser user = new ParseUser();
                    // Set the user's username and password, which can be obtained by a forms
                    user.setUsername(binding.editTextSignUpUsername.getText().toString());
                    user.setPassword(binding.editTextSignUpPassword.getText().toString());
                    progressDialog.show();
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            progressDialog.dismiss();
                            if (e == null) {
                                Toast.makeText(SignUpActivity.this, "Sign Up successful", Toast.LENGTH_SHORT).show();
                                ParseObject parseObject = new ParseObject("Follow");
                                parseObject.put("username", binding.editTextSignUpUsername.getText().toString());
                                parseObject.saveInBackground();
                                finish();
                            } else {
                                ParseUser.logOut();
                                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });
    }
}