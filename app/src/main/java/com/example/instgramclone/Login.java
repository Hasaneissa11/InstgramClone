package com.example.instgramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity implements View.OnClickListener{

    Button logInLoginActivity , signUpLoginActivity ;
    EditText emailLoginEditText , passwordLoginEditText ;
      ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Log In");

        signUpLoginActivity = findViewById(R.id.SignButtonLogin);
        logInLoginActivity = findViewById(R.id.logInButtonLogin);

        emailLoginEditText = findViewById(R.id.emailEditTextLogin);
       passwordLoginEditText = findViewById(R.id.passwordEditTextLogin);

       logInLoginActivity.setOnClickListener(this);
       signUpLoginActivity.setOnClickListener(this);



        if (ParseUser.getCurrentUser()!=null)
        {
            ParseUser.logOutInBackground();

        }




    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SignButtonLogin:
                Intent intent= new Intent(Login.this,MainActivity.class);
                startActivity(intent);
                break;

            case R.id.logInButtonLogin:
                ParseUser.logInInBackground(emailLoginEditText.getText().toString(), passwordLoginEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                            TranstionToSocialMedia();
                    }
                });

        }
    }
    public void rootTapped(View view) {


        InputMethodManager inputMethodManger = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManger.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

    }
    public void TranstionToSocialMedia(){

        Intent intent = new Intent(Login.this,SocialMedia.class);
        startActivity(intent);
        finish();

    }



}
