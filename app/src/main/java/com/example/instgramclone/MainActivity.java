package com.example.instgramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText userNameSignUp , emailSignUp , passwordSignUp;
    Button logIn , signUp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Sign Up");


        initializeUiComponent();




        if (ParseUser.getCurrentUser()!=null)
        {
            TranstionToSocialMedia();
        }

    }


    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.signUpButtonSignUp:
                if (userNameSignUp.getText().toString().equals("")||
                passwordSignUp.getText().toString().equals("")||
                        emailSignUp.getText().toString().equals(""))
                {

                    Toast.makeText(this ,"Username , Email , Password  Must be not Empty",Toast.LENGTH_SHORT);
                }

                else {


                ParseUser parseUser = new ParseUser();
                parseUser.setUsername(userNameSignUp.getText().toString());
                parseUser.setPassword(passwordSignUp.getText().toString());
                parseUser.setEmail(emailSignUp.getText().toString());


                final     ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("Signing Up");
                progressDialog.show();
                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e==null)
                        {
                            TranstionToSocialMedia();

                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,e.getMessage().toString(),Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();

                    }
                });}
                break;


            case R.id.logInButtonSignUp:
                Intent intent = new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                break;


        }


    }

    public void rootTapped(View view) {

        InputMethodManager inputMethodManager =  (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);


    }

    public void TranstionToSocialMedia(){

        Intent intent = new Intent(MainActivity.this,SocialMedia.class);
        startActivity(intent);
        finish();

    }
    public void initializeUiComponent(){

        userNameSignUp = findViewById(R.id.userNameEditTextSignUp);
        emailSignUp = findViewById(R.id.emailEditTextSignUp);
        passwordSignUp = findViewById(R.id.passwordEditTextSignUp);
        logIn = findViewById(R.id.logInButtonSignUp);
        signUp = findViewById(R.id.signUpButtonSignUp);
        logIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

        passwordSignUp.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                if (i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()== KeyEvent.ACTION_DOWN)
                {onClick(signUp);}
                return false;


            }
        });



    }
}

